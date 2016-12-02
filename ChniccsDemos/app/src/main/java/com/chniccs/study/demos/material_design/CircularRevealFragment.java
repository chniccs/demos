package com.chniccs.study.demos.material_design;

import android.content.Context;
import android.os.Build;
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
 */

public class CircularRevealFragment extends BaseFragment {

    @Override
    public int getLayout() {
        return R.layout.fragment_circularreveal;
    }

    @Override
    public void initView() {


    }
    @OnClick(R.id.fab)
    public void fabClick(View view){


    }



}
