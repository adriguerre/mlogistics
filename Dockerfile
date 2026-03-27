FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre AS app
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

FROM gcr.io/datadoghq/agent:latest-jmx AS datadog
RUN mkdir -p /opt/datadog-agent/bin/agent/dist/jmxfetch && \
    curl -sSL -o /opt/datadog-agent/bin/agent/dist/jmxfetch/jmxfetch.jar \
    "https://repo1.maven.org/maven2/com/datadoghq/jmxfetch/0.51.0/jmxfetch-0.51.0-jar-with-dependencies.jar"