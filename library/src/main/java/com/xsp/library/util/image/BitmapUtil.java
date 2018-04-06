package com.xsp.library.util.image;

import android.graphics.Bitmap;
import android.view.View;

import com.xsp.library.util.BaseUtil;
import com.xsp.library.util.ExceptionUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Bitmap util
 */
public class BitmapUtil extends BaseUtil {
    /**
     * get bitmap by view
     */
    public static Bitmap getBitmap(View view) {
        if (view == null) {
            return null;
        }
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        WeakReference<Bitmap> wBitmap = new WeakReference<>(bitmap);
        view.setDrawingCacheEnabled(false);
        return wBitmap.get();
    }

    public static String compressBitmap(Bitmap rawBitmap, int quality, int size, File file) {
        if (rawBitmap == null || quality < 0 || size < 0) {
            return "";
        }
        String compressedPath = "";
        try {
            while (true) {
                FileOutputStream out = new FileOutputStream(file);
                compressedPath = file.getAbsolutePath();
                rawBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
                out.close();
                long compressedLength = file.length();
                if (compressedLength > size) {
                    quality -= 10;
                    if (quality <= 0) {
                        break;
                    }
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            ExceptionUtil.printStackTrace(e);
        }
        recycle(rawBitmap);
        return compressedPath;
    }

    public static void recycle(Bitmap bitmap) {
        if (bitmap != null) {
            bitmap.recycle();
            System.gc();
        }
    }
}
