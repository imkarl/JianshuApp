package jianshu.api;

import android.content.Context;
import android.net.Uri;

import com.copy.jianshuapp.common.LogUtils;
import com.copy.jianshuapp.common.ObjectUtils;

/**
 * @version JianShu 2017-03
 */
public class MatchJianShuUrl {
    public static boolean matchUrl(String url, Context context) {
        return matchUrl(url, context, null);
    }

    public static boolean matchUrl(String url, Context context, String from) {
        LogUtils.d("matchUrl : url = " + url);
        if (ObjectUtils.isEmpty(url)) {
            return false;
        }
        if (!isHyperlink(url)) {
            url = "http://" + url;
        }
        LogUtils.d("after append scheme : url = " + url);
        Uri uri = Uri.parse(url);
        if (uri == null) {
            return false;
        }
        String scheme = uri.getScheme();
        String host = uri.getHost();
        String path = uri.getPath();
        LogUtils.d("scheme = " + scheme + " host = " + host + " path = " + path + " auth = " + uri.getAuthority());
        if ("jianshu".equalsIgnoreCase(scheme)) {
            return matchUrl(host, path, context, from);
        }
        return isHttp(host, path, context, from);
    }

    private static boolean matchUrl(String host, String path, Context context, String from) {
        if (ObjectUtils.isEmpty(host) || ObjectUtils.isEmpty(path)) {
            return false;
        }
        String[] pathArray = path.split("/");
        if (pathArray.length < 2) {
            return false;
        }

        // FIXME: 17/3/10 待恢复
        return true;
//
//        String id = pathArray[pathArray.length - 1];
//        if (host.equals("users") || host.equals("MediaType")) {
//            UserCenterActivity.a((Activity) context, id);
//            return true;
//        } else if (host.equals("collections") || host.equals("JsCallback") || host.equals("collection")) {
//            CollectionActivity.a((Activity) context, id);
//            return true;
//        } else if (host.equals("notebooks") || host.equals("nb")) {
//            NotebookActivity.a((Activity) context, id);
//            return true;
//        } else if (host.equals("notes") || host.equals("FormBody")) {
//            if (TextUtils.isEmpty(from)) {
//                ArticleDetailActivity.a(context, id, "URL链接");
//            } else {
//                ArticleDetailActivity.a(context, id, from);
//            }
//            return true;
//        } else if (host.equals("comments")) {
//            try {
//                CommentDetailActivity.a((Activity) context, Long.valueOf(id), null);
//                return true;
//            } catch (NumberFormatException isWebsite) {
//                isWebsite.printStackTrace();
//                return false;
//            }
//        } else if (path.contains("recommended/user")) {
//            RecommendActivity.a(context, 2);
//            return true;
//        } else if (!path.contains("recommended/collection")) {
//            return false;
//        } else {
//            RecommendActivity.a(context, 1);
//            return true;
//        }
    }

    private static boolean isHttp(String host, String path, Context context, String from) {

        // FIXME: 17/3/10 待恢复
        return true;

//        if (!a(host)) {
//            return false;
//        }
//        if (ObjectUtils.isEmpty(path)) {
//            return false;
//        }
//        String[] pathArray = path.split("/");
//        LogUtils.writeFile(pathArray);
//        if (pathArray.length < 2) {
//            return false;
//        }
//        int i = pathArray.length - 2;
//        while (i > 0) {
//            String pItem = pathArray[i];
//            String id = pathArray[i + 1];
//            if (ObjectUtils.isEmpty(id)) {
//                return false;
//            }
//            if (pItem.equals("users") || pItem.equals("MediaType")) {
//                UserCenterActivity.a((Activity) context, id);
//                return true;
//            } else if (pItem.equals("collections") || pItem.equals("JsCallback") || pItem.equals("collection")) {
//                CollectionActivity.a((Activity) context, id);
//                return true;
//            } else if (pItem.equals("notebooks") || pItem.equals("nb")) {
//                NotebookActivity.a((Activity) context, id);
//                return true;
//            } else if (pItem.equals("notes") || pItem.equals("FormBody")) {
//                if (TextUtils.isEmpty(from)) {
//                    ArticleDetailActivity.a(context, id, "URL链接");
//                } else {
//                    ArticleDetailActivity.a(context, id, from);
//                }
//                return true;
//            } else if (pItem.equals("comments")) {
//                try {
//                    CommentDetailActivity.a((Activity) context, Long.valueOf(id), null);
//                    return true;
//                } catch (NumberFormatException isWebsite) {
//                    isWebsite.printStackTrace();
//                }
//            } else {
//                i--;
//            }
//        }
//        if (path.contains("recommended/user")) {
//            RecommendActivity.a(context, 2);
//            return true;
//        } else if (!path.contains("recommended/collection")) {
//            return false;
//        } else {
//            RecommendActivity.a(context, 1);
//            return true;
//        }
    }

    public static boolean isJianShuHost(String host) {
        return !ObjectUtils.isEmpty(host)
                && ("www.jianshu.com".equalsIgnoreCase(host)
                    || "jianshu.com".equalsIgnoreCase(host)
                    || "www.jianshu.io".equalsIgnoreCase(host)
                    || "jianshu.io".equalsIgnoreCase(host));
    }

    public static boolean isHttp(String url) {
        if (url == null || url.length() <= 6 || !url.substring(0, 7).equalsIgnoreCase("http://")) {
            return false;
        }
        return true;
    }

    public static boolean isHttps(String url) {
        if (url == null || url.length() <= 7 || !url.substring(0, 8).equalsIgnoreCase("https://")) {
            return false;
        }
        return true;
    }

    public static boolean isJianshuScheme(String url) {
        if (url == null || url.length() <= 9 || !url.substring(0, 10).equalsIgnoreCase("jianshu://")) {
            return false;
        }
        return true;
    }

    public static boolean isWebsite(String url) {
        if (url == null || url.length() == 0) {
            return false;
        }
        if (isHttp(url) || isHttps(url)) {
            return true;
        }
        return false;
    }

    public static boolean isHyperlink(String url) {
        if (url == null || url.length() == 0) {
            return false;
        }
        if (isWebsite(url) || isJianshuScheme(url)) {
            return true;
        }
        return false;
    }
}
