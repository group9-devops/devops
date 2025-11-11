FROM amazoncorretto:17
COPY ./target/devops.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "devops.jar", "db:3307", "30000"]