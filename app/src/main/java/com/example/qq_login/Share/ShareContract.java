package com.example.qq_login.Share;

import com.example.qq_login.SecondActivity;
import java.util.ArrayList;

/**
 * Created by zhuyue66 on 2017/10/22.
 */

public interface ShareContract {

    interface QQ_Share{
        void share_QQ(String title,String summary,String imageUrl,String clickUrl,String AppName);
        void share_QQZone(ArrayList<String> list, String title, String summary, String targetUrl);
        void logout();
    }

    interface ISecondView{
        SecondActivity get();
    }

}
