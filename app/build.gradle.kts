plugins {
    application
    checkstyle
    id("com.github.ben-manes.versions") version "0.54.0"
    id("org.sonarqube") version "7.3.0.8198"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("info.picocli:picocli:4.7.7")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
}

application {
    mainClass = "hexlet.code.App"
}

sonar {
    properties {
        property("sonar.projectKey", "2DimBer3_qa-auto-engineer-java-project-71")
        property("sonar.organization", "2dimber3")
    }
}

tasks.test {
    useJUnitPlatform()
}