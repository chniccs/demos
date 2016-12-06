package com.chniccs.study.demos.dagger2.component;

import com.chniccs.study.demos.dagger2.DaggerFragment;
import com.chniccs.study.demos.dagger2.container.A;
import com.chniccs.study.demos.dagger2.module.MyModule;

import dagger.Component;

/**
 * Created by ccs on 16/11/29.
 */
@Component(modules = {MyModule.class})
// 指明Component查找Module的位置
public interface AEntityComponent{    // 必须定义为接口，Dagger2框架将自动生成Component的实现类，对应的类名是Dagger×××××，这里对应的实现类是DaggerMyComponent
   void inject(A a);   // 注入到A(Container)的方法，方法名一般使用inject
}