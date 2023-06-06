FROM maven:3.9.2-amazoncorretto-11 as builder
WORKDIR /app
COPY .. .
RUN mvn clean package -Dmaven.test.skip=true

FROM amazoncorretto:11
COPY --from=builder /app/gateway/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]