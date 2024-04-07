plugins {
    id("build-jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":weakCon-api-v1"))
    implementation(project(":weakCon-common"))

    testImplementation(kotlin("test-junit"))
}