FROM openjdk:17-jdk-slim

LABEL maintainer="Abdoulaye Kaba <abdallahkaba98@gmail.com>"

WORKDIR /app
COPY target/gestion-produits.jar app.jar

EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
