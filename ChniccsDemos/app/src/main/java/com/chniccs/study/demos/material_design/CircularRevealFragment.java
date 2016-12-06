package com.chniccs.study.demos.material_design;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.View;

import com.chniccs.study.demos.R;
import com.chniccs.study.demos.app.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ccs on 16/12/2.
 * 5.0 circularReveal动画的案例
 * 参考：
 * http://www.jianshu.com/p/e30510b83e0e
 * http://blog.csdn.net/tm1989tm/article/details/51025309
 * https://github.com/ozodrukh/CircularReveal
 * https://github.com/leelei/Android-CircularRevealFragment
 */

public class CircularRevealFragment extends BaseFragment implements IOnFragmentTouched {

    @Override
    public int getLayout() {
        return R.layout.fragment_circularreveal;
    }

    @Override
    public void initView() {}
    @Override
    public void onFragmentTouched(Fragment fragment, float x, float y) {
        if (fragment instanceof CircularRevealFragment_temp) {
            final CircularRevealFragment_temp theFragment = (CircularRevealFragment_temp) fragment;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {//要求5.0以上
                getActivity().getSupportFragmentManager().beginTransaction().remove(theFragment).commit();
                return;
            }
            Animator unreveal = theFragment.prepareUnrevealAnimator(x, y);

            unreveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }
                @Override
                public void onAnimationEnd(Animator animation) {
                    //remove the fragment only when the animation finishes
                    getActivity().getSupportFragmentManager().beginTransaction().remove(theFragment).commit();
                }
                @Override
                public void onAnimationCancel(Animator animation) {
                }
                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            unreveal.start();
        }
    }
    @OnClick(R.id.fab)
    public void fabClick(View view){
        int randomColor = Color.argb(255, (int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
        Fragment fragment = CircularRevealFragment_temp.newInstance(20,20, randomColor,this);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragment)
                .commit();
    }
}
