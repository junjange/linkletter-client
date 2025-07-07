import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id("linkletter.client.convention.lint")
    id("linkletter.client.convention.kmp")
    id("linkletter.client.convention.kmp.compose")
    id("linkletter.client.convention.kmp.ios")
    id("linkletter.client.convention.android.application")
}

android.namespace = "linkletter.client"

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    targets.filterIsInstance<KotlinNativeTarget>().forEach {
        it.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            binaryOption("bundleId", "linkletter.client")
            binaryOption("bundleVersion", version.toString())
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.model)

            implementation(projects.core.data)

            implementation(projects.core.designsystem)

            implementation(projects.core.network)

            implementation(projects.feature.main)
            implementation(projects.feature.followingfeed)
            implementation(projects.feature.mybloggers)
            implementation(projects.feature.addblog)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel.navigation)
        }
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.lifecycle.compose)
        }
    }
}
