plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "com.emma_ea.dogify.android"
        minSdk = 24
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
}

dependencies {
    val lifecycleVersion = "2.3.1"
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    // koin
    implementation("io.insert-koin:koin-android:3.2.0")
    // android lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel- ktx:$lifecycleVersion")
    // android kotlin extensions
    implementation("androidx.core:core-ktx:1.6.0")
    // compose activity
    val composeVersion = "1.2.1"
    implementation("androidx.activity:activity-compose:1.5.1")
    implementation("androidx.compose.ui:ui:$composeVersion")
    // tooling support (previews, etc)
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation ("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    // foundation (border, background, box, image, etc)
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    // material design
    implementation("androidx.compose.material:material:$composeVersion")
    // material icons
    implementation("androidx.compose.material:material-icons-core:$composeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    // mdc theme
    implementation("com.google.android.material:compose-theme-adapter:1.1.16")
    // accompanist lib
    val accompanistVersion = "0.13.0"
    implementation("com.google.accompanist:accompanist-coil:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-swiperefresh:$accompanistVersion")
}