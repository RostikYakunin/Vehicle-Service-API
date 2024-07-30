FROM gradle:8.1.1-jdk17 AS builder

WORKDIR /app
COPY ./ /app/

RUN ./gradlew clean build

FROM builder

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar /app/app.jar
CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "/app/app.jar"]
