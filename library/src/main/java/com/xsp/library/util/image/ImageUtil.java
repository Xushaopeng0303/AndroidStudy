package com.xsp.library.util.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.text.TextUtils;

import com.xsp.library.util.BaseUtil;
import com.xsp.library.util.ExceptionUtil;
import com.xsp.library.util.file.FileUtil;
import com.xsp.library.util.ui.DimenUtil;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Image util
 */
public class ImageUtil extends BaseUtil {

    public static int[] getImageSize(String srcPath) {
        int[] size = new int[2];
        if (TextUtils.isEmpty(srcPath)) {
            return size;
        }
        BitmapFactory.Options newOpts = getOptions(srcPath);
        size[0] = newOpts.outWidth;
        size[1] = newOpts.outHeight;
        return size;
    }

    public static byte[] getImageByPath(String srcPath, float factor, int width, int height, int quality) {
        if (TextUtils.isEmpty(srcPath)) {
            return null;
        }
        Bitmap srcBitmap = null;
        Bitmap dstBitmap = null;
        try {
            int realWidth;
            int realHeight;
            if (factor == 0) {
                realWidth = width;
                realHeight = height;
            } else {
                realWidth = (int) (DimenUtil.getWindowWidth() * factor);
                realHeight = (int) (DimenUtil.getWindowHeight() * factor);
            }

            BitmapFactory.Options newOpts = getOptions(srcPath);
            float w = newOpts.outWidth;
            float h = newOpts.outHeight;
            float hh = realHeight;
            float ww = realWidth;
            float be = 1;
            if (w >= h && w > ww) {
                be = (newOpts.outWidth / ww);
            } else if (w < h && h > hh) {
                be = (newOpts.outHeight / hh);
            }
            be = (float) Math.ceil(be);
            newOpts.inSampleSize = (int) be;
            srcBitmap = BitmapFactory.decodeFile(srcPath, newOpts);
            dstBitmap = ImageUtil.rotateBitmap(srcPath, srcBitmap);
            if (dstBitmap != null) {
                srcBitmap = dstBitmap;
            }

            return compressImage(srcBitmap, 0, quality);
        } catch (Exception e) {
            ExceptionUtil.printStackTrace(e);
        } finally {
            BitmapUtil.recycle(srcBitmap);
            BitmapUtil.recycle(dstBitmap);
        }

        return null;
    }

    /**
     * compress image
     *
     * @param bitmap  compressed bitmap
     * @param size    compressed size, (unit : kb)
     * @param quality compressed quality, must be between 0 and 100.
     */
    public static byte[] compressImage(Bitmap bitmap, int size, int quality) {
        if (null == bitmap || quality < 0 || quality > 100) {
            return null;
        }

        byte[] byteArray = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
            int options = 70;
            while (options > 0 && byteArrayOutputStream.toByteArray().length / 1024 > size) {
                byteArrayOutputStream.reset();
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, byteArrayOutputStream);
                options -= 10;
            }
            byteArray = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            ExceptionUtil.printStackTrace(e);
        } finally {
            FileUtil.close(byteArrayOutputStream);
        }
        return byteArray;
    }

    public static Bitmap rotateBitmap(String path, Bitmap bitmap) {
        if (null == bitmap || TextUtils.isEmpty(path)) {
            return null;
        }
        int angle = 0;
        ExifInterface exif;
        try {
            exif = new ExifInterface(path);
        } catch (IOException e) {
            ExceptionUtil.printStackTrace(e);
            exif = null;
        }
        if (null != exif) {
            // 读取图片中相机方向信息
            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            // 计算旋转角度
            switch (ori) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    angle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    angle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    angle = 270;
                    break;
                default:
                    angle = 0;
                    break;
            }
        }
        if (angle != 0) {
            // 旋转图片
            Matrix matrix = new Matrix();
            matrix.postRotate(angle);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
        }

        return null;
    }

    public static String saveImage(String imagePath, String directory, String imageName, float factor) {
        if (TextUtils.isEmpty(imagePath) || !new File(imagePath).exists()) {
            return "";
        }
        imageName = TextUtils.isEmpty(imageName) ? System.currentTimeMillis() + "" : imagePath;
        File file = new File(directory, imageName + ".jpg");
        byte[] bytes = ImageUtil.getImageByPath(imagePath, factor, 0, 0, 0);
        if (null == bytes) {
            return "";
        }
        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            bufferedOutputStream.write(bytes);
            bufferedOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileUtil.close(bufferedOutputStream);
        }
        return file.getAbsolutePath();
    }


    private static BitmapFactory.Options getOptions(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, newOpts);
        newOpts.inJustDecodeBounds = false;
        return newOpts;
    }

}
