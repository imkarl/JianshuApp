package com.copy.jianshuapp.uilayer.widget;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.support.annotation.DimenRes;
import android.support.annotation.Dimension;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

import com.copy.jianshuapp.common.AppUtils;
import com.copy.jianshuapp.common.DisplayInfo;
import com.copy.jianshuapp.utils.ImageUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 通用的DraweeView
 * @version imkarl 2017-04
 *
 * @tips 支持的xml属性：
 * actualImageUri
 * actualImageResource
 *
 * actualImageScaleType
 * placeholderImage
 * pressedStateOverlayImage
 * progressBarImage
 * fadeDuration
 * viewAspectRatio
 * placeholderImageScaleType
 * retryImage
 * retryImageScaleType
 * failureImage
 * failureImageScaleType
 * progressBarImageScaleType
 * progressBarAutoRotateInterval
 * backgroundImage
 * overlayImage
 *
 * roundAsCircle
 * roundedCornerRadius
 * roundTopLeft
 * roundTopRight
 * roundBottomLeft
 * roundBottomRight
 * roundWithOverlayColor
 * roundingBorderWidth
 * roundingBorderColor
 * roundingBorderPadding
 */
public class UniversalDraweeView extends SimpleDraweeView {
    private GenericDraweeHierarchy mDraweeHierarchy;
    private boolean mEnableResize = true;

    public UniversalDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }
    public UniversalDraweeView(Context context) {
        super(context);
    }
    public UniversalDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public UniversalDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public UniversalDraweeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setHierarchy(GenericDraweeHierarchy hierarchy) {
        super.setHierarchy(hierarchy);
        mDraweeHierarchy = hierarchy;
    }

    public void setPlaceholderImage(@Nullable Drawable drawable) {
        mDraweeHierarchy.setPlaceholderImage(drawable);
    }
    public void setPlaceholderImage(@DrawableRes int resourceId) {
        mDraweeHierarchy.setPlaceholderImage(resourceId);
    }

    public void setFailureImage(@Nullable Drawable drawable) {
        mDraweeHierarchy.setFailureImage(drawable);
    }
    public void setFailureImage(@DrawableRes int resourceId) {
        mDraweeHierarchy.setFailureImage(resourceId);
    }

    public void setCircle(boolean circle) {
        ensureRoundingParams();
        mDraweeHierarchy.getRoundingParams().setRoundAsCircle(circle);
    }
    public void setCornerRadius(@Dimension float radius) {
        ensureRoundingParams();
        mDraweeHierarchy.getRoundingParams().setCornersRadius(radius);
    }
    public void setCornerRadiusRes(@DimenRes int radiusRes) {
        float radius = AppUtils.getContext().getResources().getDimensionPixelSize(radiusRes);
        setCornerRadius(radius);
    }

    private void ensureRoundingParams() {
        if (mDraweeHierarchy.getRoundingParams() == null) {
            mDraweeHierarchy.setRoundingParams(new RoundingParams());
        }
    }

    public void setResize(boolean resize) {
        this.mEnableResize = resize;
    }


    @Override
    public void setImageURI(Uri uri, @Nullable Object callerContext) {
        if (!mEnableResize) {
            loadImageURI(uri, callerContext, -1, -1);
        } else {
            int width = getViewWidth();
            int height = getViewHeight();
            if (width == 0 || height == 0) {
                getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        getViewTreeObserver().removeOnPreDrawListener(this);
                        loadImageURI(uri, callerContext, getViewWidth(), getViewHeight());
                        return true;
                    }
                });
            } else {
                loadImageURI(uri, callerContext, width, height);
            }
        }
    }
    private void loadImageURI(Uri uri, @Nullable Object callerContext, int width, int height) {
        // 对URL做格式转换、尺寸限制等处理
        uri = Uri.parse(ImageUtils.format(uri.toString(), DisplayInfo.getWidthPixels()));

        ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(uri);
        imageRequestBuilder.setProgressiveRenderingEnabled(true);

        imageRequestBuilder.setRotationOptions(RotationOptions.autoRotateAtRenderTime());
        if (width > 0 && height > 0) {
            imageRequestBuilder.setResizeOptions(new ResizeOptions(width, height));
        }

        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder()
                .setCallerContext(callerContext)
                .setImageRequest(imageRequestBuilder.build())
                .setOldController(getController())
                .setAutoPlayAnimations(true)  // 自动播放动态图
                .setTapToRetryEnabled(true)  // 在加载失败时，可以点击重新加载
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id,
                                                ImageInfo imageInfo,
                                                Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        onUpdateImage(imageInfo);
                    }
                });
        if (AppUtils.isMainThread()) {
            setController(controller.build());
        } else {
            Observable.just(0)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(it -> setController(controller.build()));
        }
    }

    protected void onUpdateImage(ImageInfo info) {
    }

    private int getViewWidth() {
        int width = getWidth();
        if (width <= 0) {
            width = getMeasuredWidth();
        }
        return width;
    }
    private int getViewHeight() {
        int height = getHeight();
        if (height <= 0) {
            height = getMeasuredHeight();
        }
        return height;
    }


    /**
     * Use this method only when using this class as an ordinary ImageView.
     * @deprecated Use {@link #setController(DraweeController)} instead.
     */
    @Override
    @Deprecated
    public void setImageIcon(@Nullable Icon icon) {
        super.setImageIcon(icon);
    }
}
