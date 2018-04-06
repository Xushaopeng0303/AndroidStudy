package com.xsp.library.image;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

/**
 * Image manager
 * update ImageView's src image
 */
public abstract class AbsImageManager {

    protected Uri mSourceUri;
    protected ImageView mImageView;
    protected int mWidth;
    protected int mHeight;
    protected float mRatio;
    protected boolean mIsCircle;
    protected boolean mIsPlayGif;
    protected float mRadius;
    protected int mBorderColor;
    protected int mBorderWidth;
    protected ImageView.ScaleType mScaleType;
    protected ImageView.ScaleType mPlaceScaleType;
    protected ImageView.ScaleType mFailScaleType;
    protected ImageView.ScaleType mOnlyScaleType;
    protected int mPlaceHolderId;
    protected int mFailImageId;
    protected int mOnlyHolderId;
    protected Drawable mPressDrawable;
    protected AbsImageListener mListener;
    protected List<Drawable> mDrawables;
    protected Drawable mDrawable;

    protected AbsImageManager() {
        mScaleType = ImageView.ScaleType.FIT_XY;
        mPlaceScaleType = ImageView.ScaleType.FIT_XY;
        mFailScaleType = ImageView.ScaleType.FIT_XY;
        mOnlyScaleType = ImageView.ScaleType.FIT_XY;
    }

    /**
     * see {@link AbsImageManager#source(ImageView, String, ImageView.ScaleType)}
     */
    public AbsImageManager source(ImageView imageView, String url) {
        return source(imageView, url, null);
    }

    /**
     * @param imageView the ImageView for show
     * @param url       Uri
     *                  <p>&nbsp;&nbsp;&nbsp;&nbsp;default value ImageView.ScaleType.FIT_XY</p>
     * @param type      scale type
     *                  <p>&nbsp;&nbsp;&nbsp;&nbsp;http - {@code https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png}</p>
     *                  <p>&nbsp;&nbsp;&nbsp;&nbsp;local file - {@code file:///sdcard/test.jpg} </p>
     *                  <p>&nbsp;&nbsp;&nbsp;&nbsp;asset - {@code asset:///test.jpg}</p>
     *                  <p>&nbsp;&nbsp;&nbsp;&nbsp;res - {@code res:/// + R.mipmap.test}</p>
     *                  <p>&nbsp;&nbsp;&nbsp;&nbsp;provider - {@code content://media/external/images/media/501500}</p>
     * @return this
     */
    public AbsImageManager source(ImageView imageView, String url, ImageView.ScaleType type) {
        mImageView = imageView;
        if (url == null) {
            url = "";
        }
        if (url.startsWith("/")) {
            int index = url.lastIndexOf("/");
            if (index < url.length() - 1) {
                url = "file://" + url.substring(0, index + 1) + Uri.encode(url.substring(index + 1));
            }
        }
        mSourceUri = Uri.parse(url);
        if (type != null) {
            mScaleType = type;
        }
        return this;
    }

    /**
     * @see AbsImageManager#size(int, int, float)
     */
    public AbsImageManager size(int width, int height) {
        return size(width, height, 0);
    }

    /**
     * <p>set image size, we use width and height, but these two must be great than 0.</p>
     * <p>or we use the {@link View#getLayoutParams()}'s width and height, must be great then 0 too</p>
     * <p>or we use pic original size</p>
     *
     * @param width  width
     * @param height height
     * @param ratio  w/h ratio
     * @return this
     */
    public AbsImageManager size(int width, int height, float ratio) {
        mWidth = width;
        mHeight = height;
        mRatio = ratio;
        return this;
    }

    /**
     * <p>set image border in circle</p>
     * <p>default border color: {@link Color#RED}</p>
     *
     * @return this
     */
    public AbsImageManager circle() {
        return circle(Color.RED, 2);
    }

    /**
     * set image border in circle
     *
     * @param borderColor border color not resId
     * @param borderWidth border width
     * @return this
     */
    public AbsImageManager circle(int borderColor, int borderWidth) {
        mIsCircle = true;
        mBorderColor = borderColor;
        mBorderWidth = borderWidth;
        return this;
    }

    /**
     * <p>set image border in round rect</p>
     * <p>default border color: {@link Color#RED}</p>
     * <p>default border: 2</p>
     *
     * @param radius radius of rect
     * @return this
     */
    public AbsImageManager roundRadius(float radius) {
        return roundRadius(radius, Color.RED, 2);
    }

