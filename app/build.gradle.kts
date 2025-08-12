
plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.wect.plants_frontend_android"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.wect.plants_frontend_android"
        minSdk = 30
        targetSdk = 36
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

//    sourceSets {
//        named("main") {
//            res.srcDirs(
//                "src/main/res",
//                "src/main/res/layout/community",
//                "src/main/res/layout/garden"
//            )
//        }
//    }



    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //使用RxJava实现防抖
    implementation("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation("io.reactivex.rxjava3:rxjava:3.1.5")
    implementation("com.jakewharton.rxbinding4:rxbinding:4.0.0")

    //导航栏依赖 Material 组件
    implementation("com.google.android.material:material:1.12.0")

    //RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    //RecyclerViewSwipeDecorator
    implementation("it.xabaras.android:recyclerview-swipedecorator:1.4")
}