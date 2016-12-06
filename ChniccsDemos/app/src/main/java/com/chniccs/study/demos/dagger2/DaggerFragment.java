package com.chniccs.study.demos.dagger2;

import android.widget.TextView;

import com.chniccs.study.demos.R;
import com.chniccs.study.demos.app.BaseFragment;
import com.chniccs.study.demos.dagger2.component.DaggerAComponent;
import com.chniccs.study.demos.dagger2.container.A;
import com.chniccs.study.demos.dagger2.entity.Aentity;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by ccs on 16/11/29.
 * dragger2的使用案例
 * 参考：http://www.jianshu.com/p/4db940c8da97
 */

public class DaggerFragment extends BaseFragment {
    @BindView(R.id.dagger_simple_tv)
    TextView mTv;
    @Inject   //标记Aentity将被注入
    public A a;   // 成员变量要求是包级可见，也就是说@Inject不可以标记为private类型。

    @Override
    public int getLayout() {
        return R.layout.fragment_dagger;
    }

    @Override
    public void initView() {
        DaggerAComponent.create().inject(this); //将实现类注入
        mTv.setText(a.mAentity.getName());
    }
}
