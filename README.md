# User Service

El Servicio de Usuarios es una aplicación basada en Spring Boot que proporciona funcionalidades relacionadas con la gestión de usuarios. Permite a los usuarios crear cuentas, recuperar información de usuarios existentes, seguir a otros usuarios y obtener una lista paginada de todos los usuarios.

Funcionalidades

Crear Usuario: Los usuarios pueden enviar solicitudes POST para crear nuevas cuentas de usuario proporcionando la información necesaria, como nombre de usuario, correo electrónico y contraseña.
Obtener Todos los Usuarios: Los usuarios pueden enviar solicitudes GET para obtener una lista paginada de todos los usuarios registrados en el sistema.
Obtener Usuario por ID: Los usuarios pueden enviar solicitudes GET para obtener información detallada de un usuario específico utilizando su ID.
Seguir a Otro Usuario: Los usuarios pueden enviar solicitudes POST para seguir a otros usuarios proporcionando el ID del usuario a seguir.

## Requisitos Previos

Asegúrate de tener instalado Java 17 en tu sistema. Puedes descargarlo desde [aquí](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html).

## Configuración

Antes de ejecutar el servicio, asegúrate de configurar las propiedades necesarias en el archivo `application.properties`

spring.datasource.url=jdbc:mysql://localhost:3306/ualaDB
spring.datasource.username=admin
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.data.cache.type=redis
spring.data.redis.host=redis
spring.data.redis.port=6379

## Crear imagen docker

```
docker build -t user_service:latest .
```

## Ejecución

```dtd
./gradlew bootRun
```
