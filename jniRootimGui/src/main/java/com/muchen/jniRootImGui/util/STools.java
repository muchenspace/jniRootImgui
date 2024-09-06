package com.muchen.jniRootImGui.util;

import java.lang.reflect.Method;

public class STools
{
    public static boolean isDebug()
    {
        try
        {
            Class<?> systemProperties = Class.forName("android.os.SystemProperties");
            Method get = systemProperties.getMethod("get", String.class, String.class);
            return "1".equals(get.invoke(systemProperties, "ro.debuggable", "0"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
