plugins {
    id("io.micronaut.application")
    id("com.github.johnrengelman.shadow")
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
    annotationProcessor("io.micronaut.data:micronaut-data-processor")
    implementation(project(":model"))
    implementation(libs.jackson.datatype.jsr310)
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut.data:micronaut-data-jdbc")
    implementation(libs.mockito.core)
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("io.micronaut.sql:micronaut-jdbc-hikari")
    runtimeOnly(libs.slf4j.simple)
}
repositories {
    mavenCentral()
}
