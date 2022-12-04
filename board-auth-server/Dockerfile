FROM openjdk:17-jdk
ARG JAR_FILE="build/libs/*.jar"
COPY ${JAR_FILE} board-auth-server.jar
ENTRYPOINT ["java", "-jar","/board-auth-server.jar"]
