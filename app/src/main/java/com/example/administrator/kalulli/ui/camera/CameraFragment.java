package com.example.administrator.kalulli.ui.camera;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.ClickListener;
import com.cjt2325.cameralibrary.listener.JCameraListener;
import com.example.administrator.kalulli.R;
import com.example.administrator.kalulli.utils.SampleUtil;
import com.example.administrator.kalulli.utils.SaveBitmap;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFragment extends Fragment {


    private static final String TAG = "CameraFragment";
    @BindView(R.id.camera)
    JCameraView camera;
    Unbinder unbinder;
    private static JSONObject jsonObject;
    private static String str;
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent(getActivity(),CameraResultActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("json",jsonObject.toString());
            bundle.putString("str",str);
            intent.putExtras(bundle);
            Log.i(TAG, "handleMessage: "+jsonObject.toString());
            //Toast.makeText(getContext(), "识别成功", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    };

    public CameraFragment() {
        // Required empty public constructor
    }

    public static CameraFragment getInstance() {
        return new CameraFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        unbinder = ButterKnife.bind(this, view);
        camera.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath());
        camera.setFeatures(JCameraView.BUTTON_STATE_BOTH);
        camera.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(final Bitmap bitmap) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 传入可选参数调用接口
                        Log.i(TAG, "run: size = "+bitmap.getByteCount());
                        HashMap<String, String> options = new HashMap<String, String>();
                        options.put("top_num", "1");
                        options.put("filter_threshold", "0.7");
                        options.put("baike_num", "1");
                        str = SaveBitmap.saveImageToGallery(getActivity(),bitmap);
                        JSONObject res = SampleUtil.client.dishDetect(str, options);
                        Log.i(TAG, "run: "+res.toString());
                        //Log.i(TAG, "run: "+res.toString());
                        jsonObject = res;
                        Message message = Message.obtain();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                }).start();

                //Log.i(TAG, "captureSuccess: "+jsonObject.toString());

/*                if (jsonObject != null){
                    Intent intent = new Intent();
                    intent.putExtra("json",jsonObject.toString());
                    Toast.makeText(getContext(), "识别成功", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }*/

            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {

            }
        });
        //左边按钮点击事件
        camera.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();
            }
        });
        camera.setRightClickListener(new ClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();
            }
        });

        return view;



    }

    @Override
    public void onResume() {
        super.onResume();
        camera.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        camera.onPause();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}