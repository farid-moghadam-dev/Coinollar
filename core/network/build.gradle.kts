import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
}

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) load(file.inputStream())
}
val apiKey: String = localProperties.getProperty("API_KEY") ?: ""

android {
    namespace = "com.faridev.coinollar.core.network"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        buildConfigField("String", "API_KEY", "\"$apiKey\"")
        buildConfigField("String", "BASE_URL", "\"https://BrsApi.ir/Api/Market/Gold_Currency.php\"")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // Ktor
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

    // Serialization
    implementation(libs.kotlin.serialization.json)

    // Koin
    implementation(libs.koin.android)

    // Timber
    implementation(libs.timber)

    testImplementation(libs.junit)
}
