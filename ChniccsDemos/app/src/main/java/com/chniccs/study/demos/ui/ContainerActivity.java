package com.chniccs.study.demos.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chniccs.study.demos.R;
import com.chniccs.study.demos.recycleview_refresh.RecycleFragment;

public class ContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container,new RecycleFragment());
        ft.commit();
    }
}
