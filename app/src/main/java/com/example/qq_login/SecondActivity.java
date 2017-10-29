package com.example.qq_login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.qq_login.Share.ShareContract;
import com.example.qq_login.Share.ShareModel;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener,ShareContract.ISecondView {

    @BindView(R.id.share_QQ)
    Button shareQQ;
    @BindView(R.id.share_QZone)
    Button shareQZone;
    @BindView(R.id.logout)
    Button logout;

    private ShareModel shareModel;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        shareModel = new ShareModel(this);

        shareQQ.setOnClickListener(this);
        shareQZone.setOnClickListener(this);
        logout.setOnClickListener(this);
        list = new ArrayList<>();
        list.add("https://api.i-meto.com/bing");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.share_QQ:
                shareModel.share_QQ("测试","测试","https://api.i-meto.com/bing","https://www.baidu.com","测试");
                break;
            case R.id.share_QZone:
                shareModel.share_QQZone(list,"测试","测试","https://www.baidu.com");
                break;
            case R.id.logout:
                shareModel.logout();
            default :
                break;
        }
    }

    @Override
    public SecondActivity get() {
        return this;
    }

    public void abc(View v){

    }

}
