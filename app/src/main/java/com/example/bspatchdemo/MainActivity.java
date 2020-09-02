package com.example.bspatchdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
private TextView textView;
private static String path =Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "DCIM" + File.separator ;
    static {
    System.loadLibrary("native");
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        textView.setText(stringFromJNI());
        String old=path+"app-dftcl-debug_0920080701_Signed_Aligned.apk";
        String xin=path+"new.apk";
        String patch=path+"com.tcl.fota.system.patch";
        String newpatch=path+"bb.patch";
        /**
         * 调试的时候需要将old旧文件和old patch push到DCIM目录
         * 然后bspatch会生成new新文件,保存在自定义路径xin
         * bsdiff会重新生成个new patch，保存在自定义路径newpatch,该new patch与old patch一致
         */
        /*String old=path+"old.txt";
        String xin=path+"new.txt";
        String patch=path+"old.patch";
        String newpatch=path+"new.patch";*/
      /*  String old=path+"GalleryN_T_v4.0.0.09.0_dev_release_signed_platformkey_v3_alldpi.apk";
        String xin=path+"new.apk";
        String patch=path+"Gallery.patch";
        String newpatch=path+"new.patch";*/

        Log.d("hu","begin------------------");
        patch(old,xin,patch); // old + oldpatch -> xin
        diff(old,xin,newpatch);//old + xin -> newpatch
    }
    public native String stringFromJNI();
    public native void patch(String old,String xin,String patch);
    public native int diff(String old,String xin,String patch);
    public void checkPermission() {
        boolean isGranted = true;
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //如果没有写sd卡权限
                isGranted = false;
            }
            if (this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
            }
            //Log.i("hu","isGranted == "+isGranted);
            if (!isGranted) {
                this.requestPermissions(
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1234);
            }
        }
    }
}