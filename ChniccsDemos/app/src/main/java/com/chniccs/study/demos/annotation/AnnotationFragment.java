package com.chniccs.study.demos.annotation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.PrintInject;


/**
 * Created by chniccs on 16/8/15.
 * Annotation自定义编译期注解的demo
 * 参考：http://www.jianshu.com/p/252b0c16ffaa
 * 更多进阶可以参考：http://blog.csdn.net/github_35180164/article/details/52121038
 */
@PrintInject({1})//这里一个自定义的注解，可以在控制台输出一些信息
public class AnnotationFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
