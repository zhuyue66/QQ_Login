package com.example.qq_login.Listener;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import com.example.qq_login.util.MyApplication;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by zhuyue66 on 2017/10/22.
 */

public class UserInfoListener extends BaseListener {

    private final String TAG = "TAG";

    @Override
    public void onComplete(Object object) {
        Toast.makeText(MyApplication.getContext(),"获取成功",Toast.LENGTH_SHORT).show();
        Log.e(TAG, "onComplete: 用户信息" + object.toString() );
        JSONObject jsonObject = (JSONObject) object;
        try{
            String nickname = jsonObject.getString("nickname");
            String icon = jsonObject.getString("figureurl_qq_2");
            SharedPreferences.Editor set_userInfo = MyApplication.getContext().getSharedPreferences("userInfo",MODE_PRIVATE).edit();
            set_userInfo.putString("nickname",nickname);
            set_userInfo.putString("icon",icon);
            set_userInfo.apply();
        }catch (JSONException e){
            e.printStackTrace();
        }
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
