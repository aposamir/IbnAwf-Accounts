plugins { id("com.android.application") }

android {
    namespace = "com.majid.ibnawf"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.majid.ibnawf"
        minSdk = 28
        targetSdk = 35
        versionCode = 3
        versionName = "1.2"

        // Xiaomi Mi Max 3 / Snapdragon 636 is arm64-v8a.
        // Package only that native ABI so GeckoView does not bundle
        // x86/x86_64/armeabi-v7a copies into the APK.
        ndk {
            abiFilters += listOf("arm64-v8a")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation("org.mozilla.geckoview:geckoview:131.+")
}
