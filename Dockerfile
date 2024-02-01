FROM gradle:8.1.1-jdk17 AS dependencies

WORKDIR /app
COPY . /app/

FROM gradle:8.1.1-jdk17 AS builder

WORKDIR /app
COPY --from=dependencies /app/ /app/

RUN ./gradlew clean build -x test


