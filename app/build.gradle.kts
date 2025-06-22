plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
    kotlin("plugin.serialization") version "2.0.21"
    id("com.google.dagger.hilt.android") version "2.48"
    id("com.google.devtools.ksp") version "2.0.21-1.0.27"
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
}

android {
    namespace = "com.dobrihlopez.financeassistant"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.dobrihlopez.financeassistant"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "API_TOKEN", "\"${project.findProperty("API_TOKEN") as String}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    hilt {
        enableAggregatingTask = false
    }
}

ktlint {
    disabledRules.set(setOf("fun-naming"))
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.airbnb.lottie.compose)
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.decompose.core)
    implementation(libs.decompose.compose)

    implementation(libs.essenty.state.keeper)
    implementation(libs.essenty.instance.keeper)

    implementation(libs.mvikotlin.core)
    implementation(libs.mvikotlin.main)
    implementation(libs.mvikotlin.coroutines)
    implementation(libs.mvikotlin.logging)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
}
