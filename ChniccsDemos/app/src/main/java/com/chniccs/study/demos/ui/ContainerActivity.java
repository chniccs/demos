package com.chniccs.study.demos.ui;



import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.chniccs.study.demos.R;

public class ContainerActivity extends AppCompatActivity {
    public static final String CLASS_NAME = "class_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        String class_name = getIntent().getStringExtra("class_name");
        if (class_name == null || TextUtils.isEmpty(class_name)) {
            return;
        }

        Class clazz1 = null;
        Object object = null;
        try {
            clazz1 = Class.forName(class_name);
            object = clazz1.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (object == null) {
            return;
        }
        ft.replace(R.id.container, (Fragment) object);
        ft.commit();
    }
}
