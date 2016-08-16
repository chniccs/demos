package com.chniccs.study.demos.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by chniccs on 16/8/16.
 */
public class QuadView extends View {
    private Paint mPaint;
    private Path mPath;
    private Point p1, p2, p3, p4, p5, p6, p7;
    private float magicNumber = 0.522f;//由来请看http://blog.csdn.net/serapme/article/details/46929375

    public QuadView(Context context) {
        this(context, null, 0);
    }

    public QuadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(0xFFfe626d);//设置颜色
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1);
        mPaint.setAntiAlias(true);
        //这里初始化一个标准半圆的点
        p1 = new Point(0, 0);
        p2 = new Point((int) (500 * magicNumber), 0);
        p3 = new Point(500, (int) (500 * magicNumber));
        p4 = new Point(500, 500);
        p5 = new Point(500, (int) (500 + 500 * magicNumber));
        p6 = new Point((int) (500 * magicNumber), 1000);
        p7 = new Point(0, 1000);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //利用贝塞尔曲线画半圆，整个圆其实就是再画另一半就行了
        if(mPath==null){
            return;
        }
        canvas.drawPath(mPath, mPaint);
    }
    private void setPath(float i){
        mPath = new Path();
        mPath.reset();
        mPath.moveTo(p1.x,p1.y);
        //动态修改控制点
        mPath.cubicTo( p2.x*i,p2.y, p3.x*i, p3.y,p4.x*i,p4.y);//这里画的是上1/4圆
        mPath.cubicTo(p5.x*i,p5.y,p6.x*i,p6.y,p7.x,p7.y);//这里画的是下面的1/4圆
        invalidate();
    }
    private class MoveAnimation extends Animation {

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            setPath(interpolatedTime);
        }
    }

    public void startAnimation() {
        MoveAnimation move = new MoveAnimation();
        move.setDuration(1000);
        move.setInterpolator(new AccelerateDecelerateInterpolator());
        startAnimation(move);
    }
}
