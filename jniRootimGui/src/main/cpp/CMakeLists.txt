# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html.
# For more examples on how to use CMake, see https://github.com/android/ndk-samples.

# Sets the minimum CMake version required for this project.
cmake_minimum_required(VERSION 3.22.1)

project("muchenkernel")

set(CMAKE_CXX_STANDARD 17)


include_directories(
        ./
        ./include
        ./include/imgui
        ./include/draw
        ./include/tools
)


file(GLOB_RECURSE src "./src/*.cpp")

add_library(${CMAKE_PROJECT_NAME} SHARED
        ${src}
        native-lib.cpp)

find_library(
        log-lib
        log)

target_link_libraries(${CMAKE_PROJECT_NAME}
        # List libraries link to the target library
        android
        EGL
        GLESv2
        log)