plugins {
    id("java")
}

allprojects {
    group = "br.com.josenaldo.kfb"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("java")
    }

   java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    val kafkaVersion = "3.6.1"

    dependencies {
        implementation(group="org.apache.kafka", name="kafka-clients", version=kafkaVersion)
        testImplementation(platform("org.junit:junit-bom:5.9.2"))
        testImplementation("org.junit.jupiter:junit-jupiter")
    }
}

tasks.test {
    useJUnitPlatform()
}
