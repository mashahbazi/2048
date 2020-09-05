package com.example.my2048.components;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;


public class CornerDrawable extends Drawable {
    private Paint paint;
    private Drawable icon;

    public CornerDrawable(Drawable icon,int backgroundColor) {
        this.icon = icon;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(backgroundColor);
        paint.setAntiAlias(true);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        RectF rectF = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
        int cornersRadius = 25;
        canvas.drawRoundRect(rectF, cornersRadius, cornersRadius, paint);
        icon.setBounds(0, 0, 0, 0);
        icon.draw(canvas);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}