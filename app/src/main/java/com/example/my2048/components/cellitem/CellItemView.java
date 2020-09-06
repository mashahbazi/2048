package com.example.my2048.components.cellitem;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.widget.AppCompatTextView;

import com.example.my2048.helpers.ColorHelper;
import com.example.my2048.helpers.MathUtils;
import com.example.my2048.R;

public class CellItemView extends AppCompatTextView {
    private int lastNum = 0;
    private int currentNum = 0;

    public CellItemView(Context context) {
        super(context);
        init(context);
    }

    public CellItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CellItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        currentNum = Integer.parseInt(context.getString(R.string.start_num));
    }

    public void setValue(int num) {
        lastNum = currentNum;
        currentNum = num;
        int pow = MathUtils.getInstance().log(num, 2);
        this.setBackgroundDrawable(ColorHelper.getDrawable(this.getContext(), pow));
        this.setText(String.valueOf(num));
        if (currentNum > lastNum) {
            this.showAnimation();
        }
    }

    private void showAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bonuse_anim);
        this.startAnimation(animation);
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
}
