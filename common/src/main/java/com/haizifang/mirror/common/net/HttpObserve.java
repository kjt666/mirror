package com.haizifang.mirror.common.net;

import android.content.Context;
import android.os.NetworkOnMainThreadException;
import android.text.TextUtils;

import androidx.annotation.CallSuper;

import com.kongzue.dialogx.dialogs.WaitDialog;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;
import retrofit2.Response;

public abstract class HttpObserve<T> implements Observer<Response<HttpResponse<T>>> {

    //    private Context mContext;
    private Context activity;
    private final String SUCCESS_CODE = "000000";
    private Disposable disposable;
    //    private Dialog dialog;
    private boolean showLoading = false;

    private boolean showErrorToast = true;

    public HttpObserve() {
    }

    public HttpObserve(Context context) {
        this.activity = context;
    }

    public HttpObserve(boolean showLoading) {
        this.showLoading = showLoading;
        showLoading();
    }

    public HttpObserve(int show) {
        showErrorToast = (1 == show);
        showLoading();
    }


    @Override
    public void onSubscribe(Disposable disposable) {
        this.disposable = disposable;

    }

    @Override
    public void onNext(Response<HttpResponse<T>> bResponse) {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        HttpResponse<T> response = bResponse.body();
        hideLoading();
        if (response == null) {
            onComplete();
            return;
        }
        if (SUCCESS_CODE.equalsIgnoreCase(response.code)) {
            onSuccess(response.getData());
            onSuccess(response);
        } else {
            HttpRetrofit.getInstance().getListener().onError(response.code, TextUtils.isEmpty(response.msg) ? "" : response.msg);
            onFailure(response.code, TextUtils.isEmpty(response.msg) ? "" : response.msg);
        }
        onComplete();
    }

    @Override
    public final void onError(Throwable throwable) {
        throwable.printStackTrace();

        String errorCode = "0";
        String errorMsg = "";
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            errorCode = String.valueOf(httpException.code());
            errorMsg = httpException.getMessage();
        } else if (throwable instanceof SocketTimeoutException) {  //VPN open
            errorCode = "1000001";
            errorMsg = "服务器响应超时";
        } else if (throwable instanceof ConnectException) {
            errorCode = "1000002";
            errorMsg = "网络连接异常";
        } else if (throwable instanceof UnknownHostException) {
            errorCode = "1000003";
            errorMsg = "无法解析主机";
        } else if (throwable instanceof UnknownServiceException) {
            errorCode = "1000004";
            errorMsg = "未知的服务器错误";
        } else if (throwable instanceof IOException) {
            errorCode = "1000005";
            errorMsg = "没有网络";
        } else if (throwable instanceof NetworkOnMainThreadException) {
            errorCode = "1000006";
            errorMsg = "错误的网络请求线程";
        } else if (throwable instanceof RuntimeException) {
            errorCode = "1000007";
            errorMsg = "运行时错误" + throwable.toString();
        } else {
            errorCode = "1000008";
            errorMsg = "未知错误" + throwable.toString();
        }
        hideLoading();
        if (showErrorToast) {
            HttpRetrofit.getInstance().getListener().toast("Error de red, inténtalo otra vez");

        }
        onFailureForNetwork(errorCode, errorMsg);
        onComplete();
    }

    @CallSuper
    @Override
    public void onComplete() {
    }

    public abstract void onSuccess(T t);

    public void onSuccess(HttpResponse<T> response) {

    }


    @CallSuper
    public void onFailure(String code, String message) {

    }

    @CallSuper
    public void onFailureForNetwork(String code, String erromessage) {

        onFailure(code, erromessage);
    }

    private void showLoading() {
        if (showLoading) {
            WaitDialog.show("");
        }
    }

    private void hideLoading() {
        if (showLoading) {
            WaitDialog.dismiss();
        }
    }

}
