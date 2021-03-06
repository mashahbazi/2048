package com.example.my2048.helpers;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.my2048.R;
import com.example.my2048.components.CornerDrawable;

public class ColorHelper {
    private static CornerDrawable[] list = new CornerDrawable[21];

    public static CornerDrawable getDrawable(Context context, int num) {
        if (list[num] != null)
            return list[num];
        else
            return addDrawable(context, num);
    }

    private static CornerDrawable addDrawable(Context context, int num) {
        CornerDrawable cornerDrawable = new CornerDrawable(context.getResources().getDrawable(R.drawable.text_drawable),
                ContextCompat.getColor(context, context.getResources().getIdentifier("color_" + num, "color", context.getPackageName())));
        list[num] = cornerDrawable;
        return cornerDrawable;
    }
}
