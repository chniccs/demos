package com.chniccs.study.demos.material_design;

import android.annotation.TargetApi;
import android.os.Build;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;

import com.chniccs.study.demos.R;
import com.chniccs.study.demos.app.BaseFragment;
import com.chniccs.study.demos.utils.GuiUtils;

import butterknife.OnClick;

/**
 * Created by ccs on 16/12/2.
 * 用于显示效果的fragment
 */

public class CircularRevealFragment_temp extends BaseFragment {

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//要求5.0以上
            setupEnterAnimation(); // 入场动画
//            setupExitAnimation(); // 退场动画
        } else {
//            initViews();
        }
    }
    @OnClick(R.id.fab)
    public void fabClick(View view){

    }
    // 退出按钮
    public void backActivity(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            onBackPressed();
        } else {
//            defaultBackPressed();
        }
    }
    // 入场动画
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupEnterAnimation() {
        Transition transition = TransitionInflater.from(getActivity())
                .inflateTransition(R.transition.arc_motion);
        getActivity().getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override public void onTransitionStart(Transition transition) {

            }

            @Override public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
//                animateRevealShow();
            }

            @Override public void onTransitionCancel(Transition transition) {

            }

            @Override public void onTransitionPause(Transition transition) {

            }

            @Override public void onTransitionResume(Transition transition) {

            }
        });
    }

    // 动画展示
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    private void animateRevealShow() {
//        GuiUtils.animateRevealShow(
//                this, mRlContainer,
//                mFabCircle.getWidth() / 2, R.color.colorAccent,
//                new GuiUtils.OnRevealAnimationListener() {
//                    @Override public void onRevealHide() {
//
//                    }
//
//                    @Override public void onRevealShow() {
//                        initViews();
//                    }
//                });
//    }

}
