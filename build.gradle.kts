// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias (libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.nexus.publish)
}

group = project.property("PUBLISHING_GROUP") as String
version = project.property("PUBLISHING_VERSION") as String

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://ossrh-staging-api.central.sonatype.com/service/local/"))
            // Only set the snapshot repository URL if the version contains "SNAPSHOT"
            if (project.version.toString().endsWith("SNAPSHOT", ignoreCase = true)) {
                snapshotRepositoryUrl.set(uri("https://central.sonatype.com/repository/maven-snapshots/"))
            }
            username.set(System.getenv("OSSRH_USERNAME"))
            password.set(System.getenv("OSSRH_TOKEN"))
        }
    }
}