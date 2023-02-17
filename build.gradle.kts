import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    application
    antlr
}

group = "me.user"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    antlr("org.antlr:antlr4:4.11.1")
}

tasks.named<AntlrTask>("generateGrammarSource").configure {
    arguments.addAll(listOf( "-visitor", "-no-listener"))
    outputDirectory = File("src/main/java")
}

tasks.named("compileKotlin").configure {
    dependsOn("generateGrammarSource")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}