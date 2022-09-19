pluginManagement {
    plugins {
        id("com.github.johnrengelman.shadow") version "7.1.2"
        id("io.micronaut.application") version "3.4.1"
        id("io.micronaut.library") version "3.4.1"
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

rootProject.name = "accounting"

include("model")
include("service")
include("tool")
