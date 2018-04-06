package com.xsp.library.image;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.HashMap;

/**
 * <p>Image manager, update ImageView's src image</p>
 * <pre>
 *      invoke {@link ImageMgr#init(Context)} when your application init
 *      then, you can use api to load image, eg:
 *      ImageMgr.create()
 *          .source(mImageView, url)}
 *          .circle(Color.parseColor("#00FFFFFF"), 30)
 *          .roundRadius(100, Color.parseColor("#00FFFFFF"), 50)
 *          .placeHolder(R.mipmap.ic_launcher, null)
 *          .failHolder(R.mipmap.failed, null)
 *          .build();
 * </pre>
 */
public class ImageMgr extends AbsImageManager {
    private static HashMap<ImageView.ScaleType, ScalingUtils.ScaleType> mScaleMap = new HashMap<>();

    static {
        mScaleMap.put(ImageView.ScaleType.CENTER, ScalingUtils.ScaleType.CENTER);
        mScaleMap.put(ImageView.ScaleType.CENTER_CROP, ScalingUtils.ScaleType.CENTER_CROP);
        mScaleMap.put(ImageView.ScaleType.CENTER_INSIDE, ScalingUtils.ScaleType.CENTER_INSIDE);
        mScaleMap.put(ImageView.ScaleType.FIT_CENTER, ScalingUtils.ScaleType.FIT_CENTER);
        mScaleMap.put(ImageView.ScaleType.FIT_XY, ScalingUtils.ScaleType.FIT_XY);
        mScaleMap.put(ImageView.ScaleType.FIT_START, ScalingUtils.ScaleType.FIT_START);
        mScaleMap.put(ImageView.ScaleType.FIT_END, ScalingUtils.ScaleType.FIT_END);
        mScaleMap.put(ImageView.ScaleType.MATRIX, ScalingUtils.ScaleType.FOCUS_CROP);
    }

    /**
     * init image manager
     */
    public static void init(Context context) {
        ImagePipelineConfig config = ImagePipelineConfig
                .newBuilder(context)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(context, config);
    }

    private ImageMgr() {

    }

    /**
     * create image manager instance
     *
     * @return image manager
     */
    public static AbsImageManager create() {
        return new ImageMgr();
    }

    @Override
    public void build() {
        if (mImageView == null || !(mImageView instanceof SimpleDraweeView)) {
            throw new SecurityException("not a SimpleDraweeView!");
        }

        if (mSourceUri == null) {
            mSourceUri = Uri.parse("");
        }

        SimpleDraweeView drawView = (SimpleDraweeView) mImageView;

        ImageRequestBuilder requestBuilder = ImageRequestBuilder.newBuilderWithSource(mSourceUri)
                .setAutoRotateEnabled(true)
                .setProgressiveRenderingEnabled(true);
        if (mWidth > 0 && mHeight > 0) {
            requestBuilder.setResizeOptions(new ResizeOptions(mWidth, mHeight));
        } else if (mRatio == 0) {
            ViewGroup.LayoutParams lp = mImageView.getLayoutParams();
            if (lp != null && lp.width > 0 && lp.height > 0) {
                requestBuilder.setResizeOptions(new ResizeOptions(lp.width, lp.height));
            }
        }

        if (mRatio > 0) {
            drawView.setAspectRatio(mRatio);
        }

        RoundingParams mParams;
        if (mIsCircle) {
            mParams = RoundingParams.asCircle();
            mParams.setRoundAsCircle(true);
        } else {
            mParams = RoundingParams.fromCornersRadius(mRadius);
            mParams.setRoundAsCircle(false);
        }
        if (mBorderColor != 0) {
            mParams.setBorderColor(mBorderColor);
        }
        if (mBorderWidth > 0) {
            mParams.setBorderWidth(mBorderWidth);
        }

        GenericDraweeHierarchyBuilder hierarchyBuilder = new GenericDraweeHierarchyBuilder(drawView.getResources())
                .setFadeDuration(300)
                .setActualImageScaleType(mScaleMap.get(mScaleType))
                .setRoundingParams(mParams);
        if (mOnlyHolderId != 0) {
            hierarchyBuilder.setPlaceholderImage(mOnlyHolderId, mScaleMap.get(mOnlyScaleType));
            hierarchyBuilder.setFailureImage(mOnlyHolderId, mScaleMap.get(mOnlyScaleType));
        } else {
            if (mPlaceHolderId != 0) {
                hierarchyBuilder.setPlaceholderImage(mPlaceHolderId, mScaleMap.get(mPlaceScaleType));
            }
            if (mFailImageId != 0) {
                hierarchyBuilder.setFailureImage(mFailImageId, mScaleMap.get(mFailScaleType));
            }
        }
        if (mPressDrawable != null) {
            hierarchyBuilder.setPressedStateOverlay(mPressDrawable);
        }
        if (mDrawable != null) {
            hierarchyBuilder.setOverlay(mDrawable);
        }
        if (mDrawables != null && mDrawables.size() > 0) {
            hierarchyBuilder.setOverlays(mDrawables);
        }
        PipelineDraweeControllerBuilder controllerBuilder = Fresco.newDraweeControllerBuilder()
                .setImageRequest(requestBuilder.build())
                .setOldController(drawView.getController());
        if (mIsPlayGif) {
            controllerBuilder.setAutoPlayAnimations(true);
        }
        if (mListener != null) {
            controllerBuilder.setControllerListener(new ControllerListener<ImageInfo>() {
                @Override
                public void onSubmit(String id, Object callerContext) {
                    if (mListener != null) {
                        mListener.onStart(id, callerContext);
                    }
                }

                @Override
                public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                    if (mListener != null) {
                        AbsImageInfo info = new AbsImageInfo(imageInfo.getWidth(), imageInfo.getHeight());
                        mListener.onSuccess(id, info);
                    }
                }

                @Override
                public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
                }

                @Override
                public void onIntermediateImageFailed(String id, Throwable throwable) {
                }

                @Override
                public void onFailure(String id, Throwable throwable) {
                    if (mListener != null) {
                        mListener.onFailure(id, throwable);
                    }
                }

                @Override
                public void onRelease(String id) {
                    if (mListener != null) {
                        mListener.onRelease(id);
                    }
                }
            });
        }

        drawView.setHierarchy(hierarchyBuilder.build());
        drawView.setController(controllerBuilder.build());
    }

}
