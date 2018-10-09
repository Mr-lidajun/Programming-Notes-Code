package com.ldj.chapter_3;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
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
        mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        Log.d(TAG, "sts:" + mScaledTouchSlop);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                int translationX = (int) (this.getTranslationX() + deltaX);
                int translationY = (int) (this.getTranslationY() + deltaY);
                this.setTranslationX(translationX);
                this.setTranslationY(translationY);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }
}
