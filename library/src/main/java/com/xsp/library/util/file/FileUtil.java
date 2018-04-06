package com.xsp.library.util.file;

import android.content.Context;
import android.os.Environment;

import com.xsp.library.util.BaseUtil;

import java.io.Closeable;
import java.io.File;

/**
 * File util, support file operations:
 */
public class FileUtil extends BaseUtil {

    public static File getSystemPictureFile(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    /**
     * close a io safely
     *
     * @param closeable closeable io
     */
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
