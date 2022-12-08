plugins {
    id("base")
}

allprojects {
    group = "br.dev.pedrolamarao.accounting"
    version = "1.0-SNAPSHOT"

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release.convention(19)
    }
}