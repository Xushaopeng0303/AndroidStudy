package com.xsp.library.util;

import com.xsp.library.LibraryBuildConfig;

/**
 * Exception management util
 */
public class ExceptionUtil extends BaseUtil {

    /**
     * Writes a printable representation of this {@code Throwable}'s stack trace
     */
    public static void printStackTrace(Exception e) {
        if (LibraryBuildConfig.getIns().isDebug()) {
            e.printStackTrace();
        }
    }
}
