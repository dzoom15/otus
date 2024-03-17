rootProject.name = "coMatch-project"

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

include(":coMatch-tmp")
