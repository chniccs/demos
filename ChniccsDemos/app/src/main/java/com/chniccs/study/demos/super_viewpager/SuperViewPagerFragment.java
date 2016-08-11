package com.chniccs.study.demos.super_viewpager;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chniccs.study.demos.R;
import com.chniccs.study.demos.super_viewpager.transformer.ZoomOutTransformer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chniccs on 16/8/11.
 * 水平和竖起滑动的viewpager的案例
 * 参考：http://www.jianshu.com/p/b9a9717e98e8
 */
public class SuperViewPagerFragment extends Fragment {
    int[] colors = {Color.BLUE, Color.GREEN, Color.GRAY};
    @BindView(R.id.super_viewpager_horizontal)
    HorizontalViewPager mHorizontalViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_super_viewpager, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }


    private void initView() {

        mHorizontalViewPager.setAdapter(new HorizontalAdapter());
    }


    class VerticalAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = new View(container.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            view.setBackgroundColor(colors[position]);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class HorizontalAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_vertical_viewpager, null);
            VerticalViewPager verticalViewPager = (VerticalViewPager) view.findViewById(R.id.super_viewpager_vertical);
            verticalViewPager.setAdapter(new VerticalAdapter());
            verticalViewPager.setPageTransformer(true, new ZoomOutTransformer());
            view.setBackgroundColor(colors[2 - position]);
            TextView textView = (TextView) view.findViewById(R.id.super_viewpager_item_tv);
            textView.setText(position + "");
            container.addView(view);
            return view;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }
}
