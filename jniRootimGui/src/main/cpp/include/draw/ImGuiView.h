#include <android/native_window.h>
#include <EGL/egl.h>
#include "tools.h"

class ImGuiView
{
public:
    ImGuiView(ANativeWindow *g_EglNativeWindowType);//初始化imgui
    void NewFrame();
    void Rendering();
    void DrawMenu();
    void Init();
private:
    std::unique_ptr<touch> touchTest;
private:
     ANativeWindow *g_EglNativeWindowType{};
     bool g_Initialized{};
     EGLDisplay g_EglDisplay = EGL_NO_DISPLAY;
     EGLSurface g_EglSurface = EGL_NO_SURFACE;
     EGLContext g_EglContext = EGL_NO_CONTEXT;

};
