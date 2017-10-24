package com.example.qq_login.Share;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import com.example.qq_login.Listener.BaseListener;
import com.example.qq_login.MainActivity;
import com.example.qq_login.util.MyApplication;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import java.util.ArrayList;

/**
 * Created by zhuyue66 on 2017/10/22.
 */

public class ShareModel implements ShareContract.QQ_Share {


    private ShareContract.ISecondView iSecondView;

    public ShareModel(ShareContract.ISecondView iSecondView1){
        this.iSecondView = iSecondView1;
    }

    /**
     * 分享到QQ
     *
     * @param title    标题
     * @param summary  摘要
     * @param imageUrl 图片url
     * @param clickUrl 点击详情url
     * @param AppName  应用名
     */
    @Override
    public void share_QQ(String title,String summary,String imageUrl,String clickUrl,String AppName) {
        final Bundle share = new Bundle();
        /*分享的类型*/
        share.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        /*分享的标题*/
        share.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        /*分享的摘要*/
        share.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        /*分享后被点击的URL*/
        share.putString(QQShare.SHARE_TO_QQ_TARGET_URL, imageUrl);
        /*分享图片的URL或者本地路径 */
        share.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, clickUrl);
        /*手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替*/
        share.putString(QQShare.SHARE_TO_QQ_APP_NAME, AppName);
        MainActivity.getTencent().shareToQQ(iSecondView.get(), share, new BaseListener());
    }

    /**
     * 分享到QQ空间
     *
     * @param list      图片list
     * @param title     标题
     * @param summary   摘要
     * @param targetUrl 点击详情url
     */
    @Override
    public void share_QQZone(ArrayList<String> list, String title, String summary, String targetUrl) {
        Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);// 标题
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);// 摘要
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,targetUrl );// 内容地址
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, list);// 图片地址
        MainActivity.getTencent().shareToQzone(iSecondView.get(), params, new BaseListener());
    }

    /**
     * 注销登录
     * */
    @Override
    public void logout() {
        Toast.makeText(MyApplication.getContext(),"注销成功",Toast.LENGTH_SHORT).show();
        MainActivity.getTencent().logout(MyApplication.getContext());
        SharedPreferences.Editor set_userInfo = MyApplication.getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE).edit();
        set_userInfo.putBoolean("is_first",true);
        set_userInfo.putString("icon",null);
        set_userInfo.putString("nickname",null);
        set_userInfo.putString("accessToken",null);
        set_userInfo.putString("expires",null);
        set_userInfo.putString("openID",null);
        set_userInfo.apply();
    }
}
