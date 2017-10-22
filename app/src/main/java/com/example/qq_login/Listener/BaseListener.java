package com.example.qq_login.Listener;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/**
 * Created by zhuyue66 on 2017/10/22.
 */

public class BaseListener implements IUiListener {
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public void onComplete(Object o) {

    }

    @Override
    public void onError(UiError uiError) {

    }

    @Override
    public void onCancel() {

    }
}
