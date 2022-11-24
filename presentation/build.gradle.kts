plugins {
    id(Plugins.androidLibraryId)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
    id(Plugins.daggerHilt)
    id(Plugins.navigationSafeArgsKotlin)
}

android {
    namespace = "rs.peles.presentation"
    compileSdk = Android.compileSdk

    defaultConfig {
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        //consumerProguardFiles("consumer-rules.pro")
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
    implementation((AndroidX.lifecycleVmKtx))
    implementation(AndroidX.navigationFragment)
    implementation(AndroidX.navigationUi)
    implementation(Google.material)

    // Dagger Hilt
    implementation(Hilt.android)
    kapt(Hilt.androidCompiler)
    kapt(Hilt.compiler)
    
    // Retrofit
    implementation(Retrofit.retrofit)

    // Chucker
    debugImplementation(Chucker.chuckerNetworkInspectionDebug)
    releaseImplementation(Chucker.chuckerNetworkInspectionRelease)

    // Test
    testImplementation(Test.junit)
    testImplementation(Test.truth)
    androidTestImplementation(Test.androidJunit)
    androidTestImplementation(Test.androidEspresso)
}