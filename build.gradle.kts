buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Plugins.safeArgs)
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.androidApplication) version Plugins.androidApplicationVersion apply(false)
    id(Plugins.androidLibrary) version Plugins.androidLibraryVersion apply(false)
    id(Plugins.kotlinAndroid) version Plugins.kotlinAndroidVersion apply(false)
    id(Plugins.kotlinJvm) version Plugins.kotlinJvmVersion apply(false)
    id(Plugins.hiltPlugin) version Hilt.hiltVersion apply(false)
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}