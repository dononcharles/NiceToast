import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.Base64

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
            nexusUrl.set(uri("https://ossrh-staging-api.central.sonatype.com/service/local/staging/deploy/maven2/"))
            // Only set the snapshot repository URL if the version contains "SNAPSHOT"
            if (project.version.toString().endsWith("SNAPSHOT", ignoreCase = true)) {
                snapshotRepositoryUrl.set(uri("https://central.sonatype.com/repository/maven-snapshots/"))
            }
            username.set(System.getenv("OSSRH_USERNAME"))
            password.set(System.getenv("OSSRH_TOKEN"))
        }
    }
}

tasks.register("publishToMavenCentral") {
    group = "publishing"
    description = "Publishes all Maven publications produced by this project to Maven Central."
    dependsOn("publish")

    doLast {
        val username = System.getenv("OSSRH_USERNAME")
        val password = System.getenv("OSSRH_TOKEN")
        require(!username.isNullOrBlank()) { "Missing OSSRH_USERNAME env var" }
        require(!password.isNullOrBlank()) { "Missing OSSRH_TOKEN env var" }

        val namespace = project.group.toString()
        val basic = Base64.getEncoder().encodeToString("$username:$password".toByteArray())

        val client = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder()
            .uri(uri("https://ossrh-staging-api.central.sonatype.com/manual/upload/defaultRepository/$namespace"))
            .header("Authorization", "Basic $basic")
            .POST(HttpRequest.BodyPublishers.noBody())
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        if (response.statusCode() < 400) {
            logger.lifecycle(response.body())
        } else {
            logger.error("Upload failed (${response.statusCode()}): ${response.body()}")
        }
    }
}