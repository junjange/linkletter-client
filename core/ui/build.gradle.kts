plugins {
    id("linkletter.client.convention.lint")
    id("linkletter.client.convention.kmp")
    id("linkletter.client.convention.kmp.android")
    id("linkletter.client.convention.kmp.ios")
    id("linkletter.client.convention.kmp.compose")
}

android.namespace = "linkletter.client.core.ui"

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.designsystem)
            api(projects.core.model)

            implementation(libs.coil)
            implementation(libs.coil.network)
        }
    }
}
