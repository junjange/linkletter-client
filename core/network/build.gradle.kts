plugins {
    id("linkletter.client.convention.lint")
    id("linkletter.client.convention.kmp")
    id("linkletter.client.convention.kmp.android")
    id("linkletter.client.convention.kmp.ios")
    id("linkletter.client.convention.kotlin.serialization")
}

android.namespace = "linkletter.client.core.network"

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.datetime)
            api(projects.core.common)
            api(projects.core.model)

            implementation(libs.bundles.ktor)
            implementation(libs.koin.core)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}
