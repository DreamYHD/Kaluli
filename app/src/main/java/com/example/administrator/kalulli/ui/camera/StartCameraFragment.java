package com.example.administrator.kalulli.ui.camera;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartCameraFragment extends Fragment {


    @BindView(R.id.camera)
    ImageView camera;
    Unbinder unbinder;

    public StartCameraFragment() {
        // Required empty public constructor
    }


    public static StartCameraFragment getInstance() {
        return new StartCameraFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_start_camera, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.camera)
    public void onViewClicked() {
        ActivityUtils.replaceFragmentToActivity(getFragmentManager(), CameraFragment.getInstance(),R.id.content_main);

    }
}
