import build.buf.gradle.BUF_BUILD_DIR
import build.buf.gradle.GENERATED_DIR
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.buf)
}

dependencies {
    api(platform(libs.protobuf.bom))
    api(libs.protobuf.kotlin)
    api(libs.protobuf.java)
    api(platform(libs.grpc.bom))
    api(libs.grpc.stub)
    api(libs.grpc.kotlin.stub)
    api(libs.grpc.protobuf)
}

sourceSets {
    listOf("java", "kotlin").forEach {
        val dir = "${layout.buildDirectory.get()}/$BUF_BUILD_DIR/$GENERATED_DIR/$it"
        getByName("main").java.srcDir(dir)
    }
}

tasks {
    withType<JavaCompile> {
        dependsOn("bufGenerate", "bufBuild")
    }

    withType<KotlinCompile> {
        dependsOn("bufGenerate", "bufBuild")
    }
}

buf {
    toolVersion = libs.versions.bufCli.get()
}
