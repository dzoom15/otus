plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
}

group = "com.otus.otuskotlin.marketplace"
version = "0.0.1"


ext {
    val specDir = layout.projectDirectory.dir("../specs")
    set("spec-v1", specDir.file("weakCon-spec-v1.yaml").toString())
}

allprojects {
    repositories {
        mavenCentral()
    }
}