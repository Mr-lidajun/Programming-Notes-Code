package material.danny_jiang.com.lagoucustomizedview.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import kotlin.math.max

class FlowLayout(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {
    // 存放容器中所有的View
    private val mAllViews = mutableListOf<MutableList<View>>()
    // 存放每一行最高View的高度
    private val mPerLineMaxHeight = mutableListOf<Int>()

    override fun generateLayoutParams(p: LayoutParams): LayoutParams {
        super.generateLayoutParams(p)
        return MarginLayoutParams(p)
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 获得宽高的测量模式和测量值
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        // 获得容器中子View的个数
        val childCount = childCount
        // 记录每一行View的总宽度
        var totalLineWidth = 0
        //记录每一行最高View的高度
        var perLineMaxHeight = 0
        // 记录当前ViewGroup的总高度
        var totalHeight = 0
        for(i in 0 until childCount) {
            val childView = getChildAt(i)
            // 对子view进行测量
            measureChild(childView, widthMeasureSpec, heightMeasureSpec)
            val lp: MarginLayoutParams = childView.layoutParams as MarginLayoutParams
            //获得子View的测量宽度
            val childWidth = childView.measuredWidth
            //获得子View的测量高度
            val childHeight = childView.measuredHeight
            if (totalLineWidth + childWidth > widthSize) {
                // 统计总高度
                totalHeight += perLineMaxHeight
                //开启新的一行
                totalLineWidth = childWidth
                perLineMaxHeight = childHeight
            } else {
                //记录每一行的总宽度
                totalLineWidth += childWidth
                // 比较每一行最高的View
                perLineMaxHeight = max(perLineMaxHeight, childHeight)
            }
            //当该View已是最后一个View时，将该行最大高度添加到totalHeight中
            if (i == childCount - 1) {
                totalHeight += perLineMaxHeight
            }
        }

        //如果高度的测量模式是EXACTLY，则高度用测量值，否则用计算出来的总高度（这时高度的设置为wrap_content）
        heightSize = if (heightMode == MeasureSpec.EXACTLY) heightSize else totalHeight
        setMeasuredDimension(widthSize, heightSize)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        mAllViews.clear()
        mPerLineMaxHeight.clear()
        //存放每一行的子View
        var lineViews = mutableListOf<View>()
        //记录每一行已存放View的总宽度
        var totalLineWidth = 0
        //记录每一行最高View的高度
        var lineMaxHeight = 0
        /****遍历所有View，将View添加到List<List<View>>集合中**********/
        //获得子View的总个数
        val childCount = childCount
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            val lp = childView.layoutParams as MarginLayoutParams
            val childWidth = childView.measuredWidth + lp.leftMargin + lp.rightMargin
            val childHeight = childView.measuredHeight + lp.topMargin + lp.bottomMargin
            if (totalLineWidth + childWidth > width) {
                mAllViews.add(lineViews)
                mPerLineMaxHeight.add(lineMaxHeight)
                //开启新的一行
                totalLineWidth = 0
                lineMaxHeight = 0
                lineViews = mutableListOf<View>()
            }
            totalLineWidth += childWidth
            lineViews.add(childView)
            lineMaxHeight = max(lineMaxHeight, childHeight)
        }
        //单独处理最后一行
        mAllViews.add(lineViews)
        mPerLineMaxHeight.add(lineMaxHeight);

        /************遍历集合中的所有View并显示出来************/
        //表示一个View和父容器左边的距离
        var left = 0
        var top = 0
        for (i in 0 until mAllViews.size) {
            //获得每一行的所有View
            lineViews = mAllViews[i]
            lineMaxHeight = mPerLineMaxHeight[i]
            for (i in 0 until lineViews.size) {
                val childView = lineViews[i]
                val lp: MarginLayoutParams = childView.layoutParams as MarginLayoutParams
                val leftChild = left + lp.leftMargin
                val topChild = top + lp.topMargin
                val rightChild = leftChild + childView.measuredWidth
                val bottomChild = topChild + childView.measuredHeight
                //四个参数分别表示View的左上角和右下角
                childView.layout(leftChild, topChild, rightChild, bottomChild)
                left += lp.leftMargin + childView.measuredWidth + lp.rightMargin
            }
            left = 0
            top += lineMaxHeight
        }
    }

}
