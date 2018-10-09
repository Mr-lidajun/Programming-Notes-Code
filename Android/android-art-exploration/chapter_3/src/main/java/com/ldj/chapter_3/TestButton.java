package com.ldj.chapter_3;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author lidajun
 */
public class TestButton extends TextView {
    private static final String TAG = TestButton.class.getSimpleName();
    // 分别记录上次滑动的坐标
    private int mScaledTouchSlop;
    private int mLastX = 0;
    private int mLastY = 0;

    public TestButton(Context context) {
        this(context, null);
    }

    public TestButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {

    }
}
