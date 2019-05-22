package com.example.administrator.kalulli;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by Administrator on 2019/5/17.
 */

public class MyApplication  extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"V530QaFzWhNAKDkbfeRKOtCL-gzGzoHsz","5zzEnhezBiJUeyBkc3InA1DA");
        AVOSCloud.setDebugLogEnabled(true);
    }
}
