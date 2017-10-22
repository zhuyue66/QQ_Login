package com.example.qq_login.Listener;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.qq_login.MainActivity;
import com.example.qq_login.util.MyApplication;
import com.tencent.tauth.UiError;
import org.json.JSONException;
import org.json.JSONObject;
import static android.content.Context.MODE_PRIVATE;


/**
 * Created by zhuyue66 on 2017/10/22.
 */

public class loginListener extends BaseListener {

    private final String TAG = "TAG";

    @Override
    public void onComplete(Object object) {
        Log.e(TAG, "onComplete: 登录成功返回的数据" + object.toString() );
        Toast.makeText(MyApplication.getContext(),"登录成功",Toast.LENGTH_SHORT).show();
        try{
            JSONObject jsonObject = (JSONObject) object;
            String openID = jsonObject.getString("openid");
            String accessToken = jsonObject.getString("access_token");
            String expires = jsonObject.getString("expires_in");
            Log.e(TAG, "***********************" + openID + accessToken + expires);
            //数据存储SharedPreferences
            SharedPreferences.Editor set_userInfo = MyApplication.getContext().getSharedPreferences("userInfo",MODE_PRIVATE).edit();
            set_userInfo.putString("openID",openID);
            set_userInfo.putString("expires",expires);
            set_userInfo.putString("accessToken",accessToken);
            set_userInfo.putBoolean("is_first",false);
            set_userInfo.apply();

            /*SharedPreferences get_userInfo = MyApplication.getContext().getSharedPreferences("userInfo",MODE_PRIVATE);
            MainActivity.getTencent().setOpenId(get_userInfo.getString("openID",null));
            MainActivity.getTencent().setAccessToken(get_userInfo.getString("accessToken",null), get_userInfo.getString("expires",null));*/

            /*MainActivity.getTencent().setOpenId(openID);
            Log.e(TAG, "----------in set-------------------");
            MainActivity.getTencent().setAccessToken(accessToken,expires);*/

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onCancel() {
        Toast.makeText(MyApplication.getContext(),"登录取消",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(UiError uiError) {
        Toast.makeText(MyApplication.getContext(),"登录失败",Toast.LENGTH_SHORT).show();
    }

}
