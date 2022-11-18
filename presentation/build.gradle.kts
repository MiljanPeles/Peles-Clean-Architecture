plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "rs.peles.cleanarchitecture"
    compileSdk = Android.compileSdk

    /*signingConfigs {
        create("release") {
            storeFile = file("E:/Miljan Peles/Projekti/Projekat/Keystore/mykey.jks")
            storePassword = "mypw!!"
            keyAlias = "key0"
            keyPassword = "mypw!!"
        }
    }*/

    defaultConfig {
        applicationId = "rs.peles.cleanarchitecture"
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.versionCode
        versionName = Android.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            //signingConfig = signingConfigs.getByName("release")
        }
        getByName("debug") {
            versionNameSuffix = "-DEBUG"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
    hilt {
        enableAggregatingTask = false
    }
}

dependencies {
    implementation(project(Modules.domain))

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.constraintLayout)
    implementation(AndroidX.lifecycleVmKtx)
    implementation(AndroidX.navigationFragment)
    implementation(AndroidX.navigationUi)
    implementation(Google.material)

    // Dagger Hilt
    implementation(Hilt.android)
    kapt(Hilt.androidCompiler)
    kapt(Hilt.compiler)

    // Test
    testImplementation(Test.junit)
    testImplementation(Test.truth)
    androidTestImplementation(Test.androidJunit)
    androidTestImplementation(Test.androidEspresso)
}