FROM eclipse-temurin:21-jdk AS build

# Install Maven
RUN apt-get update && apt-get install -y maven

#Copy project files
COPY ./daily-quest/src /home/app/src
COPY ./daily-quest/pom.xml /home/app

#Build project
RUN mvn -f /home/app/pom.xml clean package
#RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
COPY --from=build /home/app/target/daily-quest-0.0.1.jar /usr/local/lib/daily-quest.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/daily-quest.jar"]