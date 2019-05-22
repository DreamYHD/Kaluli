package com.example.administrator.kalulli.utils;

/**
 * Created by Administrator on 2019/5/18.
 */

public class HealthUtil {
    //“克莱托指数”体重(kg)÷身高²(m)20-25正常，20以下瘦，25以上胖 某人是60kg，1.7m那就是60÷1.7²=20.76124567474 正常
    public double getHealthNum(double weight,double height){
        return weight / (height * height);
    }
    public String getHealth(double weight,double height){
        double health = weight / (height * height);
        String healthThing = "";
        if (health > 25){
            healthThing = "胖";
        }else if (health < 20){
            healthThing = "瘦";
        }else {
            healthThing = "正常";
        }
        return  healthThing;
    }
}
