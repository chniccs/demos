package com.chniccs.study.demos.material_design;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chniccs.study.demos.R;
import com.chniccs.study.demos.app.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chniccs on 16/8/15.
 */
public class SnackBarFragment extends BaseFragment {

    @Override
    public int getLayout() {
        return R.layout.md_snackbar;
    }

    @Override
    public void initView() {

    }

    @OnClick(R.id.md_floatingaction)
    public void click(View v) {
        Snackbar.make(mView,"snackbar",Snackbar.LENGTH_SHORT)
                .setAction("啥都不做",new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        //  do something you want
                    }
                })
                .show();
    }

}
