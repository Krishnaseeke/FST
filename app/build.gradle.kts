plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.baselineprofile")
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
    implementation("androidx.profileinstaller:profileinstaller:1.3.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    "baselineProfile"(project(":baselineprofile"))
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.compose.material3:material3:1.0.0-alpha07")
    implementation ("androidx.navigation:navigation-compose:2.4.0-beta02")
    implementation ("androidx.compose.material3:material3:1.0.0-alpha11")
    implementation ("androidx.compose.material3:material3:1.0.0-alpha11")
    implementation ("com.google.accompanist:accompanist-coil:0.15.0")
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.20.0")
    androidTestImplementation( "androidx.compose.ui:ui-test-junit4:1.6.7")
    androidTestImplementation( "Gradle:androidx.compose.ui:ui-test-junit4-android:1.6.7@arr")
    debugImplementation( "androidx.compose.ui:ui-test-manifest:1.6.7")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.7")
    implementation ("androidx.compose.ui:ui:1.6.7") // Compose UI
    implementation ("androidx.compose.ui:ui-tooling:1.6.7") // Compose UI Tooling
    implementation ("androidx.benchmark:benchmark-macro-junit4:1.3.0") // Benchmarking
    implementation ("androidx.test.ext:junit:1.1.6") // JUnit for AndroidX
    implementation ("androidx.test.espresso:espresso-core:3.5.2") // Espresso









}