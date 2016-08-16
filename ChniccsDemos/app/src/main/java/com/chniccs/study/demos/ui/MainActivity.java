package com.chniccs.study.demos.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.chniccs.study.demos.R;
import com.chniccs.study.demos.annotation.AnnotationFragment;
import com.chniccs.study.demos.bezier.BezierFragment;
import com.chniccs.study.demos.material_design.MaterialDesignFragment;
import com.chniccs.study.demos.super_viewpager.SuperViewPagerFragment;
import com.chniccs.study.demos.dynamic_proxy.DynamicProxyFragment;
import com.chniccs.study.demos.greendao.GreenDaoFragment;
import com.chniccs.study.demos.handlerthread.HandlerThreadFragment;
import com.chniccs.study.demos.mvp.MvpFragment;
import com.chniccs.study.demos.readerscript.ReaderScriptFragment;
import com.chniccs.study.demos.recycleview_refresh.RecycleFragment;
import com.chniccs.study.demos.retrofit.RetrofitFragment;
import com.chniccs.study.demos.rxbus.RxBusFragment;
import com.chniccs.study.demos.rxjava.RxJavaFragment;
import com.chniccs.study.demos.security.SecurityFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recycleview)
    RecyclerView mRecyclerView;
    private ArrayList<String> mFragments;//用于存储各个fragment的类名


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        MyAdapter adapter = new MyAdapter();
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MyItemClickListener() {
            @Override
            void onClick(View v, int position) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ContainerActivity.class);
                String className = mFragments.get(position);
                intent.putExtra(ContainerActivity.CLASS_NAME, className);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(RecycleFragment.class.getName());//可上拉加载和下拉刷新的recycle案例
        mFragments.add(ReaderScriptFragment.class.getName());//动态模糊的案例
        mFragments.add(HandlerThreadFragment.class.getName());//handlerThread用法的案例
        mFragments.add(SecurityFragment.class.getName());//自定义加密解密的案例
        mFragments.add(RxJavaFragment.class.getName());//rxjava的案例
        mFragments.add(RetrofitFragment.class.getName());//retrofit的案例
        mFragments.add(RxBusFragment.class.getName());//rxbus的案例
        mFragments.add(MvpFragment.class.getName());//Mvp模式的案例
        mFragments.add(DynamicProxyFragment.class.getName());//动态代理模式的案例
        mFragments.add(GreenDaoFragment.class.getName());//使用greendao的案例
        mFragments.add(SuperViewPagerFragment.class.getName());//水平和竖起方向滑动的viewpager的案例
        mFragments.add(AnnotationFragment.class.getName());//自定义编译期注解的案例
        mFragments.add(MaterialDesignFragment.class.getName());//材料设计语言风格控件的案例
        mFragments.add(BezierFragment.class.getName());//贝塞尔曲线的案例
    }

    class MyAdapter extends RecyclerView.Adapter<MyHolder> {
        private MyItemClickListener myItemClickListener;

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyHolder holder = new MyHolder(LayoutInflater.from(
                    MainActivity.this).inflate(R.layout.item, parent,
                    false));
            holder.setOnClickListener(myItemClickListener);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            String className = mFragments.get(position);
            holder.tv.setText(className.substring(className.lastIndexOf(".") + 1, className.length()));
        }

        @Override
        public int getItemCount() {
            return mFragments.size();
        }

        /**
         * 设置Item点击监听
         *
         * @param listener
         */
        public void setOnItemClickListener(MyItemClickListener listener) {
            this.myItemClickListener = listener;
        }


    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MyItemClickListener myItemClickListener;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);//注解绑定
            itemView.setOnClickListener(this);//设置item点击
        }

        public void setOnClickListener(MyItemClickListener listener) {
            myItemClickListener = listener;
        }

        @BindView(R.id.tv)
        TextView tv;

        @Override
        public void onClick(View view) {
            myItemClickListener.onClick(view, getPosition());
        }
    }

    abstract class MyItemClickListener {
        abstract void onClick(View v, int position);
    }

}

