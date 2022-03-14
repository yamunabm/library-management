FROM maven:latest as build
ADD . / library-management/
WORKDIR /library-management
RUN mvn clean install -U -DskipTests

FROM openjdk:11.0.7-jre-slim-buster
RUN mkdir library-management
WORKDIR /library-management/
COPY --from=build /library-management/target/library-management-0.0.1-SNAPSHOT.jar .

ENV PORT='8085'
EXPOSE ${PORT}

ENTRYPOINT /bin/sh -c "java -jar /library-management/library-management-0.0.1-SNAPSHOT.jar"