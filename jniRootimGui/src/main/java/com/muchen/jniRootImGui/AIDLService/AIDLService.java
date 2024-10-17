

package com.muchen.jniRootImGui.AIDLService;


import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;
import android.view.Surface;

import androidx.annotation.NonNull;

import com.muchen.jniRootImGui.ITestService;
import com.topjohnwu.superuser.ipc.RootService;

// Demonstrate RootService using AIDL (daemon mode)
public class AIDLService extends RootService
{

    static
    {
        if (Process.myUid() == 0)
        {
            System.loadLibrary("muchenkernel");
            Log.d("muchen", "加载so");
        }
    }

    public  native void native_init_ImGui(Surface surface);

    class TestIPC extends ITestService.Stub
    {
        @Override
        public void init_ImGui(Surface surface)
        {
            native_init_ImGui(surface);
        }
    }


    @Override
    public IBinder onBind(@NonNull Intent intent)
    {
        Log.d("muchen", "AIDLService: onBind");
        return new TestIPC();
    }
}
