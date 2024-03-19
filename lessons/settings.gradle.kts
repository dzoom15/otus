dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
rootProject.name = "lessons"

include("m1l1-first")
include("m2l3-kmp")