    /**
     * set image border in round rect
     *
     * @param radius      radius of rect
     * @param borderColor border color not resId
     * @param borderWidth border width
     * @return this
     */
    public AbsImageManager roundRadius(float radius, int borderColor, int borderWidth) {
        mIsCircle = false;
        mRadius = radius;
        mBorderColor = borderColor;
        mBorderWidth = borderWidth;
        return this;
    }

    /**
     * @param placeId   the res id of place holder image
     * @param scaleType scale type
     *                  <p>&nbsp;&nbsp;&nbsp;&nbsp;ImageView.ScaleType.FIT_XY</p>
     * @return this
     */
    public AbsImageManager placeHolder(int placeId, ImageView.ScaleType scaleType) {
        mPlaceHolderId = placeId;
        if (scaleType != null) {
            mPlaceScaleType = scaleType;
        }
        return this;
    }

    /**
     * @param failId    the res id of request failed image
     * @param scaleType scale type
     *                  <p>&nbsp;&nbsp;&nbsp;&nbsp;ImageView.ScaleType.FIT_XY</p>
     * @return this
     */
    public AbsImageManager failHolder(int failId, ImageView.ScaleType scaleType) {
        mFailImageId = failId;
        if (scaleType != null) {
            mFailScaleType = scaleType;
        }
        return this;
    }

    /**
     * @param holderId  the res id of request place holder and failed image
     * @param scaleType scale type
     *                  <p>&nbsp;&nbsp;&nbsp;&nbsp;ImageView.ScaleType.FIT_XY</p>
     * @return this
     */
    public AbsImageManager imageHolder(int holderId, ImageView.ScaleType scaleType) {
        mOnlyHolderId = holderId;
        if (scaleType != null) {
            mOnlyScaleType = scaleType;
        }
        return this;
    }

    /**
     * set press drawable of imageView
     *
     * @param drawable press drawable
     * @return this
     */
    public AbsImageManager pressState(Drawable drawable) {
        mPressDrawable = drawable;
        return this;
    }

    /**
     * set image operate listener
     *
     * @param listener operate listener
     * @return this
     * @see AbsImageListener
     */
    public AbsImageManager listener(AbsImageListener listener) {
        mListener = listener;
        return this;
    }

    /**
     * set whether to play gif
     *
     * @param playGif whether to play gif
     * @return this
     * @see AbsImageListener
     */
    public AbsImageManager playGif(boolean playGif) {
        mIsPlayGif = playGif;
        return this;
    }

    /**
     * Sets a single overlay.
     *
     * @param drawable overlay drawable
     * @return modified instance of this builder
     */
    public AbsImageManager overLay(Drawable drawable) {
        mDrawable = drawable;
        return this;
    }

    /**
     * Sets the overlays.
     *
     * Overlays are drawn in list order after the backgrounds and the rest of the hierarchy. The
     * last overlay will be drawn at the top.
     *
     * @param drawables overlay drawables
     * @return modified instance of this builder
     */
    public AbsImageManager overLays(List<Drawable> drawables) {
        mDrawables = drawables;
        return this;
    }


    /**
     * describe image info by width && height
     */
    public class AbsImageInfo {
        public int width;
        public int height;

        public AbsImageInfo(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    /**
     * <pre>
     * provide callbacks below:
     * 1. {@link AbsImageListener#onStart(String, Object)}
     * 2. {@link AbsImageListener#onSuccess(String, AbsImageInfo)}
     * 3. {@link AbsImageListener#onFailure(String, Throwable)}
     * 4. {@link AbsImageListener#onRelease(String)}
     * </pre>
     */
    public interface AbsImageListener {
        /**
         * Called before the image request is submitted.
         * <p> IMPORTANT: It is not safe to reuse the controller from within this callback!
         *
         * @param id            controller id
         * @param callerContext caller context
         */
        void onStart(String id, Object callerContext);

        /**
         * Called after the final image has been set.
         *
         * @param id        controller id
         * @param imageInfo image info
         * @see AbsImageInfo
         */
        void onSuccess(String id, AbsImageInfo imageInfo);

        /**
         * Called after the fetch of the final image failed.
         *
         * @param id        controller id
         * @param throwable failure cause
         */
        void onFailure(String id, Throwable throwable);

        /**
         * Called after the controller released the fetched image.
         * <p> IMPORTANT: It is not safe to reuse the controller from within this callback!
         *
         * @param id controller id
         */
        void onRelease(String id);
    }

    /**
     * load build params and start load image uri
     */
    public abstract void build();
}
