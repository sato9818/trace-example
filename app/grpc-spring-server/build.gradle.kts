plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.spring)
}

dependencies {
    implementation(libs.grpc.spring.boot.starter)
    implementation(project(":protocol:test-proto"))
}
