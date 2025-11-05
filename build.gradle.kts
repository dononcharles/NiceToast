import com.vanniktech.maven.publish.MavenPublishBaseExtension

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.vanniktech.maven.publish) apply false
}

subprojects {
    afterEvaluate {
        if (plugins.hasPlugin("com.vanniktech.maven.publish")) {
            apply(plugin = "signing")
            // Maven publishing configuration
            configure<MavenPublishBaseExtension> {
                // Use per-module overrides if provided
                val groupId = rootProject.property("GROUP") as String
                val artifactId = (project.findProperty("POM_ARTIFACT_ID") ?: project.name) as String
                val version = project.findProperty("POM_VERSION") as String

                coordinates(groupId, artifactId, version)

                pom {
                    name.set(artifactId)
                    description.set(
                        if (artifactId.startsWith("c", ignoreCase = true)) {
                            "$artifactId is a stunning and highly customizable COMPOSE toast library for Android written in Kotlin."
                        } else {
                            "$artifactId is a stunning and highly customizable LEGACY VIEW-BASED toast library for Android written in Kotlin."
                        }
                    )
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

                publishToMavenCentral()
            }

            extensions.configure<SigningExtension> {
                useGpgCmd()
                val publishingExtension = extensions.getByType<PublishingExtension>()
                sign(publishingExtension.publications)
            }
        }
    }
}