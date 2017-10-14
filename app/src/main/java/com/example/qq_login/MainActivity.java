package com.example.qq_login;

import android.content.Intent;
import android.icu.util.JapaneseCalendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.jar.JarException;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String APP_ID = "1105602574";   //官方获取的APPID
    private Tencent mTencent;
    private IUiListener mIUiListener; //登录
    private IUiListener userInfoListener; //获取用户信息
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //获取到Tencent的实例，必须使用Tencent类才能调用SDK中的各种接口
        mTencent = Tencent.createInstance(APP_ID,MainActivity.this.getApplicationContext());
    }

    /**
     * 登录
     * */
    public void buttonLogin(View v){

        mIUiListener = new IUiListener(){

            @Override
            public void onComplete(Object object) {
                Log.e(TAG, "onComplete: 登录成功返回的数据" + object.toString() );
                TextView textView = (TextView) findViewById(R.id.login);
                textView.setText(object.toString());
                Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                try{
                    JSONObject jsonObject = (JSONObject) object;
                    String openID = jsonObject.getString("openid");
                    String accessToken = jsonObject.getString("access_token");
                    String expires = jsonObject.getString("expires_in");
                    mTencent.setOpenId(openID);
                    mTencent.setAccessToken(accessToken, expires);
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this,"登录取消",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(UiError uiError) {
                Toast.makeText(MainActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
            }

        };
        /**
         * context-上下文
         * 第二个参数SCOPO 是一个String类型的字符串，表示一些权限
         *官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。
         *例如：SCOPE = “get_user_info,add_t”；
         *所有权限用“all”
         *事件监听器-IUiListener接口的实例
         */
        mTencent.login(MainActivity.this,"all", mIUiListener);

    }

    /**
     * 获取用户信息
     **/
    public void buttonGet(View v){

        userInfoListener = new IUiListener(){

            @Override
            public void onComplete(Object object) {
                Toast.makeText(MainActivity.this,"获取成功",Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onComplete: 用户信息" + object.toString() );
                CircleImageView imageView = (CircleImageView) findViewById(R.id.image);
                TextView textView = (TextView) findViewById(R.id.userInfo);
                textView.setText(object.toString());
                try{
                    JSONObject jsonObject = (JSONObject) object;
                    String figureUrl = jsonObject.getString("figureurl_qq_2");
                    Glide.with(MainActivity.this).load(figureUrl).into(imageView);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this,"获取取消",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(UiError uiError) {
                Toast.makeText(MainActivity.this,"获取失败",Toast.LENGTH_SHORT).show();
            }

        };
        userInfo = new UserInfo(MainActivity.this, mTencent.getQQToken());
        userInfo.getUserInfo(userInfoListener);
    }

    /**
     * 分享
     * */
    public void buttonShare(View v){

        IUiListener shareListener = new IUiListener () {
            @Override
            public void onComplete(Object o) {

            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        };
        final Bundle params = new Bundle();
        /*SHARE_TO_QQ_KEY_TYPE-分享的类型，图文分享-SHARE_TO_QQ_TYPE_DEFAULT*/
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        /*分享的标题*/
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "测试");
        /*分享的摘要*/
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "测试");
        /*分享后被点击的URL*/
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "https://www.baidu.com");
        /*分享图片的URL或者本地路径 */
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        /*手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替*/
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "测试应用1105602574");
        mTencent.shareToQQ(MainActivity.this, params, shareListener);
    }


    /**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 撤销登录状态退出登录
     * */
    @Override
    protected void onDestroy() {
        super.onDestroy();
            mTencent.logout(this);
    }
}
