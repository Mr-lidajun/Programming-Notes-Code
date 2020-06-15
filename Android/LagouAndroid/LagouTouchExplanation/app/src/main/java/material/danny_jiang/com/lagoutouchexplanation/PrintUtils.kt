package material.danny_jiang.com.lagoutouchexplanation

import android.util.Log
import android.view.MotionEvent

fun printEvent(tag: String, methodName: String, event: MotionEvent) {
    when (event.action) {
        MotionEvent.ACTION_DOWN -> Log.i(tag, "$methodName:DOWN")
        MotionEvent.ACTION_MOVE -> Log.i(tag, "$methodName:MOVE")
        MotionEvent.ACTION_UP -> Log.i(tag, "$methodName:UP")
        MotionEvent.ACTION_CANCEL -> Log.i(tag, "$methodName:CANCEL")
    }
}

fun printParam(tag: String, reg: String) {
    Log.i(tag, reg)
}