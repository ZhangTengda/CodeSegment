package com.testone.demo.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

/**
 * 自定义一个可以双向滑动拖拽的seekbar
 */
class MySeekBar : View {
    private var bgPaint: Paint? = null
    private var buttonPaint: Paint? = null
    private var progressPaint: Paint? = null
    private var leftRect: Rect? = null
    private var rightRect: Rect? = null
    private var progressRect: Rect? = null
    private var isCanSeek = true//是否可以拖动

    private var downX: Float = 0f
    private var downY: Float = 0f
    private var upX: Float = 0f
    private var upY: Float = 0f
    private var moveX: Float = 0f

    private var currentProgress = 0//当前进度
    private var totalProgress = 100//总进度
    private var startProgress = 0//开始的进度
    private var endProgress = 0//结束的进度

    private var buttonWidth = 40//拖动的按钮的宽度
    private var isFirstInit=true
    private var SEEKMODE=0
    private val LEFTMODE=1
    private val RIGHTMODE=2

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, -1)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        //背景色的画笔
        bgPaint = Paint()
        bgPaint!!.style = Paint.Style.FILL_AND_STROKE
        bgPaint!!.color = Color.BLUE
        bgPaint!!.alpha = 80
        bgPaint!!.isAntiAlias = true
        //拖动按钮的画笔
        buttonPaint = Paint()
        buttonPaint!!.style = Paint.Style.FILL_AND_STROKE
        buttonPaint!!.color = Color.GRAY
//        buttonPaint!!.alpha = 80
        buttonPaint!!.isAntiAlias = true

        //当前进度的画笔
        progressPaint = Paint()
        progressPaint!!.style = Paint.Style.FILL_AND_STROKE
        progressPaint!!.color = Color.RED
        progressPaint!!.alpha = 80
        progressPaint!!.isAntiAlias = true

        //当前进度的矩形
        progressRect = Rect()
        //当前进度左边的矩形--可拖拽
        leftRect = Rect()
        //当前进度右边的矩形--可拖拽
        rightRect = Rect()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = measureSize(widthMeasureSpec)
        var height = measureSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    private fun initProgress() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        drawLeftRect()
        drawRightRect()
        drawProgressRect()
    }

    fun measureSize(measureSpec: Int): Int {
        var result = 0;
        var specMode = MeasureSpec.getMode(measureSpec)
        var specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 40.0f, context.resources.displayMetrics).toInt()
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize)
            }
        }
        return result
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //绘制背景色
        canvas!!.drawColor(Color.YELLOW)
       if (isFirstInit){
           initProgress()
       }
        //绘制总的进度
//        canvas!!.drawRect(Rect(), bgPaint)

        //绘制左边的拖拽按钮
        canvas.drawRect(leftRect, buttonPaint)
//        drawLeftRect(canvas)
        //绘制当前的进度条
        canvas.drawRect(progressRect, progressPaint)
//        drawProgressRect(canvas)
        //绘制右边的拖拽按钮
        canvas.drawRect(rightRect, buttonPaint)
