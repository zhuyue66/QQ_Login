package com.example.qq_login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import com.example.qq_login.Listener.UserInfoListener;
import com.example.qq_login.Listener.loginListener;
import com.example.qq_login.util.MyApplication;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {

    private static final String APP_ID = "1105602574";
    private static Tencent mTencent;
    private IUiListener loginListener;
    private final String TAG = "TAG";
    private SharedPreferences.Editor set_userInfo;
    private SharedPreferences get_userInfo;
    @BindView(R.id.button)
    Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mTencent = Tencent.createInstance(APP_ID, MainActivity.this.getApplicationContext());
        set_userInfo = MyApplication.getContext().getSharedPreferences("userInfo",MODE_PRIVATE).edit();
        //SharedPreferences第一次登录"is_first"值为true
        set_userInfo.apply();
        get_userInfo = getSharedPreferences("userInfo",MODE_PRIVATE);
        if (get_userInfo.getBoolean("is_first",true)){
            Log.e(TAG, "++++++++++++in if++++++++++++++");
            loginListener = new loginListener();
            mTencent.login(MainActivity.this, "all", loginListener);
        }else {
            Log.e(TAG, "++++++++++++++++in else++++++++++++++++");
        }
        SharedPreferences get_userInfo = MyApplication.getContext().getSharedPreferences("userInfo",MODE_PRIVATE);
        MainActivity.getTencent().setOpenId(get_userInfo.getString("openID",null));
        Log.e(TAG, "0000000000 in set 000000000");
        MainActivity.getTencent().setAccessToken(get_userInfo.getString("accessToken",null), get_userInfo.getString("expires",null));

        UserInfo userInfo = new UserInfo(MainActivity.this, mTencent.getQQToken());
        userInfo.getUserInfo(new UserInfoListener());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static Tencent getTencent() {
        return mTencent;
    }

}
