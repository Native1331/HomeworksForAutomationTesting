import org.gradle.internal.impldep.org.junit.platform.launcher.TagFilter.includeTags

plugins {
    id("java")
    kotlin("jvm")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation(kotlin("stdlib-jdk8"))
    // Source: https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
    testImplementation("org.junit.jupiter:junit-jupiter-api:6.1.3")
// Source: https://mvnrepository.com/artifact/org.assertj/assertj-core
    testImplementation("org.assertj:assertj-core:3.27.7")
    // Source: https://mvnrepository.com/artifact/io.rest-assured/rest-assured
    implementation("io.rest-assured:rest-assured:5.5.6")
}

kotlin {
    jvmToolchain(21)
}
tasks.test {
    useJUnitPlatform ()
       }

tasks.register<Test>("specialTests") {
    // Явно указываем classpath
    classpath = sourceSets["test"].runtimeClasspath
    testClassesDirs = sourceSets["test"].output
    useJUnitPlatform {
        includeTags("SpecialTest")
    }

    maxParallelForks = 2
    testLogging {
        events("passed", "failed", "skipped")
        showStandardStreams = true
    }
}
tasks.register<Test>("newAutotests") {
    // Явно указываем classpath
    classpath = sourceSets["test"].runtimeClasspath

    useJUnitPlatform {
        includeTags("newAutotests")
    }}
tasks.register<Test>("restAssuredTests"){
    useJUnitPlatform{includeTags("RestAssuredTests")}
}
tasks.register<Test>("assertJTest"){
    useJUnitPlatform{includeTags("AssertJTest")}
}
tasks.register<Test>("statusCodeTest"){
    useJUnitPlatform(){includeTags("StatusCodeTest")}
}

tasks.register("Tests") {
    doLast { println("Test run is over") }}





