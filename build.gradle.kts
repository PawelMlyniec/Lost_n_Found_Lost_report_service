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

    // Tests
    testImplementation("org.springframework.boot", "spring-boot-starter-test")
    testImplementation("org.springframework.kafka", "spring-kafka-test")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.6.1"
    }
}
