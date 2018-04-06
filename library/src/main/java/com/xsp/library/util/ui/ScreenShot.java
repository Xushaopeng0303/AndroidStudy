package com.xsp.library.util.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.xsp.library.LibraryBuildConfig;
import com.xsp.library.util.file.FileUtil;
import com.xsp.library.util.image.BitmapUtil;
import com.xsp.library.util.image.ImageConstant;
import com.xsp.library.util.time.DateConstant;
import com.xsp.library.util.time.TimeUtil;

import java.io.File;

/**
 * Screen Shot Manager
 */
public class ScreenShot {
    private View mScreenShotView;
    private int mQuality;
    private int mCompressSize;
    private String mFileName;
    private String mExtensionName;
    private File mFileDir;

    public ScreenShot(Context context) {
        mQuality = 80;
        mCompressSize = ImageConstant.MB;
        mFileName = TimeUtil.formatTime(DateConstant.DATE_FORMAT_3);
        mExtensionName = ImageConstant.EXTENSION_NAME_JPG;
        mFileDir = FileUtil.getSystemPictureFile(context);
    }

    public static ScreenShot create(Context context) {
        return new ScreenShot(context);
    }

    public ScreenShot setScreenShotView(View view) {
        this.mScreenShotView = view;
        return this;
    }

    public ScreenShot setQuality(int quality) {
        this.mQuality = quality;
        return this;
    }

    public ScreenShot setCompressSize(int size) {
        this.mCompressSize = size;
        return this;
    }

    public ScreenShot setFileName(String fileName) {
        this.mFileName = fileName;
        return this;
    }

    public ScreenShot setExtensionName(String extensionName) {
        this.mExtensionName = extensionName;
        return this;
    }

    public ScreenShot setFileDir(File fileDir) {
        this.mFileDir = fileDir;
        return this;
    }

    public String save() {
        if (mScreenShotView == null || mFileName.contains("/")) {
            if (LibraryBuildConfig.getIns().isDebug()) {
                throw new NullPointerException("View == null or file name contains '/'");
            }
            return "";
        }
        Bitmap bitmap = BitmapUtil.getBitmap(mScreenShotView);
        File absolutePath;
        try {
            absolutePath = new File(mFileDir + File.separator + mFileName + mExtensionName);
        } catch (Exception e) {
            return "";
        }
        return BitmapUtil.compressBitmap(bitmap, mQuality, mCompressSize, absolutePath);
    }

}
