package com.xsp.library.standard;

import android.graphics.drawable.Drawable;

/**
 * The ability of a base title class
 */
public interface ITitleActivity {
    void setTitle(CharSequence title);
    void setLeftText(CharSequence leftTitle);
    void setRightText(CharSequence rightText);
    void setLeftIcon(Drawable leftIcon);
    void setRightIcon(Drawable rightIcon);
}
