package com.zcf.blepro.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class LinearCircle extends View {

    private int width;
    private int height;

    private Paint outerCirclePaint;//外层圆的画笔
    private Paint innerCirclePaint;//内层圆的画笔
    private Paint linePaint;//线段画笔
    private Paint arrowPaint;//指针画笔

    private Path outerCirclePath;//外层圆的Path
    private Path innerCirclePath;//内层圆的Path
    private Path linePath;//线段的Path
    private Path arrowPath;//指针的Path
    private Path measureArrowPath;//arrowPath借助该Path来保持一定的长度

    private RectF outRectF;//用于绘制外层圆
    private RectF innerRectF;//用于绘制内层圆

    private int count = 80 - 50;//画count根线
//    private static int outerR = 200;//外部圆环的半径
//private static int innerR = (int) (outerR * 0.85f);//内部圆环的半径

    private static int outerR;//外部圆环的半径
    private static int innerR;//内部圆环的半径
    private int shortageAngle = 270;//缺失的部分的角度
    private int startAngle = 180;//开始的角度
    private int sweepAngle = 90;//扫过的角度

    private float[] leftEndPoint;//左侧边界的坐标
    private float[] rightEndPoint;//右侧边界的坐标
    private float leftEndTan;//左侧边界的tan值
    private float rightEndTan;//右侧边界的tan值

    private float nowX = 0;//触摸位置的横坐标
    private float nowY = 0;//触摸位置的纵坐标
    private static float percent = 0.9f;//指针与内层圆的比值
    //    private float arrowLength = innerR * percent;//指针的长度
    private float arrowLength;//指针的长度

    private PathMeasure arrowMeasure;//用于指针的测量


    public LinearCircle(Context context) {
        super(context);
        initPaint();
//        initAngle();
    }

    public LinearCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
//        initAngle();
    }

    public LinearCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
