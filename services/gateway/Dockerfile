FROM eclipse-temurin:24-jdk
WORKDIR /app
COPY build/libs/app.jar app.jar
EXPOSE 8700
ENTRYPOINT ["java", "-jar", "app.jar"]
