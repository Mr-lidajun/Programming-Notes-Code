package material.danny_jiang.com.lagoutouchexplanation.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import material.danny_jiang.com.lagoutouchexplanation.printEvent

class DownInterceptGroup(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private val TAG = DownInterceptGroup::class.java.simpleName


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        printEvent(TAG, "dispatchTouchEvent", ev);
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        printEvent(TAG, "onInterceptTouchEvent", ev);
        return super.onInterceptTouchEvent(ev)
    }
}