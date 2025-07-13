plugins {
    id("linkletter.client.convention.lint")
    id("linkletter.client.convention.kmp")
    id("linkletter.client.convention.kmp.android")
    id("linkletter.client.convention.kmp.ios")
}

android.namespace = "linkletter.client.core.datastore"

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.datastore.core)
            api(projects.core.model)

            implementation(libs.kotlinx.datetime)
            implementation(libs.koin.core)
        }
    }
}
