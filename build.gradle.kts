plugins {
    kotlin("jvm") version "2.3.20"
    id("de.eldoria.plugin-yml.paper") version "0.9.0"
    // id("com.gradleup.shadow") version "9.2.2"
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
}

val craftEngineVersion = "26.5-SNAPSHOT"

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")

    compileOnly("net.momirealms:craft-engine-core:$craftEngineVersion")
    compileOnly("net.momirealms:craft-engine-bukkit:$craftEngineVersion")

    // implementation(kotlin("stdlib"))

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