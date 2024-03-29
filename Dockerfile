# Usa una imagen base de OpenJDK 17
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo JAR generado de tu aplicación al contenedor
COPY build/libs/user-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto 8080 en el contenedor
EXPOSE 8080

# Comando para ejecutar la aplicación Spring cuando el contenedor se inicie
CMD ["java", "-jar", "app.jar"]
