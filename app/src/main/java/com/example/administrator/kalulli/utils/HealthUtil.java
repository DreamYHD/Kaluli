package com.example.administrator.kalulli.utils;

import com.avos.avoscloud.AVUser;

/**
 * Created by Administrator on 2019/5/18.
 */

public class HealthUtil {
    //“克莱托指数”体重(kg)÷身高²(m)20-25正常，20以下瘦，25以上胖 某人是60kg，1.7m那就是60÷1.7²=20.76124567474 正常
    public static double getHealthNum(double weight, double height) {
        return weight / (height * height);
    }

    public static String getHealth(String weight, String height) {
        double health = Double.parseDouble(weight) / (Double.parseDouble(height) * Double.parseDouble(height));
        String healthThing = "";
        if (health > 25) {
            healthThing = "胖";
        } else if (health < 20) {
            healthThing = "瘦";
        } else {
            healthThing = "正常";
        }
        return healthThing;
    }

    //    男：[66 + 1.38 x 体重(kg) + 5 x 高度(cm) - 6.8 x 年龄] x 活动量
//    女：[65.5 + 9.6 x 体重(kg) + l.9 x 高度(cm) - 4.7 x 年龄] x 活动量
//    一般人的活动量由1.1 - 1.3不等，活动量高数值便愈高，甚至有可能高出1.3的数值，若平日只坐在办公室工作的女性，活动量约1.1，运动量高的人约为1.3。
//    例如：身高156cm，体重46kg的18岁女性，每日所需的卡路里为1580Kca|。
//    公式：[665 + 9.6 x 46 + 1.9 x 156 - 4.7 x 18] x 1.2 = 1580Kca|
    public static String getKC() {
        if (AVUser.getCurrentUser() != null) {
            AVUser avUser = AVUser.getCurrentUser();
            int age = Integer.parseInt(avUser.get(TableUtil.USER_AGE).toString());
            double height = 100 * Double.parseDouble(avUser.get(TableUtil.USER_HEIGHT).toString());
            double weight = Double.parseDouble(avUser.get(TableUtil.USER_WEIGHT).toString());
            String major = avUser.get(TableUtil.USER_MAJOR).toString();
            String sex = avUser.get(TableUtil.USER_SEX).toString();

            double act;
            if (major.contains("运动员")) {
                act = 1.3;
            } else {
                act = 1.2;
            }if (sex == "男"){
                return  (660+ 1.37 * weight + 5 * height - 6.8 * age ) * act+"";
            }else {
                return  (655 + 9.6 * weight + 1.9 * height - 4.7 * age ) * act+"";

            }

        } else {
            return  0+"";
        }
    }


}
