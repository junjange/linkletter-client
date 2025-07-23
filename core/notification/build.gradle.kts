plugins {
    id("linkletter.client.convention.lint")
    id("linkletter.client.convention.kmp")
    id("linkletter.client.convention.kmp.android")
    id("linkletter.client.convention.kmp.ios")
    id("linkletter.client.convention.kotlin.serialization")
}

android.namespace = "linkletter.client.core.notification"

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.datetime)
            api(projects.core.common)
            api(projects.core.model)
            api(projects.core.domain)

            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.coroutines.core)
            implementation(libs.koin.core)
        }
        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
        }
    }
}
