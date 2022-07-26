plugins {
    id("io.micronaut.application") version "3.4.1"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

application {
    mainClass.set("br.dev.pedrolamarao.accounting.service.AccountingApplication")
}

micronaut {
    version.set("3.5.3")
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("br.dev.pedrolamarao.accounting.*")
    }
}

dependencies {
    annotationProcessor("io.micronaut:micronaut-http-validation")
    implementation(project(":model"))
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("io.micronaut:micronaut-validation")
}

tasks.withType<JavaCompile> {
    options.release.set(17)
}
