# JVM Builder
FROM openjdk:14-alpine as jvm-builder
ENV JAVA_MINIMAL="/opt/java-minimal"
RUN apk add binutils
RUN jlink \
    --verbose \
    --add-modules \
        java.base,java.sql,java.naming,java.desktop,java.management,java.security.jgss,java.instrument,jdk.jdwp.agent,jdk.unsupported,jdk.net \
    --compress 2 --strip-debug --no-header-files --no-man-pages \
    --output "$JAVA_MINIMAL"

# Fast build with local gradle
FROM alpine:3.11 as fast
ENV JAVA_HOME="/opt/java-minimal"
ENV PATH="$PATH:$JAVA_HOME/bin"
ARG PROFILE
ENV PROFILE $PROFILE
COPY --from=jvm-builder "$JAVA_HOME" "$JAVA_HOME"
WORKDIR lost-report-service
COPY ./ ./
ENTRYPOINT java -jar -Dspring.profiles.active=$PROFILE build/libs/lost-report-service.jar

# JAR builder
FROM openjdk:14 as jar-builder
WORKDIR lost-report-service
COPY ./ ./
RUN ./gradlew bootJar
RUN mv build/libs/* .

# Full build with dockerized gradle
FROM alpine:3.11 as full
ENV JAVA_HOME=/opt/java-minimal
ENV PATH="$PATH:$JAVA_HOME/bin"
ARG PROFILE
ENV PROFILE $PROFILE
COPY --from=jvm-builder "$JAVA_HOME" "$JAVA_HOME"
WORKDIR lost-report-service
COPY --from=jar-builder /lost-report-service/lost-report-service.jar .
ENTRYPOINT java -jar -Dspring.profiles.active=$PROFILE lost-report-service.jar