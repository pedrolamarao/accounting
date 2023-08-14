plugins {
    id("base")
}

allprojects {
    group = "br.dev.pedrolamarao.accounting"
    version = "1.0-SNAPSHOT"

    pluginManager.withPlugin("java-base") {
        tasks.withType<JavaCompile> {
            options.release.convention(20)
        }
    }
}

tasks.wrapper.configure {
    gradleVersion = "8.2.1"
}
