package com.chniccs.study.demos.dagger2.module;

import com.chniccs.study.demos.dagger2.container.A;
import com.chniccs.study.demos.dagger2.entity.Aentity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ccs on 16/11/29.
 * 实体提供的容器类，用来提供各类实例
 */
@Module// 注明本类是Module
public class MyModule {
    @Provides
// 注明该方法是用来提供依赖对象的方法
    public Aentity provideAentity() {
        return new Aentity();
    }

    @Provides
    public A provideA() {
        return new A();
    }
}
