# 도커 이미지 내용
FROM openjdk:17 AS builder
WORKDIR /app

ARG JAR_FILE=/build/libs/springboot-developer-1.0.jar
COPY ${JAR_FILE} /app.jar
#COPY /build/libs/springboot-developer-1.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
