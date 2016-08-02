package com.chniccs.study.demos.recycleview_refresh;

import android.app.Fragment;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chniccs.study.demos.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by meiya on 16/8/1.
 * 带下拉刷新的RecycleView的通用Fragment
 * 因为使用的是butterknife注解，所以需要在gladle中配置
 * //以下的是配置在主工程中
 *  apply plugin: 'android-apt'
 *
 *  android {
 *  compileSdkVersion 24
 *  .....
 *  dependencies {
 *  ...
 *  compile 'com.android.support:recyclerview-v7:24.1.1'//recycle
 *  compile 'com.jakewharton:butterknife:8.2.1'
 *  apt 'com.jakewharton:butterknife-compiler:8.2.1'
 *  以下配置在项目中：
 *  dependencies {
 *  ...
 *  classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
 */
public class RecycleFragment extends Fragment {
    @BindView(R.id.recycleview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swiperefresh)
    SuperSwipeRefreshLayout mRefresh;
    @BindView(R.id.btn)
    Button mBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new MyAdapter());
        //mRefresh.setHeaderView();//设置头部刷新的view
        //下拉刷新
        mRefresh.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mRefresh.setRefreshing(false);
                            }
                        });
                    }
                }).start();

            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });
        //上拉加载更多
        mRefresh.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {

            }

            @Override
            public void onPushDistance(int distance) {

            }

            @Override
            public void onPushEnable(boolean enable) {

            }
        });

//        mBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                doRefresh(view);
//            }
//        });

    }
    @OnClick(R.id.btn)
    public void doRefresh(View view){
        mRefresh.refresh();
//        Toast.makeText(getActivity(),"点击了",Toast.LENGTH_SHORT).show();
    }

    class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(LayoutInflater.from(
                    parent.getContext()).inflate(viewType == 0 ? R.layout.item : R.layout.item_two, parent,
                    false));
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.tv.setText(position + "");
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        @Override
        public int getItemViewType(int position) {
            return position % 2 == 0 ? 0 : 1;
        }

    }

    class MyHolder extends RecyclerView.ViewHolder {

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            tv = (TextView) itemView.findViewById(R.id.tv);
        }

        @BindView(R.id.tv)
        TextView tv;
    }
}
