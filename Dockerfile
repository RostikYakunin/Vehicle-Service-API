FROM gradle:8.1.1-jdk17-jammy AS builder

WORKDIR /app
COPY build.gradle.kts /app/
COPY settings.gradle.kts /app/
RUN gradle -x clean build --build-cache > /dev/null 2>&1 || true

COPY ./ /app/
RUN gradle clean build

FROM docker.io/eclipse-temurin:17-jre-jammy AS runtime
COPY --from=builder /app/build/libs/*.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]
