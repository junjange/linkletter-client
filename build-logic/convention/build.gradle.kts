import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "linkletter.client.buildlogic.convention"

repositories {
    google {
        content {
            includeGroupByRegex("com\\.android.*")
            includeGroupByRegex("com\\.google.*")
            includeGroupByRegex("androidx.*")
        }
    }
    mavenCentral()
    gradlePluginPortal()
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "17"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.bundles.plugins)
}

gradlePlugin {
    plugins {
        register("lint") {
            id = "linkletter.client.convention.lint"
            implementationClass = "linkletter.client.buildlogic.convention.LintPlugin"
        }
        register("kmp") {
            id = "linkletter.client.convention.kmp"
            implementationClass = "linkletter.client.buildlogic.convention.KmpPlugin"
        }
        register("kmpAndroid") {
            id = "linkletter.client.convention.kmp.android"
            implementationClass = "linkletter.client.buildlogic.convention.KmpAndroidPlugin"
        }
        register("kmpIos") {
            id = "linkletter.client.convention.kmp.ios"
            implementationClass = "linkletter.client.buildlogic.convention.KmpIosPlugin"
        }
        register("kmpCompose") {
            id = "linkletter.client.convention.kmp.compose"
            implementationClass = "linkletter.client.buildlogic.convention.KmpComposePlugin"
        }
        register("kotlinSerialization") {
            id = "linkletter.client.convention.kotlin.serialization"
            implementationClass =
                "linkletter.client.buildlogic.convention.KotlinSerializationPlugin"
        }
        register("kotlinAndroid") {
            id = "linkletter.client.convention.kotlin.android"
            implementationClass = "linkletter.client.buildlogic.convention.KotlinAndroidPlugin"
        }
        register("androidApplication") {
            id = "linkletter.client.convention.android.application"
            implementationClass =
                "linkletter.client.buildlogic.convention.AndroidApplicationPlugin"
        }
        register("androidLibrary") {
            id = "linkletter.client.convention.android.library"
            implementationClass = "linkletter.client.buildlogic.convention.AndroidLibraryPlugin"
        }
        register("googleServices") {
            id = "linkletter.client.convention.google.services"
            implementationClass = "linkletter.client.buildlogic.convention.GoogleServicesPlugin"
        }
        register("ktorfit") {
            id = "linkletter.client.convention.ktorfit"
            implementationClass = "linkletter.client.buildlogic.convention.KtorfitPlugin"
        }
        register("room") {
            id = "linkletter.client.convention.room"
            implementationClass = "linkletter.client.buildlogic.convention.RoomPlugin"
        }
        register("spotless") {
            id = "linkletter.client.convention.spotless"
            implementationClass = "linkletter.client.buildlogic.convention.SpotlessPlugin"
        }
        register("detekt") {
            id = "linkletter.client.convention.detekt"
            implementationClass = "linkletter.client.buildlogic.convention.DetektPlugin"
        }
    }
}
