package com.chniccs.study.demos.rxjava;

/**
 * Created by chniccs on 16/8/5.
 */
public class RESTResult<T> {
    public static final int SUCCESS=0;
    public static final int SIGN_OUT=1;

    public int status;
    public String message;

    public T data;
}
