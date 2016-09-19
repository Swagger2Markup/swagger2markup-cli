FROM openjdk:8
MAINTAINER Robert Winkler

WORKDIR /usr/bin/swagger2markup

RUN wget https://jcenter.bintray.com/io/github/swagger2markup/swagger2markup-cli/1.0.1/swagger2markup-cli-1.0.1.jar

ENTRYPOINT ["java","-jar","/usr/bin/swagger2markup/swagger2markup-cli-1.0.1.jar"]