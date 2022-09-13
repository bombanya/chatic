FROM maven:3.8.6-eclipse-temurin-17
WORKDIR /chatic
COPY . .
WORKDIR src/main/resources/certs
RUN rm *
RUN openssl genrsa -out keypair.pem 2048
RUN openssl rsa -in keypair.pem -pubout -out public.pem
RUN openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
WORKDIR /chatic
RUN mvn clean install -DskipTests
CMD mvn spring-boot:run
