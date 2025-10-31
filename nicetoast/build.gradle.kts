import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("com.vanniktech.maven.publish") version "0.34.0"
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
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

mavenPublishing {
    // ✅ This automatically configures publishing to the new Central Portal API
    publishToMavenCentral()

    // ✅ Automatically signs all publications using environment GPG vars
    signAllPublications()

    // ✅ Define your coordinates (groupId, artifactId, version)
    coordinates(
        project.group.toString(),
        "nicetoast",
        project.version.toString()
    )

    // ✅ POM metadata (required by Central)
    pom {
        name.set("NiceToast")
        description.set("Nice Toast is a stunning and highly customizable toast library for Android written in Kotlin.")
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
            connection.set("scm:git:https://github.com/dononcharles/NiceToast.git")
            developerConnection.set("scm:git:ssh://github.com/dononcharles/NiceToast.git")
            url.set("https://github.com/dononcharles/NiceToast")
        }
    }
}