FROM openjdk:21-jdk

WORKDIR /app

COPY target/digital-course-marketplace-0.0.1-SNAPSHOT.jar /app/digital-course-marketplace.jar

EXPOSE 8080

CMD ["java", "-jar", "digital-course-marketplace.jar"]