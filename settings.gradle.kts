rootProject.name = "linkletter-client"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":composeApp")

include(":core:model")
include(":core:designsystem")
include(":core:ui")
include(":core:common")
include(":core:data")
include(":core:domain")
include(":core:network")
include(":core:datastore")
include(":core:database")
include(":core:navigation")

include(":feature:main")
include(":feature:home")
include(":feature:bookmark")
include(":feature:blogadd")
