plugins { id("com.android.application") }

android {
    namespace = "com.majid.ibnawf"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.majid.ibnawf"
        minSdk = 28
        targetSdk = 35
        versionCode = 2
        versionName = "1.1"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation("org.mozilla.geckoview:geckoview:131.+")
}
