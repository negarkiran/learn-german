FROM openjdk:12-jdk-alpine

RUN addgroup -S german && adduser -S german -G german
USER german:german

COPY leran-german-app/build/libs/*.jar home/spring/learn-german.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/home/spring/learn-german.jar"]