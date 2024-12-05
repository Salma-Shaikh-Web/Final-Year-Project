//val <LibrariesForLibs> LibrariesForLibs.kotlin: Any
    //get() {return "Hello, world!"}

val implementation: Unit = Unit

object libs {
    const val junit = "junit:junit:4.13.2"
    const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
    // Add other dependencies here
    // Define other library dependencies here
    const val appcompat = "androidx.appcompat:appcompat:1.3.0"
    const val material = "com.google.android.material:material:1.4.0"
    const val activity = "androidx.activity:activity-ktx:1.4.0"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.1"
    const val recyclerview = "androidx.recyclerview:recyclerview:1.2.1"
    const val core = "androidx.core:core-ktx:version"

    //const val googleServices = "com.google.gms:google-services:4.4.1"
    // Define other dependencies as needed
}

plugins {
    alias(libs.plugins.androidApplication) apply true
    id("com.google.gms.google-services")
    //id("kotlin-android-extensions")
}

android {
    namespace = "com.example.hajricard"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.hajricard"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)

    //implementation(libs.firebase.firestore)
    implementation ("com.google.gms:google-services:4.4.1")
    implementation("com.google.firebase:firebase-firestore:25.0.0")

    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))

    implementation("com.google.firebase:firebase-auth:21.0.1")
    implementation ("com.google.firebase:firebase-core:21.1.0")
    implementation("com.hbb20:ccp:2.5.2")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation("com.google.android.material:material:1.5.0")
    implementation ("com.firebaseui:firebase-ui-firestore:8.0.2")
    implementation ("androidx.core:core-ktx:1.10.1")
    implementation ("com.google.android.gms:play-services-tasks:18.0.2")
    implementation ("com.google.firebase:firebase-database:21.0.0")
    implementation ("org.tensorflow:tensorflow-lite:2.12.0")
    implementation ("org.tensorflow:tensorflow-lite-gpu:2.12.0")
    implementation ("org.tensorflow:tensorflow-lite-support:0.4.3")
    implementation ("androidx.credentials:credentials:<latest version>")
    implementation ("androidx.credentials:credentials-play-services-auth:<latest version>")
    implementation ("com.google.android.libraries.identity.googleid:googleid:<latest version>")

    implementation(project(":app"))
    //implementation(libs.ext.junit)
    //implementation(libs.ext.junit)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.espresso)



}