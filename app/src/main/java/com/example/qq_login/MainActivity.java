package com.example.qq_login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.qq_login.Listener.UserInfoListener;
import com.example.qq_login.Listener.loginListener;
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
    IUiListener loginListener;
    private final String TAG = "TAG";
    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mTencent = Tencent.createInstance(APP_ID, MainActivity.this.getApplicationContext());
        loginListener = new loginListener();

        mTencent.login(MainActivity.this, "all", loginListener);
        mTencent.setOpenId("A9A77917358A693A1E1D19094FFD807E");
        mTencent.setAccessToken("83DE6B216AAD2743EFAF69EC6F9AFE10", "7776000");
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
