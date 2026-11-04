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
    implementation("org.seleniumhq.selenium:selenium-java:4.51.0")
    testImplementation("org.testng:testng:7.10.2")
    implementation("org.seleniumhq.selenium:selenium-devtools-v155:4.51.0")
    testImplementation("com.codeborne:selenide:7.19.0")
    // Source: https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly("org.projectlombok:lombok:1.18.46")
    implementation("org.projectlombok:lombok:1.18.46")
    // Source: https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    annotationProcessor("org.projectlombok:lombok:1.18.46")
    implementation("org.slf4j:slf4j-api:2.0.18")
    testCompileOnly("org.projectlombok:lombok:1.18.46")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.46")
    runtimeOnly("ch.qos.logback:logback-classic:1.4.14")
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("ch.qos.logback:logback-classic:1.4.14")
    // Source: https://mvnrepository.com/artifact/org.aeonbits.owner/owner
    implementation("org.aeonbits.owner:owner:1.0.12")
}


tasks.test {
    useJUnitPlatform()
    maxParallelForks = 4

    // или: maxParallelForks = (Runtime.getRuntime().availableProcessors() - 1).coerceAtLeast(1)

   }

kotlin {
    jvmToolchain(21)
}
tasks.test {
    useJUnitPlatform ()
       }
tasks.register<Test>("restAssuredTests"){
    useJUnitPlatform{includeTags("RestAssuredTests")}
}
tasks.register<Test>("assertJTest"){
    useJUnitPlatform{includeTags("AssertJTest")}
}
tasks.register<Test>("statusCodeTest"){
    useJUnitPlatform(){includeTags("StatusCodeTest")}
}
tasks.register<Test>("seleniumTests"){
    useJUnitPlatform{includeTags("SeleniumTests")}
}
tasks.register<Test>("selenideTests"){
    useJUnitPlatform{includeTags("SelenideTests")}
}
tasks.register<Test>("drAndDrTests"){
    useJUnitPlatform{includeTags("DrAndDrTests")}
    }
tasks.register<Test>("selenideWithPropertiesTests"){
    useJUnitPlatform{includeTags("SelenideWithPropertiesTests")}
}

tasks.register("Tests") {
    doLast { println("Test run is over") }}





