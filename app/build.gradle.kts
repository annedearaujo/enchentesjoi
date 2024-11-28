plugins {
    id("com.android.application") version "8.7.2"
    id("org.jetbrains.kotlin.android") version "1.9.0"
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.appalertaenchentes"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.appalertaenchentes"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
    }

    packaging {
        resources {
            excludes += setOf("META-INF/LICENSE-notice.md", "META-INF/LICENSE.md", "META-INF/NOTICE.md", "META-INF/NOTICE")
        }
    }
}

dependencies {
    // AndroidX
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    implementation("androidx.core:core:1.15.0")

    // Google Play Services
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // ViewModel and LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx")

    // Room / SQLite & Lifecycle
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    // Firebase
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))

    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-messaging-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-perf-ktx")
    implementation("com.google.firebase:firebase-inappmessaging-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")

    //WorkManager
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services")

    // Outros
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("androidx.activity:activity-ktx:1.8.1")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("com.deque.android:axe-android:0.2.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")
    implementation("androidx.cardview:cardview:1.0.0")

    // Testes
    testImplementation("junit:junit:4.13.2") {
        exclude("META-INF", "LICENSE.md")
    }
    testImplementation("org.mockito:mockito-core:5.7.0") {
        exclude("META-INF", "LICENSE.md")
    }
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0") {
        exclude("META-INF", "LICENSE.md")
    }
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.1") {
        exclude("META-INF", "LICENSE.md")
    }
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.1") {
        exclude("META-INF", "LICENSE.md")
    }
    testImplementation("org.assertj:assertj-core:3.24.2") {
        exclude("META-INF", "LICENSE.md")
    }
    testImplementation ("org.robolectric:robolectric:4.11.1") {
        exclude("META-INF", "LICENSE.md")
    }
    testImplementation("androidx.test:runner:1.5.2") {
        exclude("META-INF", "LICENSE.md")
    }
    testImplementation ("io.mockk:mockk:1.13.8") {
        exclude("META-INF", "LICENSE.md")
    }

    // Testes instrumentados
    androidTestImplementation("androidx.test.ext:junit:1.1.5") {
        exclude("META-INF", "LICENSE.md")
    }
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") {
        exclude("META-INF", "LICENSE.md")
    }
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.1") {
        exclude("META-INF", "LICENSE.md")
    }
    androidTestImplementation ("androidx.test:core:1.5.0") {
        exclude("META-INF", "LICENSE.md")
    }
    androidTestImplementation ("io.mockk:mockk-android:1.13.8") {
        exclude("META-INF", "LICENSE.md")
    }
}