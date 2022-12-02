plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
    id("maven-publish")
    id("com.jfrog.artifactory")
    id("com.pschsch.artifactory.publish")
}

group = findProperty("mavenGroup") ?: throw IllegalStateException()
version = findProperty("mavenArtifactVersion") ?: throw IllegalStateException()

kotlin {
    android {
        publishAllLibraryVariants()
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    jvm()
    js(IR) {
        browser()
        nodejs()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api("io.ktor:ktor-client-core:2.1.0")
                implementation("io.ktor:ktor-client-logging:2.1.0")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.1.0")
                implementation("io.ktor:ktor-client-content-negotiation:2.1.0")
                implementation("io.github.aakira:napier:2.6.1")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:2.1.0")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-cio-jvm:2.1.0")
            }
        }
        val jsMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation("io.ktor:ktor-client-js:2.1.0")
            }
        }
        val iosMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation("io.ktor:ktor-client-ios:2.1.0")
            }
        }
        listOf(
            "iosX64",
            "iosArm64",
            "iosSimulatorArm64"
        ).forEach {
            sourceSets.getByName(it + "Main").dependsOn(iosMain)
        }
    }
}

android {
    namespace = "com.pschsch.core.kmm.ktor.client.setup"
    compileSdk = 33
    sourceSets.getByName("main").manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }
    libraryVariants.all {
        generateBuildConfigProvider?.get()?.enabled = false
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

artifactoryPublishConfig {
    kotlinMultiplatform {
        includeAndroid = true
        includeIOS = true
        includeJVM = true
        includeJS = true
    }
}