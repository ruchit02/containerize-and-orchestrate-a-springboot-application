::1
---

docker run -d -p 5672:5672 \
-p 15672:15672 \
--network proj-net \
--name mssg-broker \
--hostname ruchit-darji \
rabbitmq:3.9.2-management

::2
---

docker run -d -p 3307:3306 \
--security-opt seccomp=unconfined \
--network proj-net \
--name mysql-db \
-v ${PWD}:/docker-entrypoint-initdb.d \
-v mysql-data:/var/lib/mysql \
-e MYSQL_ROOT_PASSWORD=root \
mysql:8.0.26

::3
---

docker run -d -p 8083:8083 \
--network proj-net \
--name auth-service \
-e MYSQL_USER=root \
-e MYSQL_PASSWORD=root \
-e MYSQL_URL=jdbc:mysql://mysql-db:3306/websecurity \
-e BROKER_HOST=mssg-broker -e BROKER_PORT=5672 \
-e TRANSFER_PROTOCOL=http \
-e GATEWAY_SERVICE_HOST=192.168.99.100 -e GATEWAY_SERVICE_PORT=8081 \
auth-image

::4
---

docker run -d -p 8082:8082 \
--network proj-net \
--name doc-service \
-e MYSQL_USER=root \
-e MYSQL_PASSWORD=root \
-e MYSQL_DATABASE=employeedocumentdetails \
-e MYSQL_URL=jdbc:mysql://mysql-db:3306/employeedocumentdetails \
-e BROKER_HOST=mssg-broker -e BROKER_PORT=5672 \
-e TRANSFER_PROTOCOL=http \
-e GATEWAY_SERVICE_HOST=192.168.99.100 -e GATEWAY_SERVICE_PORT=8081 \
photo-image

::5
---

docker run -d -p 8081:8081 \
--network proj-net \
--name gateway-service \
-e BROKER_HOST=mssg-broker -e BROKER_PORT=5672 \
-e TRANSFER_PROTOCOL=http \
-e AUTH_SERVICE_HOST=auth-service -e AUTH_SERVICE_PORT=8083 \
-e DOC_SERVICE_HOST=doc-service -e DOC_SERVICE_PORT=8082 \
gateway-image


::Previous Dockerfile
---------------------

FROM openjdk:8-jdk-alpine

WORKDIR /app

COPY .mvn/ .mvn

COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw","spring-boot:run"]