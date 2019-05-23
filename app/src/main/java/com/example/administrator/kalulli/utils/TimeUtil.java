package com.example.administrator.kalulli.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2019/5/18.
 */

public class TimeUtil {
    public static String getDate(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(System.currentTimeMillis()).split(" ")[0];
    }
    public static String getNowTime(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(System.currentTimeMillis()).split(" ")[1];
    }
    public static String getRealDate(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(System.currentTimeMillis()).split(":")[0]
                +":"+sdf.format(System.currentTimeMillis()).split(":")[1];
    }

}
