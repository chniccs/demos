package com.chniccs.study.demos.super_viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by chniccs on 16/8/11.
 * 可以与竖直方向没去的viewpager一起使用的水平方向的viewpager
 * 主要是解决了竖直方向viewpager与水平方向事件冲突的问题
 * 引用自：http://www.jianshu.com/p/b9a9717e98e8
 */
public class HorizontalViewPager extends ViewPager {
    private float mDownX;
    private float mDownY;
    private float mTouchSlop;

    public HorizontalViewPager(Context context) {
        super(context);
        init(context);
    }

    public HorizontalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = super.onInterceptTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = x;
                mDownY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - mDownX);
                float dy = Math.abs(y - mDownY);
                if (!intercept && dx > mTouchSlop && dx * 0.5f > dy) {
                    intercept = true;
                }
                break;
            default:
                break;
        }
        return intercept;
    }
}
