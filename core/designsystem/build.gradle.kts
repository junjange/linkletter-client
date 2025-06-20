plugins {
    id("linkletter.client.convention.lint")
    id("linkletter.client.convention.kmp")
    id("linkletter.client.convention.kmp.android")
    id("linkletter.client.convention.kmp.ios")
    id("linkletter.client.convention.kmp.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)

            implementation(libs.coil)
            implementation(libs.coil.network)
        }

        appleMain {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
        androidMain {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }
        jvmMain {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }
    }
}

android.namespace = "linkletter.client.core.designsystem"

compose.resources {
    publicResClass = true
}
