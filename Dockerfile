FROM openjdk:11.0-jdk-oracle

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src

EXPOSE 80

ENTRYPOINT ["./gradlew","clean","bootRun"]