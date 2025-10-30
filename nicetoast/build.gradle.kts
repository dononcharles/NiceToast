import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.dononcharles.nicetoast"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.junit.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId = "io.github.dononcharles"
                artifactId = "nicetoast"
                version = "1.0.0"

                afterEvaluate {
                    from(components["release"])
                }

                // --- Include the required POM metadata ---
                // Maven Central requires this information.
                pom {
                    name.set("NiceToast") // Corrected the name from 'MotionToast' to 'NiceToast'
                    description.set("Nice Toast is a stunning and highly customizable toast library for Android written in Kotlin")
                    url.set("https://github.com/dononcharles/NiceToast")
                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("dononcharles")
                            name.set("Komi Donon")
                            email.set("dononcharles@gmail.com")
                        }
                    }
                    scm {
                        connection.set("scm:git:github.com/dononcharles/NiceToast.git")
                        developerConnection.set("scm:git:ssh://github.com/dononcharles/NiceToast.git")
                        url.set("https://github.com/dononcharles/NiceToast")
                    }
                }

            }
        }
    }
    repositories {
        // This is the staging repository where artifacts are sent before release
        maven {
            name = "sonatype" // A more standard name for this repository
            val releasesRepoUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
            val snapshotsRepoUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
            // Automatically select the correct URL based on the library's version string
            url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)

            credentials {
                // Read credentials securely from environment variables
                username = System.getenv("OSSRH_USERNAME") // Using OSSRH prefix for clarity
                password = System.getenv("OSSRH_TOKEN")
            }
        }
    }
}