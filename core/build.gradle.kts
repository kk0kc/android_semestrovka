import java.util.Properties
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.detekt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.junit)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint)
}

val keystoreProperties = Properties()
keystoreProperties.load(project.rootProject.file("keys.properties").inputStream())

android {
    compileSdk = 34
    namespace = "itis.lisa.semestrovka.core"

    with (defaultConfig) {
        minSdk = 24
        targetSdk = 34
    }

    defaultConfig {
//        buildConfigField("String", "SPACEX_API_URL", "\"https://api.spacexdata.com/v4/\"")
        buildConfigField("String", "SPACEX_API_URL", keystoreProperties.getProperty("baseUrl"))
        buildConfigField("String", "API_KEY", keystoreProperties.getProperty("apiKey"))
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            consumerProguardFiles("proguard-rules.pro")
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    kotlin {
        jvmToolchain(17)
    }

    kotlinOptions {
        freeCompilerArgs = listOf(
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=kotlinx.coroutines.FlowPreview",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
        )
    }
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material3)
    implementation(libs.hilt)
    implementation(libs.kotlin.coroutines)
    implementation(libs.kotlin.serialization)
    implementation(libs.kotlin.serialization.converter)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.navigation)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.timber)
    testImplementation("org.testng:testng:6.9.6")
    androidTestImplementation(libs.bundles.common.android.test)

    ksp(libs.hilt.compiler)
    kspAndroidTest(libs.test.android.hilt.compiler)

    detektPlugins(libs.detekt.compose.rules)
}
