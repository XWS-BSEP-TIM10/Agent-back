FROM maven:3.8.5-openjdk-17-slim AS build
RUN ls
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

FROM openjdk:17-alpine
COPY --from=build /home/app/target/Agent-0.0.1-SNAPSHOT.jar /usr/local/lib/Agent-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/Agent-0.0.1-SNAPSHOT.jar"]