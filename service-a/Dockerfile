FROM openjdk:17
COPY ./target/libs /tmp/libs
COPY ./target/helidon-micro.jar /tmp
COPY ./oci/ /root/.oci
WORKDIR /tmp
ENTRYPOINT ["java","-jar","helidon-micro.jar"]