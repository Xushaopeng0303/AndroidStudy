package com.xsp.library.util.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.xsp.library.LibraryBuildConfig;
import com.xsp.library.util.ExceptionUtil;
import com.xsp.library.util.app.DeviceUtil;
import com.xsp.library.util.time.TimeUtil;

import java.io.File;

/**
 * Screen Shot tool
 */
public class ScreenShotUtil {
    private View mScreenShotView;
    private int mQuality;
    private int mCompressSize;
    private String mFileName;
    private String mExtensionName;
    private File mFileDir;

    public ScreenShotUtil(Context context) {
        mQuality = 80;
        mCompressSize = ImageConstant.MB;
        mFileName = TimeUtil.formatTime("yyyy-MM-dd HH:mm:ss");
        mExtensionName = ImageConstant.EXTENSION_NAME_JPG;
        mFileDir = DeviceUtil.getSystemPictureFile(context);
    }

    public static ScreenShotUtil create(Context context) {
        return new ScreenShotUtil(context);
    }

    public ScreenShotUtil setScreenShotView(View view) {
        this.mScreenShotView = view;
        return this;
    }

    public ScreenShotUtil setQuality(int quality) {
        this.mQuality = quality;
        return this;
    }

    public ScreenShotUtil setCompressSize(int size) {
        this.mCompressSize = size;
        return this;
    }

    public ScreenShotUtil setFileName(String fileName) {
        this.mFileName = fileName;
        return this;
    }

    public ScreenShotUtil setExtensionName(String extensionName) {
        this.mExtensionName = extensionName;
        return this;
    }

    public ScreenShotUtil setFileDir(File fileDir) {
        this.mFileDir = fileDir;
        return this;
    }

    public String save() {
        if (null == mScreenShotView || mFileName.contains("/")) {
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
            ExceptionUtil.printStackTrace(e);
            return "";
        }
        return BitmapUtil.compressBitmap(bitmap, mQuality, mCompressSize, absolutePath);
    }

}
