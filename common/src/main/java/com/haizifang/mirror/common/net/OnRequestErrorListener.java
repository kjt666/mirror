package com.haizifang.mirror.common.net;

public interface OnRequestErrorListener {

    void onError(String code,String errorMsg);

    void toast(String s);

}
