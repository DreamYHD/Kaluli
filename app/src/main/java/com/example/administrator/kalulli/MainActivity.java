package com.example.administrator.kalulli;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.administrator.kalulli.base.BaseActivity;
import com.example.administrator.kalulli.ui.CameraFragment;
import com.example.administrator.kalulli.ui.MeFragment;
import com.example.administrator.kalulli.ui.SuggestFragment;
import com.example.administrator.kalulli.utils.ActivityUtils;
import com.example.administrator.kalulli.utils.BottomNavigationViewHelper;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.content_main)
    FrameLayout contentMain;
    @BindView(R.id.bottom_menu)
    BottomNavigationView bottomMenu;

    @Override
    protected void logicActivity(Bundle mSavedInstanceState) {
        if (mSavedInstanceState==null){
            ActivityUtils.replaceFragmentToActivity(mFragmentManager, CameraFragment.getInstance(),R.id.content_main);
        }
        BottomNavigationViewHelper.disableShiftMode(bottomMenu);
        bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.find_item:
                        ActivityUtils.replaceFragmentToActivity(mFragmentManager,CameraFragment.getInstance(),R.id.content_main);
                        break;
                    case R.id.near_item:
                        ActivityUtils.replaceFragmentToActivity(mFragmentManager, SuggestFragment.getInstance(),R.id.content_main);
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
