package com.chniccs.study.demos.security;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chniccs.study.demos.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chniccs on 16/8/4.
 * AES加解密案例
 */
public class SecurityFragment extends Fragment {
    @BindView(R.id.security_tv_before_encrypt)
    TextView mTvBeforeEncrypt;
    @BindView(R.id.security_tv_after_encrypt)
    TextView mTvAfterEncrypt;
    @BindView(R.id.security_tv_after_decrypt)
    TextView mTvAfterDecrypt;
    private String str = "chniccs";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_security, container, false);
        ButterKnife.bind(this, view);
        doEncrypt();
        return view;
    }

    private void doEncrypt() {
        mTvBeforeEncrypt.setText(str);
        try {
            String encrypt = AESHelper.encrypt(str, "chniccs");
            mTvAfterEncrypt.setText(encrypt);
            mTvAfterDecrypt.setText(AESHelper.decrypt(encrypt, "chniccs"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
