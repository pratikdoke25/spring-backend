# FROM openjdk:17-oracle
# FROM maven:3.8.3-openjdk-17 AS build
# COPY target/jGame.jar jGame.jar
# EXPOSE 8080
# CMD ["java", "-jar", "jGame.jar"]


FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM maven:3.8.3-openjdk-17-slim

COPY --from=build /target/jGame.jar jGame.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "jGame.jar"]

# CMD ["java", "-jar", "a-mazing-journey-backend.jar"]
