package material.danny_jiang.com.lagoucustomizedview.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.annotation.IntRange
import androidx.core.view.ViewCompat
import kotlin.math.min


class PieImageView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val MAX_PROGRESS = 100
    private var mArcPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = dpToPixel(0.1f, getContext())
        color = Color.RED
    }
    private var mCirclePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = dpToPixel(2f, getContext())
        color = Color.argb(120, 0xff, 0xff, 0xff)
    }
    private var mBound: RectF = RectF()
    private var mProgress = 0

    fun setProgress(@IntRange(from = 0, to = 100) progress: Int) {
        mProgress = progress
        ViewCompat.postInvalidateOnAnimation(this);
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        // 判断是wrap_content的测量模式
        // 判断是wrap_content的测量模式
        if (MeasureSpec.AT_MOST == widthMode || MeasureSpec.AT_MOST == heightMode) {
            val measuredWidth = MeasureSpec.getSize(widthMeasureSpec)
            val measuredHeight = MeasureSpec.getSize(heightMeasureSpec)
            // 将宽高设置为传入宽高的最小值
            val size = if (measuredWidth > measuredHeight) measuredHeight else measuredWidth
            // 调用setMeasuredDimension设置View实际大小
            setMeasuredDimension(size, size)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val min = min(w, h)
        val max = w + h - min
        val r = min(w, h) / 3
        mBound[(max shr 1) - r.toFloat(), (min shr 1) - r.toFloat(), (max shr 1) + r.toFloat()] =
            (min shr 1) + r.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mProgress != MAX_PROGRESS && mProgress != 0) {
            val angle = mProgress * 360f / MAX_PROGRESS
            canvas.drawArc(mBound, 270f, angle, true, mArcPaint)
            canvas.drawCircle(mBound.centerX(), mBound.centerY(), mBound.height() / 2, mCirclePaint)
        }
    }

    private var scale: Float = 0f
    fun dpToPixel(dp: Float, context: Context): Float {
        if (scale == 0f) {
            scale = context.resources.displayMetrics.density
        }
        return (dp * scale)
    }
}