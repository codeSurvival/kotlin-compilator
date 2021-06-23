FROM gradle:7.0.0-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM alpine:3.10

ENV GRADLE_VERSION 6.9

RUN apk --no-cache add openjdk11 --repository=http://dl-cdn.alpinelinux.org/alpine/edge/community
RUN apk add bash curl docker unzip bash-completion ca-certificates
RUN cd /opt && curl -sSl http://mirror.vorboss.net/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz | tar -xz

RUN apk -U add --no-cache curl; \
    curl https://downloads.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip > gradle.zip; \
    unzip gradle.zip; \
    rm gradle.zip; \
    apk del curl; \
    apk update && apk add --no-cache libstdc++ && rm -rf /var/cache/apk/*

ENV PATH "$PATH:/opt/apache-maven-3.6.3/bin:/gradle-${GRADLE_VERSION}/bin/"

USER root
WORKDIR app
EXPOSE 8081
ENV SPRING_DATASOURCE_URL="fill it" SPRING_DATASOURCE_USERNAME="fill it" SPRING_DATASOURCE_PASSWORD="fill it"

COPY --from=build /home/gradle/src/compilator /app/compilator
COPY --from=build /home/gradle/src/exposition/build/libs/*.jar /app/kotlin-compiler.jar
COPY --from=build /home/gradle/src/build.gradle.kts /app/build.gradle.kts

ENTRYPOINT ["java", "-jar","kotlin-compiler.jar"]