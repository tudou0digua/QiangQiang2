package com.cb.qiangqiang2.data.api;

/**
 * Created by cb on 2016/8/31.
 */
public class ApiException extends RuntimeException {
    public ApiException() {
        super();
    }

    public ApiException(String message) {
        super(message);
    }
}
