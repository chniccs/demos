package com.chniccs.study.demos.material_design;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.chniccs.study.demos.R;
import com.chniccs.study.demos.app.BaseFragment;
import com.chniccs.study.demos.utils.GuiUtils;

import butterknife.OnClick;

/**
 * Created by ccs on 16/12/2.
 * 用于显示效果的fragment
 */

public class CircularRevealFragment_temp extends BaseFragment {
    private IOnFragmentTouched mListener;

    @Override
    public int getLayout() {
        return R.layout.fragment_circularreveal_temp;
    }

    @Override
    public void initView() {
        mView.setBackgroundColor(getArguments().getInt("color"));

        // To run the animation as soon as the view is layout in the view hierarchy we add this listener and remove it
        // as soon as it runs to prevent multiple animations if the view changes bounds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//要求5.0以上
            mView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
                                           int oldRight, int oldBottom) {
                    v.removeOnLayoutChangeListener(this);
                    int cx = getArguments().getInt("cx");
                    int cy = getArguments().getInt("cy");

                    //get the hypothenuse so the radius is from one corner to the other
                    int radius = (int) Math.hypot(right, bottom);
                    Animator reveal = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0, radius);
                    reveal.setInterpolator(new DecelerateInterpolator(2f));
                    reveal.setDuration(1000);
                    reveal.start();
                }
            });
        }

        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mListener != null){
                    mListener.onFragmentTouched(CircularRevealFragment_temp.this, event.getX(), event.getY());
                }
                return true;
            }
        });
    }

    public CircularRevealFragment_temp() {
    }

    public static CircularRevealFragment_temp newInstance(int centerX, int centerY, int color, IOnFragmentTouched listener) {
        Bundle args = new Bundle();
        args.putInt("cx", centerX);
        args.putInt("cy", centerY);
        args.putInt("color", color);
        CircularRevealFragment_temp fragment = new CircularRevealFragment_temp();
        fragment.setArguments(args);
        fragment.mListener = listener;
        return fragment;
    }

    /**
     * Get the animator to unreveal the circle
     *
     * @param cx center x of the circle (or where the view was touched)
     * @param cy center y of the circle (or where the view was touched)
     * @return
     */
    public Animator prepareUnrevealAnimator(float cx, float cy) {
        int radius = getEnclosingCircleRadius(getView(), (int) cx, (int) cy);
        Animator anim = ViewAnimationUtils.createCircularReveal(getView(), (int) cx, (int) cy, radius, 0);
        anim.setInterpolator(new AccelerateInterpolator(2f));
        anim.setDuration(1000);
        return anim;
    }

    /**
     * To be really accurate we have to start the circle on the furthest corner of the view
     *
     * @param v  the view to unreveal
     * @param cx center x of the circle
     * @param cy center y of the circle
     * @return the maximum radius
     */
    private int getEnclosingCircleRadius(View v, int cx, int cy) {
        int realCenterX = cx + v.getLeft();
        int realCenterY = cy + v.getTop();
        int distanceTopLeft = (int) Math.hypot(realCenterX - v.getLeft(), realCenterY - v.getTop());
        int distanceTopRight = (int) Math.hypot(v.getRight() - realCenterX, realCenterY - v.getTop());
        int distanceBottomLeft = (int) Math.hypot(realCenterX - v.getLeft(), v.getBottom() - realCenterY);
        int distanceBotomRight = (int) Math.hypot(v.getRight() - realCenterX, v.getBottom() - realCenterY);

        return Math.max(Math.max(Math.max(distanceTopLeft, distanceTopRight), distanceBottomLeft), distanceBotomRight);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mListener = null;
    }
}
