package com.example.qq_login.Listener;

import android.util.Log;
import android.widget.Toast;
import com.example.qq_login.util.MyApplication;
import com.tencent.tauth.UiError;

/**
 * Created by zhuyue66 on 2017/10/22.
 */

public class UserInfoListener extends BaseListener {

    private final String TAG = "TAG";

    @Override
    public void onComplete(Object object) {
        Toast.makeText(MyApplication.getContext(),"获取成功",Toast.LENGTH_SHORT).show();
        Log.e(TAG, "onComplete: 用户信息" + object.toString() );
    }

    @Override
    public void onCancel() {
        Toast.makeText(MyApplication.getContext(),"获取取消",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(UiError uiError) {
        Toast.makeText(MyApplication.getContext(),"获取失败",Toast.LENGTH_SHORT).show();
    }
}
