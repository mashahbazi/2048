package com.example.my2048;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class CustomTextView extends AppCompatTextView {
    private int lastNum = 0;

    public CustomTextView(Context context) {
        super(context);
        init(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        lastNum = Integer.valueOf(context.getString(R.string.start_num));
    }

    public void setText(int num, int a) {
        lastNum = num;
        int pow = MathUtils.getInstance().log(num, 2);
        this.setBackgroundDrawable(BackgroundColors.getDrawable(this.getContext(), pow));
        this.setText(String.valueOf(num));
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        new Handler(Looper.getMainLooper())
                .post(() -> {
                    if (text.toString().equals("0"))
                        super.setText("", type);
                    else {
                        super.setText(text, type);
                    }
                });
    }

    public int getLastNum() {
        return lastNum;
    }

    public void addNewNum() {
        int rand = MathUtils.getInstance().getRoundInt(4);
        if (rand % 4 == 0)
            setText(4, 0);
        else
            setText(2, 0);
    }
}
