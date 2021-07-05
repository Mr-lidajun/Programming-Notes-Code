package material.danny_jiang.com.lagoucustomizedview.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import material.danny_jiang.com.lagoucustomizedview.R

class CustomToolBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    RelativeLayout(context, attrs, defStyleAttr) {
    private val leftImg: ImageView
    private val rightImg: ImageView
    private val titleTextView: TextView

    //1.声明一个接口
    interface ImgClickListener {
        fun leftImgClick()
        fun rightImgClick()
    }

    //2.创建一个接口变量
    private var imgClickListener: ImgClickListener? =
        null

    //3.为接口变量声明一个set方法，
    fun setImgClickListener(imgClickListener: ImgClickListener?) {
        this.imgClickListener = imgClickListener
    }

    init {
        titleTextView = TextView(context)
        leftImg = ImageView(context)
        leftImg.setPadding(12, 12, 12, 12)
        rightImg = ImageView(context)
        rightImg.setPadding(12, 12, 12, 12)
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CustomToolBar)
        val titleText = ta.getString(R.styleable.CustomToolBar_titleText)
        //第二个参数表示默认颜色
        val titleTextColor =
            ta.getColor(R.styleable.CustomToolBar_myTitleTextColor, Color.BLACK)
        //已经由sp转为px
        val titleTextSize =
            ta.getDimension(R.styleable.CustomToolBar_titleTextSize, 12f)

        //读取图片
        val leftDrawable = ta.getDrawable(R.styleable.CustomToolBar_leftImageSrc)
        val rightDrawable = ta.getDrawable(R.styleable.CustomToolBar_rightImageSrc)

        //回收TypedArray
        ta.recycle()
        leftImg.setImageDrawable(leftDrawable)
        rightImg.setImageDrawable(rightDrawable)
        titleTextView.text = titleText
        titleTextView.textSize = titleTextSize
        titleTextView.setTextColor(titleTextColor)

        //给控件设置LayoutParams时，该控件的父容器是那个，就选那个的LayoutParams
        val leftParams = LayoutParams(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                48f,
                resources.displayMetrics
            ).toInt(),
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                48f,
                resources.displayMetrics
            ).toInt()
        )
        //表示该控件和父容器的左边对齐
        leftParams.addRule(ALIGN_PARENT_LEFT, TRUE)
        this.addView(leftImg, leftParams)
        val titleParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        titleParams.addRule(CENTER_IN_PARENT, TRUE)
        addView(titleTextView, titleParams)
        val rightParams = LayoutParams(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                48f,
                resources.displayMetrics
            ).toInt(),
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                48f,
                resources.displayMetrics
            ).toInt()
        )
        rightParams.addRule(ALIGN_PARENT_RIGHT, TRUE)
        addView(rightImg, rightParams)

        //4.点击ImageView时调用接口中的方法
        leftImg.setOnClickListener {
            if (imgClickListener != null) {
                imgClickListener!!.leftImgClick()
            }
        }
        rightImg.setOnClickListener {
            if (imgClickListener != null) {
                imgClickListener!!.rightImgClick()
            }
        }
    }
}