package com.copy.jianshuapp.third;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;

import com.copy.jianshuapp.common.AppUtils;
import com.copy.jianshuapp.exception.Exception;
import com.copy.jianshuapp.uilayer.listeners.OnLoadedListener;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.internal.Supplier;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.ByteConstants;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.animated.base.AnimatedImage;
import com.facebook.imagepipeline.animated.base.AnimatedImageFrame;
import com.facebook.imagepipeline.cache.DefaultBitmapMemoryCacheParamsSupplier;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableAnimatedImage;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;

/**
 * Fresco管理器
 * @version imkarl 2017-04
 */
public class FrescoManager {
    private FrescoManager() {}

    public static void init(Context context, File baseDirectoryPath) {
        ImagePipelineConfig.Builder imagePipelineConfigBuilder = ImagePipelineConfig.newBuilder(context)
                .setMainDiskCacheConfig(DiskCacheConfig.newBuilder(context)
                        .setBaseDirectoryPath(baseDirectoryPath)
                        .setBaseDirectoryName("original")
                        .build())
                .setDownsampleEnabled(true);
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        Supplier<MemoryCacheParams> memoryCacheParamsSupplier = new DefaultBitmapMemoryCacheParamsSupplier(activityManager) {
            @Override
            public MemoryCacheParams get() {
                int maxCacheEntries = 256;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    maxCacheEntries = 64;
                }
                return new MemoryCacheParams(
                        getMaxCacheSize(),
                        maxCacheEntries,
                        Integer.MAX_VALUE,
                        Integer.MAX_VALUE,
                        Integer.MAX_VALUE);
            }
            private int getMaxCacheSize() {
                final int maxMemory = Math.min(activityManager.getMemoryClass() * ByteConstants.MB, Integer.MAX_VALUE);

                if (maxMemory < 32 * ByteConstants.MB) {
                    return 4 * ByteConstants.MB;
                } else if (maxMemory < 64 * ByteConstants.MB) {
                    return 6 * ByteConstants.MB;
                } else {
                    return maxMemory / 4;
                }
            }
        };
        imagePipelineConfigBuilder.setBitmapMemoryCacheParamsSupplier(memoryCacheParamsSupplier);
        Fresco.initialize(context, imagePipelineConfigBuilder.build());
    }

    public static void loadBitmap(Uri uri, int width, int height, OnLoadedListener<Bitmap> listener) {
        ResizeOptions resizeOptions = null;
        if (width > 0 && height > 0) {
            resizeOptions = new ResizeOptions(width, height);
        }

        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .setResizeOptions(resizeOptions)
                .build();

        ImagePipeline imagePipeline = Fresco.getImagePipeline();

        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, AppUtils.getContext());
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            public void onNewResultImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                if (!dataSource.isFinished()) {
                    return;
                }

                CloseableReference<CloseableImage> closeableImageRef = dataSource.getResult();
                Bitmap bitmap = null;
                if (closeableImageRef != null &&
                        closeableImageRef.get() instanceof CloseableBitmap) {
                    bitmap = ((CloseableBitmap) closeableImageRef.get()).getUnderlyingBitmap();
                } else if (closeableImageRef != null &&
                        closeableImageRef.get() instanceof CloseableAnimatedImage) {
                    Bitmap newBitmap = null;
                    try {
                        AnimatedImage animatedImage = ((CloseableAnimatedImage) closeableImageRef.get()).getImage();
                        AnimatedImageFrame animatedImageFrame = animatedImage.getFrame(0);
                        newBitmap = Bitmap.createBitmap(animatedImageFrame.getWidth(), animatedImageFrame.getHeight(), Bitmap.Config.ARGB_8888);
                        animatedImage.getFrame(0).renderFrame(animatedImageFrame.getWidth(), animatedImageFrame.getHeight(), newBitmap);
                        bitmap = newBitmap;
                    } catch (Exception e) {
                        if (newBitmap != null && !newBitmap.isRecycled()) {
                            newBitmap.recycle();
                        }
                    }
                }

                try {
                    onNewResultImpl(bitmap);
                } finally {
                    CloseableReference.closeSafely(closeableImageRef);
                }
            }
            @Override
            protected void onNewResultImpl(Bitmap bitmap) {
                listener.onSuccess(bitmap);
            }
            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                listener.onFail();
            }
        }, CallerThreadExecutor.getInstance());
    }

}
