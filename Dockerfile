# 도커 이미지 내용
FROM openjdk:17 AS builder
WORKDIR /app

COPY ./gradlew .
COPY ./gradle .
COPY ./build.gradle .
COPY ./settings.gradle .
COPY ./src .
# gradlew 실행권한 부여
RUN chmod +x ./gradlew
RUN microdnf install findutils

FROM openjdk:17
WORKDIR /app
COPY --from=builder ./build/libs/springboot-developer-1.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "springboot-developer.jar"]
