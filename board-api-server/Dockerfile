FROM openjdk:17-jdk
ARG JAR_FILE="build/libs/*.jar"
COPY ${JAR_FILE} board-api-server.jar
ENTRYPOINT ["java", "-jar","/board-api-server.jar"]
