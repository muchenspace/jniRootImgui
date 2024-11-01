plugins {
    id("com.android.application")
}

android {
    namespace = "jniRootImGui"
    compileSdk = 34
    buildFeatures.aidl = true;
    defaultConfig {
        applicationId = "com.muchen.jniRootImGui"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    val libsuVersion = "6.0.0"
    implementation("com.github.topjohnwu.libsu:core:$libsuVersion")
    implementation("com.github.topjohnwu.libsu:service:$libsuVersion")
    implementation("com.github.topjohnwu.libsu:nio:$libsuVersion")
}