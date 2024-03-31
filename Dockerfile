# FROM openjdk:17-oracle
# #FROM maven:3.8.3-openjdk-17 AS build
# COPY target/jgame.jar jgame.jar
# EXPOSE 8080
# CMD ["java", "-jar", "jgame.jar"]


FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM maven:3.8.3-openjdk-17-slim

COPY --from=build /target/demo-0.0.1.jar jgame.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "jgame.jar"]

