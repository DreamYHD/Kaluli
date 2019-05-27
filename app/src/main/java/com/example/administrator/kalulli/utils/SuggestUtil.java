package com.example.administrator.kalulli.utils;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/20.
 */

public class SuggestUtil {

    private static final String TAG = "SuggestUtil";
    public static int number = 0;
    public static Map<String,Integer>map = new HashMap();

    public static Map<String,Integer> getUserList() {
        AVQuery<AVObject> query = new AVQuery<>(TableUtil.USER_TABLE_NAME);
        //1 获取用户列表
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                //2 基于用户特征计算相似度
                if (list != null && list.size() != 0) {
                    for (int i = 0; i < list.size(); i++) {
                        AVObject avObject = list.get(i);
                        AVUser avUser = AVUser.getCurrentUser();
                        double finalHeight = Double.parseDouble(avUser.get(TableUtil.USER_HEIGHT).toString());
                        double finalWeight = Double.parseDouble(avUser.get(TableUtil.USER_WEIGHT).toString());
                        double iHeight = Double.parseDouble(avObject.get(TableUtil.USER_HEIGHT).toString());
                        double iWeight = Double.parseDouble(avObject.get(TableUtil.USER_WEIGHT).toString());
                        double finalIco = finalWeight / (finalHeight * finalHeight);
                        double iIco = iWeight / (iHeight * iHeight);
                        //1
                        if ((finalIco - iIco) <= 3 && (iIco - finalIco) <= 3) {
                            number += 4;
                        }
                        //2
                        if (avObject.get(TableUtil.USER_SEX).equals(avUser.get(TableUtil.USER_SEX))) {
                            number += 2;
                        } else {
                            number -= 1;
                        }
                        //3
                        int finalAge = Integer.parseInt(avUser.get(TableUtil.USER_AGE).toString());
                        int iAge = Integer.parseInt(avObject.get(TableUtil.USER_AGE).toString());
                        if ((finalAge - iAge) <= 5 && (iAge - finalAge) <= 5) {
                            number += 1;
                        } else if ((finalAge - iAge) >= 15 || (iAge - finalAge) >= 15) {
                            number -= 1;
                        }
                        //4
                        String finalMajor = avUser.get(TableUtil.USER_MAJOR).toString();
                        String iMajor = avObject.get(TableUtil.USER_MAJOR).toString();
                        if (levenshtein(iMajor,finalMajor)) {
                            number += 1;
                        }
                        //5
                        String finalLove = avUser.get(TableUtil.USER_LOVE).toString();
                        String iLove = avObject.get(TableUtil.USER_LOVE).toString();
                        if (levenshtein(finalLove,iLove)){
                            number += 1;
                        }
                        map.put(avObject.getObjectId(),number);
                        number = 0;

                    }
                } else {
                    Log.i(TAG, "done: ");
                }
            }
        });
        return map;
    }
    //2 基于用户特征计算相似度
    /**
     *
     * @param str1
     * @param str2
     */
    public static boolean levenshtein(String str1,String str2) {
        //计算两个字符串的长度。
        int len1 = str1.length();
        int len2 = str2.length();
        //建立上面说的数组，比字符长度大一个空间
        int[][] dif = new int[len1 + 1][len2 + 1];
        //赋初值，步骤B。
        for (int a = 0; a <= len1; a++) {
            dif[a][0] = a;
        }
        for (int a = 0; a <= len2; a++) {
            dif[0][a] = a;
        }
        //计算两个字符是否一样，计算左上的值
        int temp;
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                //取三个值中最小的
                dif[i][j] = min(dif[i - 1][j - 1] + temp, dif[i][j - 1] + 1,
                        dif[i - 1][j] + 1);
            }
        }
        System.out.println("字符串\""+str1+"\"与\""+str2+"\"的比较");
        //取数组右下角的值，同样不同位置代表不同字符串的比较
        System.out.println("差异步骤："+dif[len1][len2]);
        //计算相似度
        float similarity =1 - (float) dif[len1][len2] / Math.max(str1.length(), str2.length());
        Log.i(TAG, "levenshtein: "+similarity);
        if (similarity >= 0.5){
            return true;
        }else {
            return false;
        }
    }

    //得到最小值
    private static int min(int... is) {
        int min = Integer.MAX_VALUE;
        for (int i : is) {
            if (min > i) {
                min = i;
            }
        }
        return min;
    }

}
