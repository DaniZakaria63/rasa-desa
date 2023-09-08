plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.shapeide.rasadesa.presenter"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {

    implementation(project(mapOf("path" to ":core")))
    implementation(project(mapOf("path" to ":domain")))

    // Kotlin Coroutines dependencies
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // Lifecycle ViewModel
    implementation(libs.lifecycle.ktx)
    implementation(libs.lifecycle.args)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit.core)
    testImplementation(libs.junit.ext)
    testImplementation(libs.test.core)
    testImplementation(libs.test.testing)
    testImplementation(libs.turbine)

    // Hilt Test
    androidTestImplementation(libs.hilt.test)
    kspAndroidTest(libs.hilt.compiler)

    // Timber
    implementation(libs.timber.core)
}