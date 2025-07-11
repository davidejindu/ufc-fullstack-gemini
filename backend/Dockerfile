FROM eclipse-temurin:17-jdk

WORKDIR /app


COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml .


RUN ./mvnw dependency:go-offline


COPY src ./src


RUN ./mvnw clean package -DskipTests


COPY target/*.jar app.jar


ENTRYPOINT ["java", "-jar", "/app/app.jar"]
