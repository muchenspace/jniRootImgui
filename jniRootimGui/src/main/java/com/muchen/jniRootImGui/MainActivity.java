package com.muchen.jniRootImGui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.muchen.jniRootImGui.AIDLService.AIDLService;
import com.muchen.jniRootImGui.imguiView.ImGuiView;
import com.muchen.jniRootImGui.util.permissionUtil;
import com.topjohnwu.superuser.Shell;
import com.topjohnwu.superuser.ipc.RootService;
import jniRootImGui.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
{
    static
    {
        Shell.setDefaultBuilder(Shell.Builder.create()
                .setFlags(Shell.FLAG_MOUNT_MASTER));
    }
    private ActivityMainBinding binding;
    public static ITestService ipc;
    private static boolean isInit = false;
    public static WindowManager windowManager;
    public static WindowManager.LayoutParams params;//imguiview的窗口信息


    class AIDLConnection implements ServiceConnection
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            Log.d("muchen", "绑定成功");
            ipc = ITestService.Stub.asInterface(service);
            isInit = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("muchen", "失败");
        }
    }
//回调


    public  int getLayoutType()
    {
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }
        else
        {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        return LAYOUT_FLAG;
    }//根据版本选择窗口类型


    protected void addFullView()//把imguiview添加进屏幕
    {
//        gl2JNIView = new GL2JNIView(this);
        ImGuiView imGuiView=new ImGuiView(this);
        params = new WindowManager.LayoutParams(
                getLayoutType(),
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_FULLSCREEN,
                PixelFormat.TRANSLUCENT);//窗口不获得焦点，不接受触摸事件，不拦截触摸事件，全屏显示
        params.gravity = Gravity.LEFT | Gravity.TOP;		// 调整悬浮窗显示的停靠位置为左侧置顶

        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        // 以屏幕左上角为原点，设置x、y初始值（10,10），相对于gravity
        params.x = 0;
        params.y = 0;
        // 设置悬浮窗口长宽数据
        params.width  = WindowManager.LayoutParams.MATCH_PARENT;//填充屏幕
        params.height = WindowManager.LayoutParams.MATCH_PARENT;//填充屏幕
        if (Build.VERSION.SDK_INT >= 28)
        {
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES ;
        }//处理刘海

        windowManager.addView(imGuiView, params);//把imGuiView添加进屏幕
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Shell.getShell(shell ->
        {
            if(shell.isRoot())
            {
                Log.d("muchen","成功获取root");
            }
        });

        if(!isInit)
        {
            permissionUtil permission = new permissionUtil(this);
            permission.getPermission();
            Intent intent = new Intent(this, AIDLService.class);
            RootService.bind(intent, new AIDLConnection());
        }

        binding.kernelVersion.setText("内核版本\n"+System.getProperty("os.version"));
        binding.startImGui.setOnClickListener(v -> {addFullView();});
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
    }
}