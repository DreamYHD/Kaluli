package com.example.administrator.kalulli.utils;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2019/5/18.
 */

public class TimeUtil {
    public static String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String current = sdf.format(System.currentTimeMillis()).split(" ")[0];
        return current;
    }
    public static String getNowTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String current = sdf.format(System.currentTimeMillis()).split(" ")[1];
        return current;
    }
    public static String getRealDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String current = sdf.format(System.currentTimeMillis()).split(":")[0]
                +":"+sdf.format(System.currentTimeMillis()).split(":")[1];
        return current;
    }

}
