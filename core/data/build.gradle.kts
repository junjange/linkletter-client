plugins {
    id("linkletter.client.convention.lint")
    id("linkletter.client.convention.kmp")
    id("linkletter.client.convention.kmp.android")
    id("linkletter.client.convention.kmp.ios")
}

android.namespace = "linkletter.client.core.data"

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.common)
            api(projects.core.database)
            api(projects.core.datastore)
            api(projects.core.network)
            api(projects.core.domain)

            implementation(libs.coroutines.core)
            implementation(libs.ksoup)
            implementation(libs.koin.core)
        }
    }
}
