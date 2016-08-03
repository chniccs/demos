package com.chniccs.study.demos.readerscript;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.chniccs.study.demos.R;
import com.chniccs.study.demos.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 通过readerScript的api进行动态模糊处理的示例，参考http://blog.csdn.net/wl9739/article/details/51955598
 */
public class ReaderScriptFragment extends Fragment {

    private Bitmap mBitmap;
    @BindView(R.id.readerscript_iv_blur)
    ImageView mIvBlur;
    @BindView(R.id.readerscript_iv_origin)
    ImageView mIvOrigin;
    @BindView(R.id.readerscript_seekbar)
    SeekBar mSeeBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reader_script, container, false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    private void initView() {
        mBitmap = UIUtils.readBitMap_8888(getActivity(), R.drawable.dayu);//拿到资源图片对象
        mBitmap = BlurBitmap.blur(getActivity(), mBitmap);//模糊处理
        mIvBlur.setImageBitmap(mBitmap);
        mSeeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mIvOrigin.setImageAlpha((int) (i*2.55));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBitmap != null) {
            mBitmap.recycle();
            mBitmap = null;
        }
    }
}
