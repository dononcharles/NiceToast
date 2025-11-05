import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
   // id("com.vanniktech.maven.publish")
    alias(libs.plugins.vanniktech.maven.publish)
   // id("signing")
}

//group = "io.github.dononcharles"
//version = "1.0.1"

android {
    namespace = "com.dononcharles.nicetoast"
    compileSdk = 36

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
        jvmToolchain(17)
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    testImplementation(libs.androidx.junit.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

extra["POM_ARTIFACT_ID"] = "nicetoast"
extra["POM_VERSION"] = "1.0.1"

/*mavenPublishing {
    // ✅ Define your coordinates (groupId, artifactId, version)
    coordinates(artifactId = "nicetoast")

    // ✅ POM metadata (required by Central)
    pom {
        name.set("NiceToast")
        description.set("Nice Toast is a stunning and highly customizable toast library for Android written in Kotlin.")
        inceptionYear.set("2024")
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

    // ✅ This automatically configures publishing to the new Central Portal API
    publishToMavenCentral()
}*/
/*

signing {
    useGpgCmd()
    sign(publishing.publications)
}*/
