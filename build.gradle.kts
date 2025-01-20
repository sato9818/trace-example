import de.undercouch.gradle.tasks.download.Download
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.plugin.SpringBootPlugin
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.download)
}

private val javaVersion: String = libs.versions.jvm.get()

allprojects {
    group = "com.example.trace"
    repositories {
        mavenCentral()
    }
    tasks {
        withType<KotlinCompile> {
            compilerOptions {
                freeCompilerArgs.add("-Xjsr305=strict")
                freeCompilerArgs.add("-Xjvm-default=all")
                freeCompilerArgs.add("-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi")
                freeCompilerArgs.add("-opt-in=io.kotest.common.ExperimentalKotest")
                freeCompilerArgs.add("-opt-in=kotlinx.serialization.ExperimentalSerializationApi")
                freeCompilerArgs.add("-opt-in=kotlinx.serialization.InternalSerializationApi")
                freeCompilerArgs.add("-opt-in=kotlin.ExperimentalStdlibApi")
                freeCompilerArgs.add("-opt-in=kotlin.io.encoding.ExperimentalEncodingApi")

                jvmTarget.set(JvmTarget.fromTarget(javaVersion))
            }
        }

        withType<JavaCompile> {
            options.encoding = "UTF-8"
            options.compilerArgs.add("-parameters")
        }
    }
    plugins.withType<JavaPlugin> {
        configure<JavaPluginExtension> {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(javaVersion))
            }
        }
    }

    plugins.withType<SpringBootPlugin> {
        tasks.withType<BootRun> {
            environment(
                "OTEL_JAVAAGENT_CONFIGURATION_FILE" to "${rootProject.projectDir}/otel/otel.properties",
            )
            jvmArgs("-javaagent:${rootProject.projectDir}/otel/opentelemetry-javaagent.jar")
        }
    }
}

tasks {
    register<Download>("downloadOtelAgent") {
        src("https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v${libs.versions.openTelemetryAgent.get()}/opentelemetry-javaagent.jar")
        dest("$rootDir/otel")
        onlyIfModified(true)
        useETag(true)
    }
}
