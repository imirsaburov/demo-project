FROM maven:3.9.6-amazoncorretto-21 as builder

ENV HOME=/app

RUN mkdir -p $HOME
WORKDIR $HOME

COPY ./ ./

RUN mvn -f $HOME/pom.xml clean -DskipTests
RUN mvn -f $HOME/pom.xml package -DskipTests

RUN mv ./target/*.jar /app/app.jar

RUN ls -la

FROM eclipse-temurin:21.0.2_13-jre

ENV TZ="Asia/Tashkent"

RUN mkdir /app

WORKDIR /app

COPY --from=builder /app/app.jar /app/app.jar

RUN ls -la

ENTRYPOINT ["java", "-jar", "app.jar"]
