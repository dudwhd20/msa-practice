FROM openjdk:17
LABEL maintainer="youngjongso"

ARG JAR_FILE=build/libs/product-service.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
