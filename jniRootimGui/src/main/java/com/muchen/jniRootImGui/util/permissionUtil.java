package com.muchen.jniRootImGui.util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class permissionUtil
{

    private String[] RequestPermissions;

    private Activity context;
    private int code;
    public permissionUtil(Activity context, String[] RequestPermissions, int code)
    {
        this.context=context;
        this.RequestPermissions = RequestPermissions;
        this.code = code;
    }
    public permissionUtil(Activity context)
    {
        this.context = context;
        this.code = 0;
        RequestPermissions = new String[]{"0"};
    }
    public void RequestPermission()
    {
        for (String requestPermission : RequestPermissions)
        {
            if(ContextCompat.checkSelfPermission(context, requestPermission) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(context, RequestPermissions,code);
                break;
            }
        }
    }
    public void R_RequestAllFileRW()
    {
        if (!Environment.isExternalStorageManager())
        {
            requestAllFilesAccess();
        }
    }
    public void getPermission()
    {
        if (android.os.Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(context)) //安卓版本大于6.0并且无悬浮窗权限 by因为安卓6.0以下默认有悬浮窗权限
        {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
            context.startActivityForResult(intent, 0);
        }
    }//获取悬浮窗权限
///////////--------------------
    private void requestAllFilesAccess()
    {
        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivityForResult(intent, code);
    }
}
