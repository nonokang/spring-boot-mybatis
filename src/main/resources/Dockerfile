FROM java:8u111-jdk
ADD /image-0.0.2-SNAPSHOT.jar //
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "/image-0.0.2-SNAPSHOT.jar"]
