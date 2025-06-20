FROM openjdk:17-jdk-slim

LABEL maintainer="Abdoulaye Kaba abdallahkaba98@gmail.com"

COPY target/gestion-produits.jar gestion-produits.jar

ENTRYPOINT ["java","-jar","gestion-produits.jar"]

EXPOSE 8083