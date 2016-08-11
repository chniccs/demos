package com.chniccs.study.demos.super_viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.chniccs.study.demos.super_viewpager.transformer.StackTransformer;

/**
 * Created by chniccs on 16/8/11.
 * 竖起方向滑动的viewpager
 * 引用：https://github.com/kaelaela/VerticalViewPager
 */
public class VerticalViewPager extends ViewPager {

    public VerticalViewPager(Context context) {
        this(context, null);
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPageTransformer(false, new StackTransformer());
    }

    private MotionEvent swapTouchEvent(MotionEvent event) {//转换X/Y的，将X方向的触摸处理转换到Y方向上
        float width = getWidth();
        float height = getHeight();

        float swappedX = (event.getY() / height) * width;
        float swappedY = (event.getX() / width) * height;

        event.setLocation(swappedX, swappedY);

        return event;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = super.onInterceptTouchEvent(swapTouchEvent(event));
        //If not intercept, touch event should not be swapped.
        swapTouchEvent(event);
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapTouchEvent(ev));
    }

}