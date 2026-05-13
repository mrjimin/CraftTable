plugins {
    kotlin("jvm") version "2.3.20"
    id("de.eldoria.plugin-yml.paper") version "0.9.0"
    id("com.gradleup.shadow") version "9.4.1"
}

group = "com.github.mrjimin.crafttable"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven("https://repo.momirealms.net/snapshots/")
    maven("https://repo.xenondevs.xyz/releases")
}

val craftEngineVersion = "26.5-SNAPSHOT"

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")

    compileOnly("net.momirealms:craft-engine-core:$craftEngineVersion")
    compileOnly("net.momirealms:craft-engine-bukkit:$craftEngineVersion")

    implementation("xyz.xenondevs.invui:invui-kotlin:2.1.0")

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(25)
}

tasks.test {
    useJUnitPlatform()
}

paper {
    main = "com.github.mrjimin.crafttable.CraftTablePlugin"
    loader = "com.github.mrjimin.crafttable.BukkitLoader"
    version = project.version.toString()
    name = "CraftTable"
    apiVersion = "1.20"
    authors = listOf("mrjimin")
    serverDependencies {
        register("CraftEngine") {
            required = true
        }
    }
}

val shadowJarPlugin = tasks.register<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJarPlugin") {
    archiveFileName = "CraftTable-${project.version}.jar"
    destinationDirectory.set(file("${project.rootDir}/target"))

    from(sourceSets.main.get().output)
    configurations = listOf(project.configurations.runtimeClasspath.get())

    exclude("kotlin/**", "kotlinx/**")
}