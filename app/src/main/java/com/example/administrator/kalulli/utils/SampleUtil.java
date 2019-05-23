package com.example.administrator.kalulli.utils;

import android.util.Log;

import com.baidu.aip.imageclassify.AipImageClassify;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2019/4/1.
 */

public class SampleUtil {
    //设置APPID/AK/SK
    public static final String APP_ID = "15901626";
    public static final String API_KEY = "gSmHI8zmM0TSr8XPlnnSUEHV";
    public static final String SECRET_KEY = "FoxHmL9uwnic37NPzf0dG4oezVWQSi7g";
    private static final String TAG = "SampleUtil";
    public static final AipImageClassify client = new AipImageClassify(APP_ID,API_KEY,SECRET_KEY);
}
