package com.chniccs.study.demos.firebase_message;

import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by ccs on 16/12/8.
 * 获得deviceToken的服务
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
    }
}
