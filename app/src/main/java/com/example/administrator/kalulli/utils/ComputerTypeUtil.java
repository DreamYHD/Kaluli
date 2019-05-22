package com.example.administrator.kalulli.utils;

/**
 * Created by Administrator on 2019/5/20.
 */

public class ComputerTypeUtil {
    public static String getType(){
        String type = "";
        String nowTime = TimeUtil.getNowTime();
        String []strings = nowTime.split(":");
        int hour = new Integer(strings[0]);
        if (hour >= 11 && hour <= 14){
            type = "午餐";
        }else if (hour >= 17 && hour <= 24){
            type = "晚餐";
        }else {
            type = "早餐";
        }
        return  type;
    }
}
