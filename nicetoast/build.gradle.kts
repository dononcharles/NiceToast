import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
    id("signing")
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
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
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
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                artifactId = "nicetoast"

                pom {
                    name.set("NiceToast")
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
}

// Prefer providers API (avoids eager System.getenv calls)
val gpgKey = providers.environmentVariable("GPG_SECRET_KEY")
val gpgPass = providers.environmentVariable("GPG_SECRET_KEY_PASSPHRASE")

signing {
    val key = gpgKey.orNull
    val pass = gpgPass.orNull
    if (!key.isNullOrBlank() && !pass.isNullOrBlank()) {
        useInMemoryPgpKeys(key, pass)
        sign(publishing.publications["release"])
    } else {
        logger.warn("Signing disabled: missing GPG env vars")
    }
}