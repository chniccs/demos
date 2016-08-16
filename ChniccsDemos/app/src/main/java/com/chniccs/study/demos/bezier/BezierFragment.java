package com.chniccs.study.demos.bezier;

import android.view.View;

import com.chniccs.study.demos.R;
import com.chniccs.study.demos.app.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chniccs on 16/8/16.
 * 贝塞尔曲线的案例
 * 参考：
 * http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0915/3457.html
 * http://blog.csdn.net/nibiewuxuanze/article/details/48103059
 */
public class BezierFragment extends BaseFragment {
    @BindView(R.id.bezier_quadview)
    QuadView mQuadView;
    @Override
    public int getLayout() {
        return R.layout.fragment_bezier;
    }

    @Override
    public void initView() {

    }
    @OnClick(R.id.bezier_quad_btn)
    public void btnClick(View v){
        mQuadView.startAnimation();
    }
}
