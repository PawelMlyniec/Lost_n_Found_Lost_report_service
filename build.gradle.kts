import com.google.protobuf.gradle.*

plugins {
    id("org.springframework.boot") version "2.4.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.google.protobuf") version "0.8.15"
    java
    idea
}

group = "com.pw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("http://packages.confluent.io/maven/")
}

buildscript {
    repositories {
        jcenter()
        maven("http://packages.confluent.io/maven/")
        maven("https://plugins.gradle.org/m2/")
        maven("https://jitpack.io")
    }
}

sourceSets {
    main {
        java {
            setSrcDirs(listOf("src/main/java", "build/generated/source/openapi/src/main/java"))
        }
        resources {
            setSrcDirs(listOf("src/main/resources", "build/generated/source/openapi/src/main/resources"))
        }
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.6.1"
    }
}

dependencies {
    // Spring
    implementation("org.springframework.boot", "spring-boot-starter-web")
    implementation("org.springframework.kafka", "spring-kafka")
    implementation("org.springframework.boot", "spring-boot-starter-data-mongodb")
    annotationProcessor("org.springframework.boot", "spring-boot-configuration-processor")

    // Lombok
    compileOnly("org.projectlombok", "lombok")
    annotationProcessor("org.projectlombok", "lombok")

    // Protobuf
    implementation("com.google.protobuf:protobuf-java:3.6.1")

    // Confluent
    implementation("io.confluent", "kafka-protobuf-serializer", "5.5.1")
    implementation("io.confluent", "kafka-schema-registry-client", "5.5.1")
    implementation("io.confluent", "monitoring-interceptors", "5.5.1")

    // OpenAPI
    compileOnly("org.springdoc", "springdoc-openapi-ui","1.5.7")

    // Tests
    testImplementation("org.springframework.boot", "spring-boot-starter-test")
    testImplementation("org.springframework.kafka", "spring-kafka-test")
}
