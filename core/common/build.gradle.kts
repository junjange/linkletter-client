plugins {
    id("linkletter.client.convention.lint")
    id("linkletter.client.convention.kmp")
    id("linkletter.client.convention.kmp.android")
    id("linkletter.client.convention.kmp.ios")
}

android.namespace = "linkletter.client.core.common"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
        }
    }
}
