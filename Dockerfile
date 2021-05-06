FROM openjdk:14-alpine as jvm-builder
ENV JAVA_MINIMAL="/opt/java-minimal"
RUN apk add binutils
RUN jlink \
    --verbose \
    --add-modules \
        java.base,java.sql,java.naming,java.desktop,java.management,java.security.jgss,java.instrument,jdk.jdwp.agent,jdk.unsupported,jdk.net \
    --compress 2 --strip-debug --no-header-files --no-man-pages \
    --output "$JAVA_MINIMAL"

FROM alpine:3.11
ARG PROFILE
ARG SCHEMA_REGISTRY_USERNAME
ARG SCHEMA_REGISTRY_PASSWORD
ENV PROFILE $PROFILE
ENV SCHEMA_REGISTRY_USERNAME $SCHEMA_REGISTRY_USERNAME
ENV SCHEMA_REGISTRY_PASSWORD $SCHEMA_REGISTRY_PASSWORD
ENV JAVA_HOME="/opt/java-minimal"
ENV PATH="$PATH:$JAVA_HOME/bin"
COPY --from=jvm-builder "$JAVA_HOME" "$JAVA_HOME"
WORKDIR lost-report-service
COPY build/libs/lost-report-service.jar ./
CMD java \
    -Dspring.profiles.active=$PROFILE \
    -DSCHEMA_REGISTRY_USERNAME=$SCHEMA_REGISTRY_USERNAME \
    -DSCHEMA_REGISTRY_PASSWORD=$SCHEMA_REGISTRY_PASSWORD \
    -jar lost-report-service.jar