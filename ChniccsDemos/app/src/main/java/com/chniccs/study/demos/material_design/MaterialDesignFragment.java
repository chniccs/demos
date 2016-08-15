package com.chniccs.study.demos.material_design;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.chniccs.study.demos.R;
import com.chniccs.study.demos.app.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chniccs on 16/8/15.
 * 这是一个用来展示材料设计语言的案例，主要是为了整理起来。
 */
public class MaterialDesignFragment extends Fragment {
    @BindView(R.id.md_viewpager)
    ViewPager mViewPager;

    @BindView(R.id.md_tablayout)
    TabLayout mTablayout;


    String[] strs = {"appbar", "collasping"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_material_design,container,false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }


    public void initView() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
                                  @Override
                                  public android.support.v4.app.Fragment getItem(int position) {
                                      return fragmentFactory(position);
                                  }

                                  @Override
                                  public int getCount() {
                                      return strs.length;
                                  }

                                  @Override
                                  public CharSequence getPageTitle(int position) {
                                      return strs[position];
                                  }
                              }

        );
        mTablayout.setupWithViewPager(mViewPager);

    }

    public Fragment fragmentFactory(int i) {
        BaseFragment fragment = null;
        switch (i) {
            case 0:
                fragment=new AppBarFragment();
                break;
            case 1:
                fragment=new CollapsingFragment();
                break;
        }
        return fragment;
    }

}