//        drawRightRect(canvas)
        isFirstInit=false
    }

    /**
     * 通过当前的进度获取控件的位置（）
     */
    private fun getLocationParams(process: Int): Int {
        if (process > 0) {
//           return (process/totalProgress)*(width-2*buttonWidth)
            return ((process.toFloat() / totalProgress) * width).toInt()
        }
        return 0
    }

    private fun drawLeftRect() {
        if (startProgress > 0) {
            leftRect!!.left = getLocationParams(startProgress)
            leftRect!!.right = getLocationParams(startProgress) + buttonWidth
            leftRect!!.top = 0
            leftRect!!.bottom = height
        } else {
            leftRect!!.left = 0
            leftRect!!.right = buttonWidth
            leftRect!!.top = 0
            leftRect!!.bottom = height
        }
        Log.i("MySeekBar","左边按钮的参数："+leftRect!!.left+"::"+leftRect!!.top+":::"+leftRect!!.right+"::"+leftRect!!.bottom)
    }

    private fun drawRightRect() {
        if (endProgress >=0&&(endProgress>=startProgress)) {
            rightRect!!.left = if (endProgress==0){buttonWidth}else{getLocationParams(endProgress)+buttonWidth}
            rightRect!!.right = getLocationParams(endProgress) + buttonWidth*2
            rightRect!!.top = 0
            rightRect!!.bottom = height
        } else {
            rightRect!!.left = 0
            rightRect!!.right = buttonWidth
            rightRect!!.top = 0
            rightRect!!.bottom = height
        }
        Log.i("MySeekBar","右按钮的参数："+rightRect!!.left+"::"+rightRect!!.top+":::"+rightRect!!.right+"::"+rightRect!!.bottom)
    }

    private fun drawProgressRect() {
        if (endProgress>startProgress) {
            progressRect!!.left = getLocationParams(startProgress)+buttonWidth
            progressRect!!.right = getLocationParams(endProgress)+buttonWidth
            progressRect!!.top = 0
            progressRect!!.bottom = height
        }
        Log.i("MySeekBar","进度条的参数："+progressRect!!.left+"::"+progressRect!!.top+":::"+progressRect!!.right+"::"+progressRect!!.bottom)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!isCanSeek)
            return false
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {//手指按下
                downX = event.x
                downY = event.y
                Log.i("MySeekBar","手指按下-----::${downX}--${downY}")
                if (viewContainsPoint(leftRect,downX,downY)){
                    SEEKMODE=LEFTMODE
                    moveX=downX
                }else if (viewContainsPoint(rightRect,downX,downY)){
                    SEEKMODE=RIGHTMODE
                    moveX=downX
                }else{
                    SEEKMODE=0
                    moveX=0f
                }
            }
            MotionEvent.ACTION_MOVE -> {//手指滑动
                Log.i("MySeekBar","手指滑动-----::x-downX====${event.x-downX}---moveX+${moveX}+++++${event.x-moveX}")
//               if (viewContainsPoint(leftRect,downX,downY)){
//                   //左边的拖动按钮
////                   leftRect!!.left=
//                   Log.i("MySeekBar","拖拽左边的参数："+progressRect!!.left+"::"+progressRect!!.top+":::"+progressRect!!.right+"::"+progressRect!!.bottom)
//                    leftRect!!.left=leftRect!!.left+(event!!.x-downX).toInt()
//                    leftRect!!.right=leftRect!!.right+(event!!.x-downX).toInt()
//                   progressRect!!.left=progressRect!!.left+(event!!.x-downX).toInt()
//                   progressRect!!.right=progressRect!!.right+(event!!.x-downX).toInt()
//                }else if (viewContainsPoint(rightRect,downX,downY)){
//                   //右边的拖动按钮
//
//               }else{
//
//               }
//                if (Math.abs(event.x-moveX)>5) {
                    when (SEEKMODE) {
                        LEFTMODE -> {
                            //左边的拖动按钮
////                   leftRect!!.left=
//                   Log.i("MySeekBar","拖拽左边的参数："+progressRect!!.left+"::"+progressRect!!.top+":::"+progressRect!!.right+"::"+progressRect!!.bottom)
                           if (leftRect!!.left + (event!!.x - moveX).toInt()>=0&&leftRect!!.right + (event!!.x - moveX).toInt()<=rightRect!!.left){
                               leftRect!!.left = leftRect!!.left + (event!!.x - moveX).toInt()
                               leftRect!!.right = leftRect!!.right + (event!!.x - moveX).toInt()
                               progressRect!!.left = progressRect!!.left + (event!!.x - moveX).toInt()
//                               progressRect!!.right = progressRect!!.right + (event!!.x - moveX).toInt()
                               invalidate()
                           }
                        }
                        RIGHTMODE -> {
                            if (rightRect!!.left + (event!!.x - moveX).toInt()>=leftRect!!.right&&rightRect!!.right + (event!!.x - moveX).toInt()<=width){
                                rightRect!!.left = rightRect!!.left + (event!!.x - moveX).toInt()
                                rightRect!!.right = rightRect!!.right + (event!!.x - moveX).toInt()
//                                progressRect!!.left = progressRect!!.left + (event!!.x - moveX).toInt()
                                progressRect!!.right = progressRect!!.right + (event!!.x - moveX).toInt()
                                invalidate()
                            }
                            invalidate()
                        }
                        else -> 0
//                    }
                }
                moveX=event.x
            }
            MotionEvent.ACTION_UP -> {//手指抬起
                SEEKMODE=0
                moveX=0f
//                leftRect!!.left=0
//                leftRect!!.right=buttonWidth
//                invalidate()
            }
            MotionEvent.ACTION_CANCEL -> {//取消触摸
                SEEKMODE=0
                moveX=0f
            }
        }
        return true
    }
    private fun viewContainsPoint(view:Rect?,pointX:Float,pointY:Float):Boolean{
        if (view!=null){
            if (pointX>=view.left&&pointY<=view.bottom){
                if (pointX<=view.right&&pointY>=view.top){
                    return true
                }
            }
        }
        return false
    }

    /**
     * 设置当前的进度
     */
    fun setProgress(currentProgress: Int) {
        if (currentProgress >= 0) {
            this.currentProgress = currentProgress
        }
    }

    /**
     * 设置当前的进度
     */
    fun setProgress(startProgress: Int, endProgress: Int) {
        if ((startProgress >= 0 || endProgress >= 0)&&(endProgress<=100)&&(startProgress<=endProgress)) {
            this.startProgress = startProgress
            this.endProgress = endProgress
            invalidate()
        }else{
            //开始进度或者小于零 并且开始进度不能大于结束进度
        }
    }
}