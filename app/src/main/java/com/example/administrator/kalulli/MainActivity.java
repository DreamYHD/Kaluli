package com.example.administrator.kalulli;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.administrator.kalulli.base.BaseActivity;
import com.example.administrator.kalulli.ui.daily.DailyFragment;
import com.example.administrator.kalulli.ui.me.MeFragment;
import com.example.administrator.kalulli.ui.camera.StartCameraFragment;
import com.example.administrator.kalulli.ui.suggest.SuggestFragment;
import com.example.administrator.kalulli.utils.ActivityUtils;
import com.example.administrator.kalulli.utils.BottomNavigationViewHelper;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.content_main)
    FrameLayout contentMain;
    @BindView(R.id.bottom_menu)
    BottomNavigationView bottomMenu;

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {
        if (mSavedInstanceState==null){
            ActivityUtils.replaceFragmentToActivity(mFragmentManager, StartCameraFragment.getInstance(),R.id.content_main);
        }
        if (mAVUserFinal == null){
            toast("请先登录",0);
        }
        int checkpermission = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (checkpermission != PackageManager.PERMISSION_GRANTED) {//没有给权限
            Log.e("permission", "动态申请");
            //参数分别是当前活动，权限字符串数组，requestcode
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        BottomNavigationViewHelper.disableShiftMode(bottomMenu);
        bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.find_item:
                        ActivityUtils.replaceFragmentToActivity(mFragmentManager,StartCameraFragment.getInstance(),R.id.content_main);
                        break;
                    case R.id.near_item:
                        ActivityUtils.replaceFragmentToActivity(mFragmentManager, SuggestFragment.getInstance(),R.id.content_main);
                        break;
                    case R.id.daily_item:
                        ActivityUtils.replaceFragmentToActivity(mFragmentManager, DailyFragment.getInstance(),R.id.content_main);
                        break;
                    case R.id.me_item:
                        ActivityUtils.replaceFragmentToActivity(mFragmentManager, MeFragment.getInstance(),R.id.content_main);
                        break;
                }
                return true;
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

}
