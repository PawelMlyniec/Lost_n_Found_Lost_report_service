import com.google.protobuf.gradle.*

plugins {
    id("org.springframework.boot") version "2.4.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.google.protobuf") version "0.8.16"
    id("com.github.imflog.kafka-schema-registry-gradle-plugin") version "1.2.0"
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

schemaRegistry {
    url.set(System.getenv("SCHEMA_REGISTRY_URL") ?: "http://localhost:9099/")
    credentials {
        username.set(System.getenv("SCHEMA_REGISTRY_USERNAME") ?: "registry-user")
        password.set(System.getenv("SCHEMA_REGISTRY_PASSWORD") ?: "<password>")
    }
    register {
        subject("lrs-lost-reports-proto-ItemsMatchedProto", "src/main/proto/com/pw/lrs/ItemsMatched.proto", "PROTOBUF")
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
    annotationProcessor("org.springframework.boot", "spring-boot-configuration-processor")

    // Security
    implementation("org.springframework.boot", "spring-boot-starter-security")
    implementation("org.springframework.boot", "spring-boot-starter-oauth2-resource-server")

    // Kafka
    implementation("org.springframework.kafka", "spring-kafka")

    // MongoDB
    implementation("org.springframework.boot", "spring-boot-starter-data-mongodb")

    // Querydsl
    implementation("com.querydsl", "querydsl-core", "4.4.0")
    implementation("com.querydsl", "querydsl-jpa", "4.4.0")
    implementation("com.querydsl:querydsl-mongodb:4.4.0")
    annotationProcessor("com.querydsl:querydsl-apt:4.4.0:morphia")

    // Lombok
    compileOnly("org.projectlombok", "lombok")
    annotationProcessor("org.projectlombok", "lombok")

    // Retrofit
    implementation("com.squareup.retrofit2","retrofit", "2.9.0")
    implementation ("com.squareup.retrofit2", "converter-gson", "2.9.0")

    // Auth0
    implementation ("com.auth0","java-jwt","3.15.0")

    // Protobuf
    implementation("com.google.protobuf:protobuf-java:3.6.1")

    // Confluent
    implementation("io.confluent", "kafka-protobuf-serializer", "5.5.1")
    implementation("io.confluent", "kafka-schema-registry-client", "5.5.1")
    implementation("io.confluent", "monitoring-interceptors", "5.5.1")

    // Metrics
    implementation("org.springframework.boot", "spring-boot-starter-actuator", "2.4.0")
    implementation("io.micrometer", "micrometer-registry-prometheus", "latest.release")

    // OpenAPI
    implementation("org.springdoc", "springdoc-openapi-ui","1.5.7")

    // Tests
    testImplementation("org.springframework.boot", "spring-boot-starter-test")
    testImplementation("org.springframework.kafka", "spring-kafka-test")
}

tasks {

    bootJar {
        mainClass.set("com.pw.lrs.LostReportService")
        archiveFileName.set("lost-report-service.jar")
        dependsOn("generateProto")
    }

    register<Exec>("dockerBuild") {
        group = "build"
        description = "Builds Docker Image"
        dependsOn("bootJar")
        commandLine("docker", "build", "-t", "lost-report-service", "--build-arg", "PROFILE=dev", ".")
    }
}