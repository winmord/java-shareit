FROM maven:3.9.2-amazoncorretto-11 as builder
WORKDIR /app
COPY . .
RUN mvn clean package -Dmaven.test.skip=true

FROM amazoncorretto:11
# ENV TZ=Europe/Moscow
# RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY --from=builder /app/server/target/*.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","/app.jar", "--server.port=9090"]
