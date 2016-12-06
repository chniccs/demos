package com.chniccs.study.demos.dagger2.container;

/**
 * Created by ccs on 16/11/29.
 */
import com.chniccs.study.demos.dagger2.component.DaggerAEntityComponent;
import com.chniccs.study.demos.dagger2.entity.Aentity;

import javax.inject.Inject;

public class A {
    @Inject   //标记Aentity将被注入
    public Aentity mAentity;   // 成员变量要求是包级可见，也就是说@Inject不可以标记为private类型。
    public A(){
        init();
    }
    public void init() {
        DaggerAEntityComponent.create().inject(this); //将实现类注入
    }
}