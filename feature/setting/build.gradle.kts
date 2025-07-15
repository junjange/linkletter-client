plugins {
    id("linkletter.client.convention.lint")
    id("linkletter.client.convention.kmp")
    id("linkletter.client.convention.kmp.android")
    id("linkletter.client.convention.kmp.ios")
    id("linkletter.client.convention.kmp.compose")
    id("linkletter.client.convention.kotlin.serialization")
}

android.namespace = "linkletter.client.feature.setting"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.ui)
            implementation(projects.core.common)
            implementation(projects.core.model)
            implementation(projects.core.data)
            implementation(projects.core.navigation)

            implementation(libs.navigation.compose)

            implementation(libs.coroutines.core)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel.navigation)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(libs.moko.permissions)
            implementation(libs.moko.permissions.compose)
        }
    }
}
