FROM openjdk:17
COPY ./target/ /tmp
COPY ./oci/ /root/.oci
WORKDIR /tmp
ENTRYPOINT ["java","-jar","helidon-micro.jar"]