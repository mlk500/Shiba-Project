FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/app-0.0.1-SNAPSHOT.jar sheba-server.jar
ENV MAIN_ADMIN_USERNAME=tal-main-admin
ENV MAIN_ADMIN_PASSWORD=Tal@Sheba@2024
ENV JWT_SECRET_KEY=bd279fb047279c287f52be609c28bf863e9699f44c5593a593a3fdcc2b37f0b7

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "sheba-server.jar"]



