plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.kapt)
    alias(libs.plugins.apollo)
}

apollo {
    service("github") {
        packageName.set("home.assignment.network")
        generateAsInternal.set(false)
        schemaFile.set(file("src/main/graphql/schema.json"))
        introspection {
            endpointUrl.set("https://api.github.com/graphql")
            headers.put("Authorization", "Bearer ${project.findProperty("apiKey")}")
        }
    }
}

android {
    namespace = "home.assignemnt.network"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "API_KEY", "\"${project.findProperty("apiKey") ?: ""}\"")

    }
    buildTypes {
        getByName("debug") {
            buildConfigField("String", "BASE_URL", "\"https://api.github.com/graphql\"")
        }
        getByName("release") {
            buildConfigField("String", "BASE_URL", "\"https://api.github.com/graphql\"")
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
}

dependencies {
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.noop)
    implementation(libs.okHttp2Curl)
    implementation(libs.timber)
    implementation(libs.apollo.runtime)
    implementation(libs.apollo.adapters)
    implementation(libs.apollo.httpCache)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
kapt {
    correctErrorTypes = true
}