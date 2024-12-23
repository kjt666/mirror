package com.haizifang.mirror.common.net;

import com.google.gson.annotations.SerializedName;

public class HttpResponse<T> {

    @SerializedName("retCode")
    public String code;

    @SerializedName("msgRet")
    public String msg;

    public T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
