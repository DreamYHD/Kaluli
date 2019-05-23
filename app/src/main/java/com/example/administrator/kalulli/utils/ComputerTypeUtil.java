package com.example.administrator.kalulli.utils;

import android.util.Log;

/**
 * Created by Administrator on 2019/5/20.
 */

public class ComputerTypeUtil {
    private static final String TAG = "ComputerTypeUtil";
    public static String getType(){
        String type = "";
        String nowTime = TimeUtil.getNowTime();
        Log.i(TAG, "getType: "+nowTime);
        String []strings = nowTime.split(":");
        int hour = Integer.valueOf(strings[0]);
        if (hour >= 11 && hour <= 15){
            type = "午餐";
        }else if (hour >= 15 && hour <= 24){
            type = "晚餐";
        }else {
            type = "早餐";
        }
        return  type;
    }
}
