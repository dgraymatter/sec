import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.21"
    kotlin("kapt") version "1.4.21"
    application
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {

    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation(kotlin("stdlib-jdk8"))

    // Need full reflection
    implementation(kotlin("reflect"))

    // Use the Kotlin JUnit integration.
    testImplementation(kotlin("test-junit5"))

    // Command Line support
    implementation("info.picocli:picocli:4.6.1")
    kapt("info.picocli:picocli-codegen:4.6.1")

    // palantir
    implementation("com.palantir.config.crypto:encrypted-config-value:2.1.0")

    // JUnit5
    val junitVersion = "5.7.0"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

application {
    mainClass.set("me.andriefc.secj.App")
}

tasks.withType<CreateStartScripts> {
    applicationName = "sec"
}

val javacTarget = JavaVersion.VERSION_1_8.toString()

tasks.withType<JavaCompile> {
    sourceCompatibility = javacTarget
    targetCompatibility = javacTarget
}

tasks.withType<Jar> {
    archiveBaseName.set("sec")
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = javacTarget
    }
}

kapt {
    arguments {
        arg("project", "${project.group}/${project.name}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform {
    }
}