FROM openjdk:17-jdk
ARG JAR_FILE="build/libs/*.jar"
COPY ${JAR_FILE} board-chat-server.jar
ENTRYPOINT ["java", "-jar","/board-chat-server.jar"]
