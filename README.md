# Tenpo PT Docker Backend

## Descripción

Este es un proyecto backend desarrollado con Spring Boot y Gradle. El proyecto incluye endpoints para gestionar `Tenpistas`, 3 tipos de `Transactions` y está configurado para ejecutarse en un contenedor Docker.

## Requisitos

- Java 21
- Gradle
- Docker
- PostgreSQL
- Postman

## Configuración

### Construir el proyecto

Para construir el proyecto, ejecuta el siguiente comando en la raíz del proyecto:

./gradlew build

Ejecutar ScriptSQL_PostgreSQL.sql en base de datos PostgreSQL

Ejecutar el proyecto localmente
Para ejecutar el proyecto localmente, usa el siguiente comando:

./gradlew bootRun

Construir y ejecutar con Docker
Para construir la imagen Docker y ejecutar el contenedor, usa los siguientes comandos:

docker build -t tenpo-pt-docker-backend .
docker run -p 8080:8080 tenpo-pt-docker-backend

Endpoints para Tenpistas

GET /tenpista
GET /tenpista/{id}
POST /tenpista
PUT /tenpista/{id}
DELETE /tenpista/{id}

Endpoints para Transacciones, TransaccionesEdit y TransaccionesRefund

GET /transaction
GET /transaction/{id}
POST /transaction
PUT /transaction/{id}
DELETE /transaction/{id}

GET /transactionEdit
GET /transactionEdit/{id}
POST /transactionEdit
PUT /transactionEdit/{id}
DELETE /transactionEdit/{id}

GET /transactionRefund
GET /transactionRefund/{id}
POST /transactionRefund
PUT /transactionRefund/{id}
DELETE /transactionRefund/{id}

* Nota: Se adjunta Collection de Postman con Endpoints listos para su ejecución 

Tests
Para ejecutar los tests, usa el siguiente comando:

Licencia
Este proyecto está licenciado bajo la Licencia MIT.

Este archivo `README.md` proporciona una descripción básica del proyecto, los requisitos, las instrucciones de configuración y ejecución, los endpoints disponibles y cómo ejecutar los tests.
