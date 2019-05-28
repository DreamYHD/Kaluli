package com.example.administrator.kalulli.utils;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/5/26.
 */

public class DailyUtil {
    private static final String TAG = "DailyUtil";
    public static String getPlanType() {
        AVUser avUser = AVUser.getCurrentUser();
        String healthType = HealthUtil.getHealth(avUser.get(TableUtil.USER_WEIGHT).toString(), avUser.get(TableUtil.USER_HEIGHT).toString());
        if (healthType.equals("胖")) {

            return "减肥";
        } else if (healthType.equals("瘦")) {
            return "增重";
        } else {
            return "增肌";
        }
    }

    public static List<String> getPlanContent() {
        String s = getPlanType();
        List<String>result = new ArrayList();
        switch (s) {
            case "减肥":
                result.add(" 您目前的体重过重,请注意合理控制饮食");
                result.add(" 最好的运动方法是你在规定的时间内，尽量多次的进行小规模的有氧运动而不要一次的运动过量，这样的减肥效果会更佳。虽然说睡觉能够忘记对食物的欲望，但是对于减肥真的起不到多少作用。而且睡觉的时间越长，越容易导致身体的代谢率降到最低，以至于身体消耗的能量就越少，身体胆固醇和脂肪的合成速度加快，从而导致肥胖问题");
                break;
            case "增重":
                result.add(" 您目前的体重过轻,建议保障营养摄入量");
                result.add(" 吃容易消化并且不油腻的食物，规律进餐，进食时细嚼慢咽，专心致志，保持心情愉快。平时里的食物的烹饪以柔软、温热为好，可以常喝新鲜的酸奶，吃面包。馒头等发酵面食，以及各种发酵后的豆制品。同时，再做一些比较温和轻松的运动，比如散步，慢跑，交谊舞，太极拳之类低强度的运动，可以放松心情，并改善消化吸收");
                break;
            case "增肌":
                result.add(" 您目前的体重正常.要注意饮食搭配,好好保持");
                result.add(" 最好的运动方法是你在规定的时间内，尽量多次的进行小规模的有氧运动而不要一次的运动过量，这样的减肥效果会更佳。");
                break;
            default:
        }
        return result;
    }

    public static String testType(String objectId) {
        final String[] result = {""};
        final boolean[] is = {true};
        final AVObject avObject = AVObject.createWithoutData(TableUtil.DAILY_TABLE_NAME, objectId);
        avObject.fetchInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject object, AVException e) {
                if (e == null){
                    Log.i(TAG, "done: testType");
                    String morning = avObject.get(TableUtil.DAILY_MORNING).toString();
                    String afternoon = avObject.get(TableUtil.DAILY_AFTERNOON).toString();
                    String evening = avObject.get(TableUtil.DAILY_EVENING).toString();
                    if (morning == null || morning.equals("0")){
                        result[0] = "不佳";
                        is[0] = false;
                    }else  if (Double.parseDouble(morning) <= 50 || Double.parseDouble(morning) >= 350){
                        result[0] = "不佳";
                        is[0] = false;
                    }
                    if (afternoon == null || morning.equals("0")){
                        result[0] = "不佳";
                        is[0] = false;
                    }else if (Double.parseDouble(afternoon) <= 150 || Double.parseDouble(afternoon) >= 650){
                        result[0] = "不佳";
                        is[0] = false;
                    }
                    if (evening == null || evening.equals("0")){
                        result[0] = "不佳";
                        is[0] = false;
                    }else if (Double.parseDouble(evening) <= 50 || Double.parseDouble(evening) >= 450){
                        result[0] = "不佳";
                        is[0] = false;
                    }
                }else{
                    Log.e(TAG, "done: "+e.getMessage() );
                }
            }
        });
        if (is[0]){
            return "正常";
        }else {
            return result[0];
        }

    }
    public static List<String> testContent(String objectId) {
        final String[] result = {""};
        final List<String>list = new ArrayList<>();
        final boolean[] is = {true};
        final AVObject avObject = AVObject.createWithoutData(TableUtil.DAILY_TABLE_NAME, objectId);
        avObject.fetchInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject object, AVException e) {
                if (e == null){
                    Log.i(TAG, "done:  e == null");
                    String morning = avObject.get(TableUtil.DAILY_MORNING).toString();
                    String afternoon = avObject.get(TableUtil.DAILY_AFTERNOON).toString();
                    String evening = avObject.get(TableUtil.DAILY_EVENING).toString();
                    //1
                    if (morning == null || morning.equals("")){
                        is[0] = false;
                        list.add(" 吃早餐,每天都要吃早餐,保证代谢且每天食欲更加稳定");
                    }else  if (Double.parseDouble(morning) <= 50 ||Double.parseDouble(morning) >= 350){
                        is[0] = false;
                        list.add(" 三餐要均衡搭配，进食过多过少都伤胃");
                    }
                    if (afternoon == null || morning.equals("")){
                        is[0] = false;
                        list.add(" 午餐是三餐中比较重要的一餐哦，不吃午餐会导致身体呈现下坡的状态");
                    }else if (Double.parseDouble(afternoon) <= 150 || Double.parseDouble(afternoon) >= 650){
                        is[0] = false;
                        list.add(" 午餐要吃饱哦，但是也不能吃的太多");
                    }
                    if (evening == null || evening.equals("")){
                        is[0] = false;
                        list.add(" 晚餐十分重要,必须吃「好」。如果进食不当,过饱、过晚,都可能对人体健康造成一定的损害");
                    }else if (Double.parseDouble(evening) <= 50 || Double.parseDouble(evening) >= 450){
                        is[0] = false;
                        list.add(" 晚餐要吃七分饱，每餐食物不可过多或者过少，避免大胃口哦");
                    }
                    //2
                    if ((Double.parseDouble(morning) + Double.parseDouble(afternoon) + Double.parseDouble(evening)) <= 700){
                        is[0] = false;
                        list.add(" 您今天的热量摄取未能达标哦，要多吃点哦");
                    }else if ((Double.parseDouble
                            (morning) + Double.parseDouble(afternoon) + Double.parseDouble(evening)) > 700){
                        is[0] = false;
                        list.add(" 您今天的热量过多,要注意多运动");
                    }else{
                        if (is[0]){
                            list.add(" 您今天的热量摄取已经达标");
                        }else {
                            list.add(" 虽然总量达标了,还是要注意各餐分配合理哦");
                        }
                    }
                }else{
                    Log.e(TAG, "done: "+e.getMessage() );
                }
            }
        });
        return list;
    }

}
