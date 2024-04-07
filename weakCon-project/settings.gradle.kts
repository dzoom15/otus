rootProject.name = "weakCon-project"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

pluginManagement {
    includeBuild("../plugin-build")

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

include(":weakCon-api-v1")
include(":weakCon-common")
include(":weakCon-mapper")
