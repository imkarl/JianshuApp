package com.copy.jianshuapp.modellayer.remote.interceptors;

import android.text.TextUtils;

import com.copy.jianshuapp.common.LogUtils;
import com.copy.jianshuapp.common.VersionChannel;
import com.copy.jianshuapp.common.reflect.Reflect;

import java.io.EOFException;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 管理Logging的拦截器
 * @version imkarl 2016-08
 */
public class LoggingInterceptor implements Interceptor {

    private static final String HEADER_FORM_DATA = "form-data; name=\"";
    private static final String HEADER_FILENAME = "\"; filename=\"";

    /** 输出长文本 */
    private static final boolean LONG_TEXT = false;

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (VersionChannel.isUnitTest()) {
            Request request = chain.request();
            return chain.proceed(request);
        }

        StringBuilder logStr = new StringBuilder();
        try {
            final Request request = chain.request();

            final RequestBody requestBody = request.body();
            final boolean hasRequestBody = requestBody != null;

            // Log http request
            final Connection connection = chain.connection();
            final Protocol protocol = (connection != null) ? connection.protocol() : Protocol.HTTP_1_1;
            logStr.append(request.method());
            logStr.append(' ');
            logStr.append(request.url());
            logStr.append(' ');
            logStr.append(protocol);
            logStr.append('\n');

            // Log request body
            if(hasRequestBody) {
                logStr.append(toString(requestBody));
            }

            // Process request
            final long startNs = System.nanoTime();
            final Response response = chain.proceed(request);
            final long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

            // Log http response
            final ResponseBody responseBody = response.body();
            final long contentLength = responseBody.contentLength();
            logStr.append(response.code());
            logStr.append(' ');
            logStr.append(response.message());
            logStr.append(" (");
            logStr.append(tookMs);
            logStr.append("ms");
            logStr.append(')');
            logStr.append('\n');

            // Log response's body
            final BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            final Buffer buffer = source.buffer();
            Charset charset = Charset.forName("UTF-8");
            final MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(charset);
                } catch (UnsupportedCharsetException e) {
                    logStr.append("Couldn't decode the response body; charset is likely malformed.").append('\n');
                    charset = null;
                }
            }
            if (contentLength != 0L && charset != null) {
                Buffer result = buffer.clone();

                String responseContent = readString(result, charset);
                logStr.append(responseContent);
            }

            return response;
        } catch (Throwable e) {
            logStr.append(e);
            throw e;
        } finally {
            LogUtils.i(logStr);
        }
    }

    private String toString(RequestBody requestBody) throws IOException {
        StringBuffer buffer = new StringBuffer();
        if (requestBody instanceof MultipartBody) {
            for (MultipartBody.Part part : ((MultipartBody)requestBody).parts()) {
                Reflect reflect = Reflect.on(part);

                Headers headers = null;
                RequestBody body = null;
                if (VersionChannel.isDebug()) {
                    try {
                        headers = reflect.get("headers");
                    } catch (Throwable ignored) { }
                    try {
                        body = reflect.get("body");
                    } catch (Throwable ignored) { }
                }

                String key = null;
                if (headers != null) {
                    key = headers.get("Content-Disposition");
                    if (key.startsWith(HEADER_FORM_DATA)) {
                        key = key.substring(HEADER_FORM_DATA.length(), key.length()-1);

                        int indexFilename = key.indexOf(HEADER_FILENAME);
                        if (indexFilename >= 0) {
                            String name = key.substring(0, indexFilename);
                            String filename = key.substring(indexFilename + (HEADER_FILENAME).length(),
                                    key.length()-1);
                            key = name+"["+filename+"]";
                        }
                    }
                }
                if (TextUtils.isEmpty(key)) {
                    key = "?";
                }

                buffer.append(key);
                buffer.append('=');
                buffer.append(readString(body));
                buffer.append('&');
            }

            if (!TextUtils.isEmpty(buffer)) {
                buffer.deleteCharAt(buffer.length()-1);
                buffer.append('\n');
            }
        } else {
            String body = readString(requestBody);
            if (!TextUtils.isEmpty(body)) {
                buffer.append(body).append('\n');
            }
        }
        return buffer.toString();
    }

    private String readString(RequestBody requestBody) throws IOException {
        if (requestBody == null) {
            return null;
        }

        MediaType contentType = requestBody.contentType();
        // 不能识别
        if (contentType == null) {
            return null;
        }
        // 多媒体
        String typeName = contentType.type().toUpperCase();
        if ("IMAGE".equals(typeName)
                || "AUDIO".equals(typeName)
                || "VIDEO".equals(typeName)) {
            return "["+typeName+"]";
        }

        // 普通
        final Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);

        Charset charset = contentType.charset(Charset.forName("UTF-8"));

        String body = readString(buffer, charset);
        try {
            body = URLDecoder.decode(body, charset.name());
        } catch (Exception ignored) {
        }
        return body;
    }

    private static String readString(Buffer buffer, Charset charset) {
        String result;
        String brand = null;

        // 限制最大输出内容
        long size = buffer.size();
        long kb = size/1024 + 1;
        if (!LONG_TEXT && size > 8*1024) {
            result = "[ "+ kb + "kb ]";

            try {
                // one
                byte[] oneBytes = buffer.readByteArray(4);
                String oneStr = bytesToHexString(oneBytes).toLowerCase();
                if (oneStr.contains("ffd8ff")) {
                    brand = "image/jpg";
                } else if (oneStr.contains("89504e47")) {
                    brand = "image/png";
                } else if (oneStr.contains("47494638")) {
                    brand = "image/gif";
                } else if (oneStr.contains("49492a00")) {
                    brand = "image/tif";
                } else if (oneStr.contains("424d")) {
                    brand = "image/bmp";
                } else {

                    // two
                    byte[] twoBytes = buffer.readByteArray(4);
                    String twoStr = new String(twoBytes, charset).toLowerCase();
                    if (twoStr.endsWith("ftyp")) {
                        brand = buffer.readString(4, charset);
                    } else {

                        // three
                        String threeStr = buffer.readString(4, charset).toLowerCase();
                        if ("webp".equals(threeStr)) {
                            brand = "image/webp";
                        }
                    }
                }
            } catch (EOFException ignored) {
            }
        } else {
            try {
                long brandLength = Math.min(16, buffer.size());
                byte[] brandBytes = buffer.readByteArray(brandLength);
                String brandStr = new String(brandBytes, charset).toUpperCase();

                if (brandStr.startsWith("#!AMR")) {
                    brand = "audio/amr";
                } else if (brandStr.length() > 12) {
                    if (brandStr.substring(4, 8).equals("ftyp")) {
                        brand = brandStr.substring(8);
                    } else if (brandStr.substring(6, 10).equals("JFIF")) {
                        brand = "image/jpg";
                    } else if (brandStr.substring(8, 12).equals("WEBP")) {
                        brand = "image/webp";
                    } else if (brandStr.substring(10, 14).equals("TALB")) {
                        brand = "audio/mp3";
                    }
                }

                if (TextUtils.isEmpty(brand)) {
                    byte[] otherBytes = buffer.readByteArray();
                    byte[] allBytes = new byte[brandBytes.length + otherBytes.length];
                    System.arraycopy(brandBytes, 0, allBytes, 0, brandBytes.length);
                    System.arraycopy(otherBytes, 0, allBytes, brandBytes.length, otherBytes.length);

                    result = new String(allBytes, charset);
                } else {
                    result = null;
                }
            } catch (OutOfMemoryError e) {
                result = "[ OutOfMemoryError "+ kb + "kb ]";
            } catch (Exception e) {
                result = "[ Unknown "+ kb + "kb ]";
            }
        }

        if (!TextUtils.isEmpty(brand)) {
            brand = brand.toLowerCase();
            if (brand.startsWith("mp4")
                || brand.startsWith("f4")
                || brand.startsWith("isom")) {
                brand = "video/mp4";
            } else if (brand.startsWith("3gp")
                    || brand.startsWith("3gs")) {
                brand = "video/3gpp";
            } else if (brand.startsWith("3g2")) {
                brand = "video/3gpp2";
            } else if (brand.contains("qt")) {
                brand = "video/quicktime";
            }
            result = "[ " + brand +" "+ kb + "kb ]";
        }
        return result;
    }

    private static final char[] HEX_ALPHABET = "0123456789abcdef".toCharArray();
    private static String bytesToHexString(byte[] bytes) {
        if(bytes == null) {
            return null;
        } else {
            StringBuilder hex = new StringBuilder(bytes.length * 2);
            for (byte b : bytes) {
                int nibble1 = b >>> 4 & 15;
                int nibble2 = b & 15;
                hex.append(HEX_ALPHABET[nibble1]);
                hex.append(HEX_ALPHABET[nibble2]);
            }
            return hex.toString();
        }
    }

}
