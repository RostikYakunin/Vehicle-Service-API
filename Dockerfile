FROM gradle:8.1.1-jdk17 AS builder

WORKDIR /app
COPY ./ /app/

RUN ./gradlew clean build -x test

FROM builder

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]