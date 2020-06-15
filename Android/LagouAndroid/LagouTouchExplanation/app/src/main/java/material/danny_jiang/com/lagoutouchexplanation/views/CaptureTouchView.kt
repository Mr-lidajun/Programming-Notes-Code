package material.danny_jiang.com.lagoutouchexplanation.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import material.danny_jiang.com.lagoutouchexplanation.printEvent
import material.danny_jiang.com.lagoutouchexplanation.printParam

class CaptureTouchView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val TAG = CaptureTouchView::class.java.simpleName

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        printEvent(TAG, "dispatchTouchEvent", event)
        val result = super.dispatchTouchEvent(event)
        printParam(TAG, "dispatchTouchEvent result is $result")
        return result
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        printEvent(TAG, "onTouchEvent", event)
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(500, 300)
    }
}