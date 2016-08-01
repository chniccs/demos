package com.chniccs.study.demos.recycleview_refresh;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by chniccs on 16/8/1.
 */
public abstract class BaseRecycleviewAdapter extends RecyclerView.Adapter<BaseRecycleviewAdapter.BaseHolder> {

    public class BaseHolder extends RecyclerView.ViewHolder{

        public BaseHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
