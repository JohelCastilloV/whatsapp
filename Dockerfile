FROM openjdk:17-oracle

WORKDIR /usr/app

ADD target/*jar app.jar

CMD sleep 10 && java -jar app.jar