//        initAngle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        outerR = w;
        innerR = (int) (outerR * 0.85f);
        arrowLength = innerR * percent;
        //让指针一开始指向正上方
        nowX = -1;
        nowY = 0;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(width, height);//设置原点

        drawOuterCircle();
        drawInnerCircle();
        drawLine(canvas);
        drawArrow(canvas);
    }

    /**
     * 外层圆圈
     */
    private void drawOuterCircle() {
        //一般绘制圆圈的方法，不做介绍了
        outerCirclePath = new Path();
        if (outRectF == null) {
            outRectF = new RectF(-outerR, -outerR, outerR, outerR);
//            outRectF = new RectF(-width, -width, width, width);
        }
        outerCirclePath.addArc(outRectF, startAngle, sweepAngle);
    }

    /**
     * 内层圆圈
     */
    private void drawInnerCircle() {
        //一般绘制圆圈的方法，不做介绍了
        innerCirclePath = new Path();
        if (innerRectF == null) {
            innerRectF = new RectF(-innerR, -innerR, innerR, innerR);
//            innerRectF = new RectF(-(width*0.85f), -(width*0.85f), (width*0.85f),(width*0.85f));
        }
        innerCirclePath.addArc(innerRectF, startAngle, sweepAngle);
    }

    /**
     * 画直线，组成一个类似于弧形的形状
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        linePath = new Path();
        //用于外层圆的测量
        PathMeasure outMeasure = new PathMeasure(outerCirclePath, false);
        float outlength = outMeasure.getLength();
        float[] outPos = new float[2];

        //用于内层圆的测量
        PathMeasure inMeasure = new PathMeasure(innerCirclePath, false);
        float inlength = inMeasure.getLength();
        float[] inPos = new float[2];

        //确定左侧末尾的坐标以及tan值
        if (leftEndPoint == null) {
            leftEndPoint = new float[2];
            //通过getPosTan拿到内层圆的左侧末尾坐标
            inMeasure.getPosTan(0, leftEndPoint, null);
            //因为指针要短一点;所以x,y都乘以percent才是指针真正的左侧末尾坐标
            leftEndPoint[0] = leftEndPoint[0] * percent;
            leftEndPoint[1] = leftEndPoint[1] * percent;
            //确定指针在左侧末尾时的tan值
            leftEndTan = leftEndPoint[1] / leftEndPoint[0];


            Log.e("aaa", "leftEndTan ==" + leftEndTan);

            Log.e("aaa", " leftEndPoint[0]==" + leftEndPoint[0]);
            Log.e("aaa", " leftEndPoint[1]==" + leftEndPoint[1]);
        }

        //确定右侧末尾的坐标以及tan值
        if (rightEndPoint == null) {
            rightEndPoint = new float[2];
            //通过getPosTan拿到内层圆的右侧末尾坐标
            inMeasure.getPosTan(inlength, rightEndPoint, null);
            //因为指针要短一点;所以x,y都乘以percent才是指针真正的右侧末尾坐标
            rightEndPoint[0] = rightEndPoint[0] * percent;
            rightEndPoint[1] = rightEndPoint[1] * percent;
            //确定指针在右侧末尾时的tan值
            rightEndTan = rightEndPoint[1] / rightEndPoint[0];
            Log.e("aaa", " rightEndPoint[0]==" + rightEndPoint[0]);
            Log.e("aaa", " rightEndTan[1]==" + rightEndPoint[1]);
            Log.e("aaa", "rightEndTan ==" + rightEndTan);
        }

        //用来画多条线段，组成弧形
        for (int i = 0; i <= count; i++) {
            //外层圆当前的弧长
            float outNowLength = outlength * i / (count * 1.0f);
            //当前弧长下对应的坐标outPos
            outMeasure.getPosTan(outNowLength, outPos, null);

            //内层圆当前的弧长
            float inNowLength = inlength * i / (count * 1.0f);
            //当前弧长下对应的坐标inPos
            inMeasure.getPosTan(inNowLength, inPos, null);

            //moveTo到内层圆弧上的点
            linePath.moveTo(outPos[0], outPos[1]);
            //lineTo到外层圆弧上的点
            linePath.lineTo(inPos[0], inPos[1]);//绘制直线

            canvas.drawPath(linePath, linePaint);
        }
    }

    /**
     * 绘制指针
     *
     * @param canvas
     */
    private void drawArrow(Canvas canvas) {
        //measureArrowPath只专门用来做计算的，不绘制（当然也可以不用多创建这个对象，直接用arrowPath来完成测量，绘制工作;
        //这里是为了任务单一，做了区分）
        measureArrowPath = new Path();

        //指针最终是由arrowPath来绘制的
        arrowPath = new Path();
        arrowPath.reset();
        measureArrowPath.reset();

        //用来封装指针的末尾坐标
        float[] endPoint = new float[2];

        //指针的起始坐标为原点，也就是(0,0)
        measureArrowPath.moveTo(0, 0);
        //指向手指目前的位置
        measureArrowPath.lineTo(nowX, nowY);
        //arrowMeasure用来测量原点到手指位置的线段
        arrowMeasure = new PathMeasure(measureArrowPath, false);
        //触摸位置与原点的长度
        float nowLineLength = arrowMeasure.getLength();

        //距离原点过近（也就是长度不够长）的处理
        if (nowLineLength < arrowLength) {
            //计算需要扩大的倍数（固定长度 ÷ 当前长度）
            float expand = arrowLength / (nowLineLength);
            //重置数据，并测量新数据
            measureArrowPath.reset();
            measureArrowPath.moveTo(0, 0);
            measureArrowPath.lineTo(nowX * expand, nowY * expand);
            arrowMeasure = new PathMeasure(measureArrowPath, false);
        }
        //测量指针末尾的坐标（指针在measureArrowPath这条线段上，且小于等于measureArrowPath线段的长度;
        // 通过getPosTan()来确定线段在长度为arrowLength时的坐标位置）
        arrowMeasure.getPosTan(arrowLength, endPoint, null);
        double nowTana = endPoint[1] / endPoint[0];

        Log.e("ttt", "nowTana====" + nowTana);
        Log.e("tta", "endPoint[0]==" + endPoint[0]);
        Log.e("tta", "endPoint[1]==" + endPoint[1]);
        //第一象限的处理
        if (endPoint[0] > 0 && endPoint[1] > 0) {
            //右下角的情况处理
            double nowTan = endPoint[1] / endPoint[0];
            //当前触摸位置的tan值大于边界的tan值，表示手指目前在左侧边界的下方
            if (nowTan > rightEndTan) {
                endPoint[0] = rightEndPoint[0];
                endPoint[1] = rightEndPoint[1];
            }
        }
        //第二象限的处理
        if (endPoint[0] < 0 && endPoint[1] > 1) {
            //左下角的情况处理
            double nowTan = endPoint[1] / endPoint[0];
            //当前触摸位置的tan值小于边界的tan值，表示手指目前在右侧边界的下方
            if (nowTan < leftEndTan) {
                endPoint[0] = leftEndPoint[0];
                endPoint[1] = leftEndPoint[1];
            }
        }
        //这里默认了第三、第四现象一般没有限制；如果圆弧的缺口过大，需要处理下；方式与上面的相似


        //这时，指针的末尾位置最终确定了，可以绘制了
        arrowPath.moveTo(0, 0);
        arrowPath.lineTo(endPoint[0], endPoint[1]);
        canvas.drawPath(arrowPath, arrowPaint);
    }

    //通过触摸等事件改变指针的指向
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:


            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                nowX = event.getX();
                nowY = event.getY();
                break;
        }
        //nowX和nowY是以左上角为原点的坐标系，这里进行了平移
        nowX = nowX - width / 2;
        nowY = nowY - height / 2;
        invalidate();
        return true;
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {

        if (outerCirclePaint == null) {
            outerCirclePaint = new Paint();
            outerCirclePaint.setStyle(Paint.Style.STROKE);
            outerCirclePaint.setColor(Color.BLACK);
        }
        if (innerCirclePaint == null) {
            innerCirclePaint = new Paint();
            innerCirclePaint.setStyle(Paint.Style.STROKE);
            outerCirclePaint.setColor(Color.BLACK);
        }
        if (linePaint == null) {
            linePaint = new Paint();
            linePaint.setStyle(Paint.Style.STROKE);
            linePaint.setStrokeWidth(4);
            linePaint.setColor(0xff1d8ffe);
        }
        if (arrowPaint == null) {
            arrowPaint = new Paint();
            arrowPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            arrowPaint.setColor(Color.RED);
            arrowPaint.setStrokeWidth(4);
        }
    }

    /**
     * 根据shortageAngle来调整圆弧的角度
     */
    private void initAngle() {
        sweepAngle = 360 - shortageAngle;
        startAngle = 90 + shortageAngle / 2;
    }
}
