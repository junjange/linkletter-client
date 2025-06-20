package linkletter.client.buildlogic.convention

import linkletter.client.buildlogic.convention.extension.kotlin
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class KmpIosPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            kotlin {
                iosX64()
                iosArm64()
                iosSimulatorArm64()
            }
        }
    }
}
