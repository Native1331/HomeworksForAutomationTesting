plugins {
    java
    kotlin("jvm")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val testProps = listOf(
    "browser_name", "browser_size", "admin_login", "admin_password",
    "start_product_name", "start_product_price", "baseUrl"
)


tasks.withType<Test>().configureEach {
    testProps.forEach { key ->
        System.getProperty(key)?.let { systemProperty(key, it) }
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<Test>("pageObjectTests") {
    useJUnitPlatform { includeTags("PageTests") }
    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath
    maxParallelForks = 1
}

tasks.register<Test>("restAssuredTests") {
    useJUnitPlatform { includeTags("RestAssuredTests") }
}

tasks.register<Test>("assertJTest") {
    useJUnitPlatform { includeTags("AssertJTest") }
}
tasks.register<Test>("mainPageTest") {
    useJUnitPlatform { includeTags("MainPageTests") }
}

tasks.register<Test>("statusCodeTest") {
    useJUnitPlatform { includeTags("StatusCodeTest") }
}

tasks.register<Test>("seleniumTests") {
    useJUnitPlatform { includeTags("SeleniumTests") }
}

tasks.register<Test>("selenideTests") {
    useJUnitPlatform { includeTags("SelenideTests") }
}

tasks.register<Test>("drAndDrTests") {
    useJUnitPlatform { includeTags("DrAndDrTests") }
}

tasks.register<Test>("selenideWithPropertiesTests") {
    useJUnitPlatform { includeTags("SelenideWithPropertiesTests") }
}

tasks.register("Tests") {
    doLast { println("Test run is over") }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.1.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("org.assertj:assertj-core:3.27.7")
    testImplementation("io.rest-assured:rest-assured:5.5.6")
    testImplementation("org.seleniumhq.selenium:selenium-java:4.51.0")
    testImplementation("org.seleniumhq.selenium:selenium-devtools-v155:4.51.0")
    testImplementation("com.codeborne:selenide:7.19.0")

    compileOnly("org.projectlombok:lombok:1.18.46")
    annotationProcessor("org.projectlombok:lombok:1.18.46")
    testCompileOnly("org.projectlombok:lombok:1.18.46")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.46")

    implementation("org.slf4j:slf4j-api:2.0.18")
    implementation("org.aeonbits.owner:owner:1.0.12")
    runtimeOnly("ch.qos.logback:logback-classic:1.4.14")

    implementation(kotlin("stdlib-jdk8"))
}

kotlin {
    jvmToolchain(21)
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
