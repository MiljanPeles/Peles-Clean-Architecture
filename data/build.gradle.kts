plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "rs.peles.data"
    compileSdk = Android.compileSdk

    defaultConfig {
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        //consumerProguardFiles = "consumer-rules.pro"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            buildConfigField(
                    "String",
                    "BASE_URL",
                    "\"https://url/\""
            )
            buildConfigField(
                    "String",
                    "API_KEY",
                    "\"adb69d232d124c98fe53160d9a4757d71380ba1d4200697e6817c99a30959ed2\""
            )
        }
        getByName("debug") {
            buildConfigField(
                    "String",
                    "BASE_URL",
                    "\"https://url/\""
            )
            buildConfigField(
                    "String",
                    "API_KEY",
                    "\"adb69d232d124c98fe53160d9a4757d71380ba1d4200697e6817c99a30959ed2\""
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
    hilt {
        enableAggregatingTask = false
    }
}

dependencies {
    implementation(project(Modules.domain))

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(Google.material)

    // Retrofit
    implementation(Retrofit.retrofit)
    implementation(Retrofit.gsonConverter)
    implementation(Retrofit.okhttp3)
    implementation(Retrofit.loggingInterceptor)

    // Gson
    implementation(Google.gson)

    //Dagger Hilt
    implementation(Hilt.android)
    kapt(Hilt.androidCompiler)
    kapt(Hilt.compiler)

    // Chucker
    debugImplementation(Chucker.chuckerNetworkInspectionDebug)
    releaseImplementation(Chucker.chuckerNetworkInspectionRelease)

    // Test
    testImplementation(Test.junit)
    testImplementation(Test.truth)
    androidTestImplementation(Test.androidJunit)
    androidTestImplementation(Test.androidEspresso)
}