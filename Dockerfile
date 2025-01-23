FROM adoptopenjdk/openjdk11:alpine
RUN apk add --no-cache tzdata
ENV TZ GMT-3
ENV TZ=GMT-3
ENV JAVA_OPTS="-Xms256m -Xmx768m"
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN apk --update add fontconfig ttf-dejavu
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app.jar"]