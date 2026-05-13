FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -DskipTests clean package

FROM tomcat:10.0-jdk17-temurin
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/social_network.war /usr/local/tomcat/webapps/social_network.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
