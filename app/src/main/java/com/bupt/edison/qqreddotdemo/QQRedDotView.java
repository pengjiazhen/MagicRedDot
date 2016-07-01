package com.bupt.edison.qqreddotdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by edison on 16/6/30.
 * 一招退朝
 */
public class QQRedDotView extends View {
    Paint dotPaint; //红点画笔
    Paint rubberPaint;//皮筋画笔
    Paint domainPaint;//范围画笔
    float initX,initY;//红点初始时的位置
    int resetRedius;//复位的距离
    int dismissRedius;//消失的距离
    int dotRedius;//红点的距离
    RectF dotRectF;//红点的范围矩阵
    RectF touchRectF;//红点的拖动矩阵

    public QQRedDotView(Context context) {
        super(context);
        init();
    }

    public QQRedDotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QQRedDotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        dotPaint = new Paint();
        dotPaint.setAntiAlias(true);
        dotPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        dotPaint.setStrokeWidth(3);
        dotPaint.setColor(Color.RED);

        rubberPaint = new Paint();
        rubberPaint.setStrokeWidth(3);
        rubberPaint.setColor(Color.RED);
        rubberPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        rubberPaint.setAntiAlias(true);

        domainPaint = new Paint();
        domainPaint.setColor(Color.BLACK);
        domainPaint.setStrokeWidth(3);
        domainPaint.setAntiAlias(true);
        domainPaint.setStyle(Paint.Style.STROKE);

        initX = 50;
        initY = 50;

        resetRedius = 30;
        dismissRedius = 45;
        dotRedius = 15;

        dotRectF = new RectF();
        touchRectF = new RectF();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("edison onLayout",getPivotX()+" "+getPivotY());
        Log.d("edison onLayout","width: "+getWidth()+" "+"height: "+getHeight());
        Log.d("edison onLayout","left: "+getLeft()+" "+"top: "+getTop()+" right: "+getRight()+" bottom: "+getBottom());
        dotRectF.set(0,0,getWidth(),getHeight());
        touchRectF.set(getLeft(),getTop(),getRight(),getBottom());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("edison onSizeChanged",getPivotX()+" "+getPivotY());
        Log.d("edison onSizeChanged","width: "+getWidth()+" "+"height: "+getHeight());
        Log.d("edison onSizeChanged","left: "+getLeft()+" "+"top: "+getTop()+" right: "+getRight()+" bottom: "+getBottom());
        dotRectF.set(0,0,getWidth(),getHeight());
        touchRectF.set(getLeft(),getTop(),getRight(),getBottom());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("edison onMeasure",getPivotX()+" "+getPivotY());
        Log.d("edison onMeasure","width: "+getWidth()+" "+"height: "+getHeight());
        Log.d("edison onMeasure","left: "+getLeft()+" "+"top: "+getTop()+" right: "+getRight()+" bottom: "+getBottom());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("edison","onDraw");
        canvas.drawRoundRect(dotRectF,12,12,dotPaint);
    }

    float downX,downY,moveX,moveY,upX,upY;
    boolean isdragable = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = event.getX()+getLeft();
                downY = event.getY()+getTop();
                Log.d("edison action down","downX: "+downX+" downY: "+downY);
                if(touchRectF.contains(downX,downY)){
                    isdragable = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(isdragable){
                    moveX = event.getX()+getLeft();
                    moveY = event.getY()+getTop();
                    setX(moveX);
                    setY(moveY);
                    //更新范围矩阵
                    requestLayout();
                }
                break;
            case MotionEvent.ACTION_UP:
                if(isdragable){
                    isdragable = false;
                    upX = event.getX()+getLeft();
                    upY = event.getY()+getTop();
                    //更新范围矩阵
                    touchRectF.set(upX,upY,upX+getWidth(),upY+getHeight());
                    requestLayout();
                }
                break;
            default:
                break;
        }
        return true;
    }

    public RectF getDotRectF(){
        return this.dotRectF;
    }

    public void setDotRectF(float left,float top,float right,float bottom){
        dotRectF.set(left,top,right,bottom);
    }

    public void animatorMove(){

    }

    //属性动画
    //scrollTo scrollBy,只能移动view的内容,不能移动View
    //实时绘制view,同样是只能移动view的内容,不能移动view
    //requestLayout(),实时layout控件,拖动的时候有抖动
}
