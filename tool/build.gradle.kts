plugins {
    id("com.github.johnrengelman.shadow")
    id("io.micronaut.application")
}

application {
    applicationName = "accounting-tool"
    mainClass.set("br.dev.pedrolamarao.accounting.tool.AccountingTool")
}

micronaut {
    version.set("3.5.3")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("br.dev.pedrolamarao.accounting.*")
    }
}

dependencies {
    annotationProcessor("info.picocli:picocli-codegen")
    implementation("info.picocli:picocli")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut.picocli:micronaut-picocli")
    runtimeOnly(libs.slf4j.simple)
}
