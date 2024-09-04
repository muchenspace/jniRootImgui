package com.muchen.jniRootImGui.imguiView;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.RemoteException;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.muchen.jniRootImGui.MainActivity;

public class ImGuiView extends SurfaceView implements SurfaceHolder.Callback//绘制imgui
{
   public ImGuiView(Context context)
   {
      super(context);
      setZOrderOnTop(true);//将surface置顶
      SurfaceHolder holder = getHolder();//创建一个与本surface关联的SurfaceHolder
      holder.setFormat(PixelFormat.TRANSLUCENT);//设置透明
      getHolder().addCallback(this); // 为 SurfaceHolder 添加回调接口
      setFocusable(true); // 设置 SurfaceView 可获得焦点
      setFocusableInTouchMode(true); // 设置 SurfaceView 在触摸模式下可获得焦点
      requestFocus(); // 请求焦点
      requestFocusFromTouch(); // 从触摸事件请求焦点
   }

   @Override
   public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder)//surface创建完成后会回调这个方法
   {
      Log.d("muchen","surfaceCreated");
         new Thread(new Runnable()
         {
            @Override
            public void run()
            {
               try
               {
                  MainActivity.ipc.init_ImGui(surfaceHolder.getSurface());
               } catch (RemoteException e)
               {
                  e.printStackTrace();
               }
            }
         }).start();
   }

   @Override
   public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2)//surface发生更改
   {
      Log.d("muchen","surfaceChanged");
   }

   @Override
   public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder)
   {

   }
}
