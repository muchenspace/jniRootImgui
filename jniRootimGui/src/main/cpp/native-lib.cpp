#include <jni.h>
#include <android/log.h>
#include <android/native_window_jni.h>
#include "ImGuiView.h"

#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, "muchen", __VA_ARGS__))


extern "C"
JNIEXPORT void JNICALL
Java_com_muchen_jniRootImGui_AIDLService_AIDLService_native_1init_1ImGui(JNIEnv *env, jobject thiz,jobject surface)
{
    LOGI("native_init_ImGui");
    ANativeWindow* w = ANativeWindow_fromSurface(env, (jobject)surface);
    ImGuiView imGuiView{w};
    imGuiView.Init();
    ANativeWindow_release(w);

    while (true)
    {
        imGuiView.NewFrame();
        imGuiView.DrawMenu();
        imGuiView.Rendering();
    }
}