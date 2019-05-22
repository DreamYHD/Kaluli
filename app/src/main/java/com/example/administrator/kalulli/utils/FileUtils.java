package com.example.administrator.kalulli.utils;

/**
 * Created by Administrator on 2019/5/8.
 */
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static butterknife.internal.Utils.arrayOf;

/**
 * Created by dreamY on 2017/8/19.
 */

public class FileUtils {
    private static final String TAG = "FileUtils";
    public static String getFilePathFromUri(Context context, Uri uri) {
        String data = null;
        Cursor mCursor = context.getContentResolver().query(uri, arrayOf(MediaStore.Images.ImageColumns.DATA), null, null, null);
        if (mCursor != null && mCursor.moveToFirst()) {
            int index = mCursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            if (index > -1) {
                data = mCursor.getString(index);
            }
        }
        if (mCursor != null) {
            mCursor.close();
        }
        return data;
    }
    public static String getFileName(String filePath){

        return filePath.substring(filePath.lastIndexOf("/")+1);
    }
    private static   String storagePath = "";
    private static final String DST_FOLDER_NAME = "PlayCamera";
    private static final File parentPath = Environment.getExternalStorageDirectory();

    /**初始化保存路径
     * @return
     */
    private static String initPath(){
        if(storagePath.equals("")){
            storagePath = parentPath.getAbsolutePath()+"/" + DST_FOLDER_NAME;
            File f = new File(storagePath);
            if(!f.exists()){
                f.mkdir();
            }
        }
        return storagePath;
    }

    /**保存Bitmap到sdcard
     * @param mJCamera
     * @param b
     */
    public static String saveBitmap(String mJCamera, Bitmap b){

        String path = initPath();
        long dataTake = System.currentTimeMillis();
        String jpegName = path + "/" + dataTake +".jpg";
        Log.i(TAG, "saveBitmap:jpegName = " + jpegName);
        try {
            FileOutputStream fout = new FileOutputStream(jpegName);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            Log.i(TAG, "saveBitmap成功");
        } catch (IOException e) {
            Log.i(TAG, "saveBitmap:失败");
            e.printStackTrace();
        }

        return jpegName;
    }


}