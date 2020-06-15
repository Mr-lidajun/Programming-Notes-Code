package material.danny_jiang.com.lagoutouchexplanation.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView
import material.danny_jiang.com.lagoutouchexplanation.printEvent
import material.danny_jiang.com.lagoutouchexplanation.printParam

class MyScrollView(context: Context, attrs: AttributeSet) : ScrollView(context, attrs) {
    private val TAG = MyScrollView::class.java.simpleName

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        printEvent(TAG, "onTouchEvent", event)
        val intercepted = super.onInterceptTouchEvent(event)
        printParam(TAG, "MyScrollView intercepted is $intercepted")
        return intercepted
    }

}