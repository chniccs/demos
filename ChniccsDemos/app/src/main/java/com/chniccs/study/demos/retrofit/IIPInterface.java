package com.chniccs.study.demos.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by chniccs on 16/8/5.
 */
public interface IIPInterface {
    @GET("/service/getIpInfo.php")
    Call<IPBean> getIP(@Query("ip") String ip);

    @GET("/service/getIpInfo.php")
    Observable<IPBean> getIPByRxjava(@Query("ip") String ip);
}
