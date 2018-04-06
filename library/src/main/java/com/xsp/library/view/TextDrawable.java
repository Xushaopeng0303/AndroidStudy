package com.xsp.library.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;

/**
 * Text information generation Drawable
 */
public class TextDrawable extends ShapeDrawable {
    private static final float SHADE_FACTOR = 0.9f;

    private final Paint textPaint;
    private final Paint borderPaint;
    private final String text;
    private final Shape shape;

    private final int width;
    private final int height;
    private final int color;
    private final int textSize;
    private final float radius;
    private final int borderThickness;

    private TextDrawable(Builder builder) {
        super(builder.shape);

        // shape properties
        color  = builder.color;
        shape  = builder.shape;
        height = builder.height;
        width  = builder.width;
        radius = builder.radius;
        borderThickness = builder.borderThickness;

        // text
        text     = builder.toUpperCase ? builder.text.toUpperCase() : builder.text;
        textSize = builder.textSize;

        textPaint = new Paint();
        textPaint.setColor(builder.textColor);
        textPaint.setAntiAlias(true);
        textPaint.setFakeBoldText(builder.isBold);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTypeface(builder.font);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStrokeWidth(builder.borderThickness);

        // border paint settings
        borderPaint = new Paint();
        borderPaint.setColor(getDarkerShade(color));
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderThickness);

        // drawable paint color
        Paint paint = getPaint();
        paint.setColor(color);
    }

    private int getDarkerShade(int color) {
        return Color.rgb((int) (SHADE_FACTOR * Color.red(color)),
                (int) (SHADE_FACTOR * Color.green(color)),
                (int) (SHADE_FACTOR * Color.blue(color)));
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Rect r = getBounds();


        // draw border
        if (borderThickness > 0) {
            RectF rect = new RectF(getBounds());
            rect.inset(borderThickness / 2, borderThickness / 2);

            if (shape instanceof OvalShape) {
                canvas.drawOval(rect, borderPaint);
            } else if (shape instanceof RoundRectShape) {
                canvas.drawRoundRect(rect, radius, radius, borderPaint);
            } else {
                canvas.drawRect(rect, borderPaint);
            }
        }

        int count = canvas.save();
        canvas.translate(r.left, r.top);

        // draw text
        int width    = this.width < 0 ? r.width() : this.width;
        int height   = this.height < 0 ? r.height() : this.height;
        int fontSize = this.textSize < 0 ? (Math.min(width, height) / 2) : this.textSize;
        textPaint.setTextSize(fontSize);
        canvas.drawText(text, width / 2, height / 2 - ((textPaint.descent() + textPaint.ascent()) / 2), textPaint);

        canvas.restoreToCount(count);

    }

    @Override
    public void setAlpha(int alpha) {
        textPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        textPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return width;
    }

    @Override
    public int getIntrinsicHeight() {
        return height;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        // background
        private int color;
        private int borderThickness;
        private int width;
        private int height;
        private float radius;
        private Shape shape;

        // font
        private String text;
        private int textColor;
        private int textSize;
        private boolean isBold;
        private boolean toUpperCase;
        private Typeface font;

        private Builder() {
            color = Color.GRAY;
            borderThickness = 0;
            width = -1;
            height = -1;
            radius = 0;
            shape = new RectShape();

            text = "";
            textColor = Color.WHITE;
            textSize = -1;
            isBold = false;
            toUpperCase = false;
            font = Typeface.create("sans-serif-light", Typeface.NORMAL);
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder textColor(int color) {
            this.textColor = color;
            return this;
        }

        public Builder textSize(int size) {
            this.textSize = size;
            return this;
        }

        public Builder bold() {
            this.isBold = true;
            return this;
        }

        public Builder toUpperCase() {
            this.toUpperCase = true;
            return this;
        }

        public Builder useFont(Typeface font) {
            this.font = font;
            return this;
        }


        public Builder color(int color) {
            this.color = color;
            return this;
        }


        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder borderWith(int thickness) {
            this.borderThickness = thickness;
            return this;
        }

        public TextDrawable rect() {
            this.shape = new RectShape();
            return build();
        }

        public TextDrawable round() {
            this.shape = new OvalShape();
            return build();
        }

        public TextDrawable roundRect(int radius) {
            this.radius = radius;
            float[] radii = {radius, radius, radius, radius, radius, radius, radius, radius};
            this.shape = new RoundRectShape(radii, null, null);
            return build();
        }

        private TextDrawable build() {
            return new TextDrawable(this);
        }
    }


}