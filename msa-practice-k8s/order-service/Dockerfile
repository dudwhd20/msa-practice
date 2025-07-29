FROM eclipse-temurin:17-jdk
LABEL maintainer="youngjongso"

ARG JAR_FILE=build/libs/order-service.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
