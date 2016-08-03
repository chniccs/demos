package com.chniccs.study.demos.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.chniccs.study.demos.R;
import com.chniccs.study.demos.recycleview_refresh.RecycleFragment;

public class ContainerActivity extends AppCompatActivity {
    public static final String CLASS_NAME = "class_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        String class_name = getIntent().getStringExtra("class_name");

        if (class_name == null || TextUtils.isEmpty(class_name)) {
            return;
        }
        Class clazz1 = null;
        try {
            clazz1 = Class.forName(class_name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        Object object = null;
        try {
            object = clazz1.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (object == null) {
            return;
        }
        ft.replace(R.id.container, (Fragment) object);
        ft.commit();
    }
}
