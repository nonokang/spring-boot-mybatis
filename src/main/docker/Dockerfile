FROM java:8u111-jdk

ADD /springboot-docker-0.0.2.jar //app.jar

EXPOSE 8082

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
