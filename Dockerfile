FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/certWatch-0.0.1-SNAPSHOT.jar certwatch-v1.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "certwatch-v1.jar"]