plugins {
    id("io.micronaut.library")
}

micronaut {
    version.set("3.10.0")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("br.dev.pedrolamarao.accounting.*")
    }
}

dependencies {
    annotationProcessor("io.micronaut.data:micronaut-data-processor")
    implementation("io.micronaut.data:micronaut-data-model")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
}

tasks.test.configure {
    useJUnitPlatform()
}