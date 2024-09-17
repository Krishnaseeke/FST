plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.testapplication.www"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
    }


    defaultConfig {
        applicationId = "com.testapplication.www"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("com.google.android.material:material:1.12.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("androidx.camera:camera-core:1.3.4")
    implementation("androidx.camera:camera-lifecycle:1.3.4")
    implementation("androidx.compose.foundation:foundation-android:1.6.8")
    implementation("androidx.compose.foundation:foundation-desktop:1.6.8")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.compose.material3:material3:1.0.0-alpha07")
    implementation ("androidx.navigation:navigation-compose:2.4.0-beta02")
    implementation ("androidx.compose.material3:material3:1.0.0-alpha11")
    implementation ("androidx.compose.material3:material3:1.0.0-alpha11")
    implementation ("com.google.accompanist:accompanist-coil:0.15.0")
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.20.0")
    implementation ("androidx.datastore:datastore-preferences:1.0.0")
    implementation ("androidx.compose.material3:material3:1.2.0-alpha02")
    implementation ("androidx.compose.ui:ui:1.0.5")
    implementation ("androidx.compose.material:material:1.0.5")
    implementation ("androidx.compose.ui:ui-tooling:1.0.5")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0")
    implementation ("androidx.activity:activity-compose:1.4.0")

    implementation ("com.google.android.gms:play-services-location:17.0.0")
    testImplementation ("junit:junit:4.12")
    androidTestImplementation ("androidx.test.ext:junit:1.1.1")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.2.0")
    implementation ("com.google.accompanist:accompanist-permissions:0.25.0")

    implementation ("androidx.camera:camera-core:1.1.0-alpha08")
    implementation ("androidx.camera:camera-camera2:1.1.0-alpha08")
    implementation ("androidx.camera:camera-lifecycle:1.1.0-alpha08")
    implementation ("androidx.camera:camera-view:1.1.0-alpha08")

    implementation ("androidx.compose.ui:ui:1.6.0")
    implementation ("androidx.compose.material:material:1.6.0")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.6.0")
    implementation ("androidx.compose.runtime:runtime-livedata:1.6.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    // Optional: For supporting additional material design components
    implementation ("androidx.compose.material3:material3:1.2.0")

    // Optional: For working with animations
    implementation ("androidx.compose.animation:animation:1.6.0")

    // Optional: For navigation support
    implementation ("androidx.navigation:navigation-compose:2.7.0")
    implementation("androidx.compose.foundation:foundation-android:1.6.8") {
        exclude(group = "androidx.compose.foundation", module = "foundation-desktop")
    }








}