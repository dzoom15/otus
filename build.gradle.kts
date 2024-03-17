plugins {
    alias(libs.plugins.kotlin.jvm) apply false
}

group = "com.otus.otuskotlin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
subprojects {
    repositories {
        mavenCentral()
    }
    group = rootProject.group
    version = rootProject.version
}