FROM openjdk:8-jdk-alpine
RUN apk add --no-cache tzdata
ENV TZ GMT-3
ENV TZ=GMT-3
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN apk --update add fontconfig ttf-dejavu
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY target/lib lib
ENTRYPOINT ["java","-jar","/app.jar"]