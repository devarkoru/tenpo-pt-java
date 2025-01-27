# Imagen base de Java
FROM eclipse-temurin:21-jdk

# Crear un directorio para la aplicación
WORKDIR /app

# Copiar el JAR generado al contenedor
COPY build/libs/tenpo-pt-docker-backend-1.0.0.jar app.jar

# Exponer el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
