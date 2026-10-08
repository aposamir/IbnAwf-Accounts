plugins { id("com.android.application") }

android {
    namespace = "com.majid.testhosts"
    compileSdk = 35

    defaultConfig {
        minSdk = 28
        targetSdk = 34
        versionCode = 2
        versionName = "1.1"
    }

    flavorDimensions += "system"
    productFlavors {
        create("nova") {
            dimension = "system"
            applicationId = "com.majid.nova.testhost"
            resValue("string", "app_name", "NOVA • مركز الاختبار")
            buildConfigField("String", "HOST_URL", "\"https://nova-neon-field-demo.onrender.com/\"")
        }
        create("firas") {
            dimension = "system"
            applicationId = "com.majid.firas.testhost"
            resValue("string", "app_name", "فراس كلكل • مركز الاختبار")
            buildConfigField("String", "HOST_URL", "\"https://fras-ordr.onrender.com/\"")
        }
    }
    buildFeatures { buildConfig = true }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
