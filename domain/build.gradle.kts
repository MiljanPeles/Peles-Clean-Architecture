plugins {
    id(Plugins.javaLibrary)
    id(Plugins.kotlinJvm)
    id(Plugins.kotlinKapt)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {

    // Coroutines
    implementation(Coroutines.coroutinesCore)
    implementation(Coroutines.javaInject)

}