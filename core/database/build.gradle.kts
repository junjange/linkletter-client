plugins {
    id("linkletter.client.convention.lint")
    id("linkletter.client.convention.kmp")
    id("linkletter.client.convention.kmp.android")
    id("linkletter.client.convention.kmp.ios")
    id("linkletter.client.convention.room")
}

android.namespace = "linkletter.client.core.database"

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.model)

            implementation(libs.kotlinx.datetime)
        }
    }
}
