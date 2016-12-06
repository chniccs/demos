package com.chniccs.study.demos.dagger2.component;

import com.chniccs.study.demos.dagger2.DaggerFragment;
import com.chniccs.study.demos.dagger2.module.MyModule;

import dagger.Component;

/**
 * Created by ccs on 16/11/29.
 */
@Component(modules = {MyModule.class})
public interface AComponent {
    void inject(DaggerFragment a);   // 注入到A(Container)的方法，方法名一般使用inject
}
