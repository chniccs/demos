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

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    String[] strs = {"Recycle&refreshDemo"};
    @BindView(R.id.recycleview)
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        MyAdapter adapter= new MyAdapter();
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyItemClickListener() {
            @Override
            void onClick(View v, int position) {
                Intent intent=new Intent();
                switch (position){
                    case 0:

                        intent.setClass(MainActivity.this,ContainerActivity.class);

                        break;
                }
                startActivity(intent);
            }
        });



    }

    class MyAdapter extends RecyclerView.Adapter<MyHolder> {
        private MyItemClickListener myItemClickListener;

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            ButterKnife.bind(R.layout.item);
            MyHolder holder=new MyHolder(LayoutInflater.from(
                    MainActivity.this).inflate(R.layout.item, parent,
                    false));
            holder.setOnClickListener(myItemClickListener);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.tv.setText(strs[position]);
        }

        @Override
        public int getItemCount() {
            return strs.length;
        }
        /**
         * 设置Item点击监听
         * @param listener
         */
        public void setOnItemClickListener(MyItemClickListener listener){
            this.myItemClickListener = listener;
        }


    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MyItemClickListener myItemClickListener;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }
        public void setOnClickListener(MyItemClickListener listener){
            myItemClickListener=listener;
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

