package com.example.administrator.kalulli.ui.daily;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.base.BaseActivity;
import com.example.administrator.kalulli.utils.DailyUtil;
import com.example.administrator.kalulli.utils.HealthUtil;
import com.example.administrator.kalulli.utils.TableUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DailyActivity extends BaseActivity {

    private static final String TAG = "DailyActivity";
    @BindView(R.id.back_daily_img)
    ImageView backDailyImg;
    @BindView(R.id.plan_tv)
    TextView planTv;
    @BindView(R.id.test_type_tv)
    TextView testTypeTv;
    @BindView(R.id.plan_content_tv)
    TextView planContentTv;
    @BindView(R.id.test_content_tv)
    TextView testContentTv;
    String planType = "";
    List<String> listPlan = new ArrayList<>();
    String testType = "";
    List<String> testContent = new ArrayList<>();
    String id;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            planTv.setText(planType);
            testTypeTv.setText(testType);
        }
    };
    private Handler TestContentHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            StringBuilder stringTest = new StringBuilder();
            for (int i = 0; i < testContent.size() ; i++) {
                Log.i(TAG, "handleMessage: "+testContent.get(i));
                stringTest.append((i+1)+"."+testContent.get(i)+"\n");
            }
            testContentTv.setText(stringTest.toString());
        }
    };
    private Handler PlanContentHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            StringBuilder stringPlan = new StringBuilder();
            for (int i = 0; i < listPlan.size() ; i++) {
                Log.i(TAG, "handleMessage: "+listPlan.get(i));
                stringPlan.append((i+1)+"."+listPlan.get(i)+"\n");
            }
            planContentTv.setText(stringPlan.toString());

        }
    };
    private Handler TestTypeHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            testTypeTv.setText(testType);
        }
    };

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {
        id = getIntent().getStringExtra("id");
        getData();

    }

    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                planType = getPlanType();
                getPlanContent();
                testType(id);
                testContent(id);
                handler.sendEmptyMessage(0);
            }
        }).start();


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_daily;
    }

    @OnClick(R.id.back_daily_img)
    public void onViewClicked() {
        mActivity.finish();
    }

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

    public  void getPlanContent() {
        String s = getPlanType();
        switch (s) {
            case "减肥":
                listPlan.add(" 您目前的体重过重,请注意合理控制饮食");
                listPlan.add(" 最好的运动方法是你在规定的时间内，尽量多次的进行小规模的有氧运动而不要一次的运动过量，这样的减肥效果会更佳。虽然说睡觉能够忘记对食物的欲望，但是对于减肥真的起不到多少作用。而且睡觉的时间越长，越容易导致身体的代谢率降到最低，以至于身体消耗的能量就越少，身体胆固醇和脂肪的合成速度加快，从而导致肥胖问题");
                break;
            case "增重":
                listPlan.add(" 您目前的体重过轻,建议保障营养摄入量");
                listPlan.add(" 吃容易消化并且不油腻的食物，规律进餐，进食时细嚼慢咽，专心致志，保持心情愉快。平时里的食物的烹饪以柔软、温热为好，可以常喝新鲜的酸奶，吃面包。馒头等发酵面食，以及各种发酵后的豆制品。同时，再做一些比较温和轻松的运动，比如散步，慢跑，交谊舞，太极拳之类低强度的运动，可以放松心情，并改善消化吸收");
                break;
            case "增肌":
                listPlan.add(" 您目前的体重正常.要注意饮食搭配,好好保持");
                listPlan.add(" 最好的运动方法是你在规定的时间内，尽量多次的进行小规模的有氧运动而不要一次的运动过量，这样的减肥效果会更佳。");
                break;
            default:
        }
        PlanContentHandle.sendEmptyMessage(0);

    }

    public  void testType(String objectId) {
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
                        testType = "不佳";
                        is[0] = false;
                    }else  if (Double.parseDouble(morning) <= 50 || Double.parseDouble(morning) >= 550){
                        testType = "不佳";
                        is[0] = false;
                    }
                    if (afternoon == null || morning.equals("0")){
                        testType = "不佳";
                        is[0] = false;
                    }else if (Double.parseDouble(afternoon) <= 150 || Double.parseDouble(afternoon) >= 1450){
                        testType = "不佳";
                        is[0] = false;
                    }
                    if (evening == null || evening.equals("0")){
                        testType = "不佳";
                        is[0] = false;
                    }else if (Double.parseDouble(evening) <= 50 || Double.parseDouble(evening) >= 750){
                        testType = "不佳";
                        is[0] = false;
                    }
                    if (is[0]){
                        testType = "良好";
                    }
                    TestTypeHandle.sendEmptyMessage(0);
                }else{
                    Log.e(TAG, "done: "+e.getMessage() );
                }
            }
        });

    }
    public void testContent(String objectId) {
        final String[] result = {""};
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
                        testContent.add(" 吃早餐,每天都要吃早餐,保证代谢且每天食欲更加稳定");
                    }else  if (Double.parseDouble(morning) <= 50 ||Double.parseDouble(morning) >= 550){
                        is[0] = false;
                        testContent.add(" 三餐要均衡搭配，进食过多过少都伤胃");
                    }
                    if (afternoon == null || morning.equals("")){
                        is[0] = false;
                        testContent.add(" 午餐是三餐中比较重要的一餐哦，不吃午餐会导致身体呈现下坡的状态");
                    }else if (Double.parseDouble(afternoon) <= 150 || Double.parseDouble(afternoon) >= 1450){
                        is[0] = false;
                        testContent.add(" 午餐要吃饱哦，但是也不能吃的太多");
                    }
                    if (evening == null || evening.equals("")){
                        is[0] = false;
                        testContent.add(" 晚餐十分重要,必须吃「好」。如果进食不当,过饱、过晚,都可能对人体健康造成一定的损害");
                    }else if (Double.parseDouble(evening) <= 50 || Double.parseDouble(evening) >= 750){
                        is[0] = false;
                        testContent.add(" 晚餐要吃七分饱，每餐食物不可过多或者过少，避免大胃口哦");
                    }
                    //2
                    if ((Double.parseDouble(morning) + Double.parseDouble(afternoon) + Double.parseDouble(evening)) <= (int) Double.parseDouble(HealthUtil.getKC())){
                        is[0] = false;
                        testContent.add(" 您今天的热量摄取未能达标哦，要多吃点哦");
                    }else if ((Double.parseDouble
                            (morning) + Double.parseDouble(afternoon) + Double.parseDouble(evening)) > 2500){
                        is[0] = false;
                        testContent.add(" 您今天的热量过多,要注意多运动");
                    }else{
                        if (is[0]){
                            testContent.add(" 您今天的热量摄取已经达标");
                        }else {
                            testContent.add(" 虽然总量达标了,还是要注意各餐分配合理哦");
                        }
                    }
                    TestContentHandle.sendEmptyMessage(0);

                }else{
                    Log.e(TAG, "done: "+e.getMessage() );
                }
            }
        });
    }
}
