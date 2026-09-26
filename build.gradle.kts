

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

}

kotlin {
    jvmToolchain(21)
}
tasks.test {
    useJUnitPlatform{}
    println("TEST PASSED")
   }


tasks.register("Tests"){
    dependsOn("testClasses")
    dependsOn("clean")
    doLast{println("Test run is over") }}

