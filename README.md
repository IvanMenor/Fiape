# FIAPE - Backend API

Backend desarrollado con **Java 21**, **Spring Boot** y **MySQL** para el proyecto **FIAPE**.

## Tecnologías

- Java 21
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- MySQL
- Maven
- Swagger / OpenAPI

---

# Requisitos

Antes de ejecutar el proyecto asegúrate de tener instalado:

- Java 21
- Maven
- MySQL 8+
- IntelliJ IDEA (opcional)

---

# Configuración de la base de datos

1. Crear una base de datos llamada:

```sql
CREATE DATABASE fiape;
```

2. Configurar las credenciales en `application.properties`.

Ejemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fiape?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASEÑA
```

> Si tu MySQL utiliza otro puerto (por ejemplo 3307), recuerda modificar la URL.

---

# Ejecutar el proyecto

Desde IntelliJ:

Ejecutar la clase:

```
DemoApplication.java
```

o desde consola:

```bash
mvn spring-boot:run
```

La API quedará disponible en:

```
http://localhost:8080
```

---

# Documentación Swagger

Una vez iniciada la aplicación, la documentación puede visualizarse en:

```
http://localhost:8080/swagger-ui/swagger-ui/index.html
```

Desde Swagger podrán:

- Consultar todos los endpoints.
- Probar las peticiones.
- Autenticarse mediante JWT cuando sea necesario.

---

# Usuarios de prueba

Se han creado **2 usuarios** para realizar pruebas.

Un ROLE_USER y un ROLE_ADMIN

Las credenciales se encuentran documentadas en el apartado **Schemas** de Swagger.

> Revisar el schema correspondiente para obtener el correo/usuario y contraseña de cada uno.

---

# Autenticación

El proyecto utiliza:

- Spring Security
- JWT (JSON Web Token)

Para consumir los endpoints protegidos:

1. Iniciar sesión.
2. Copiar el token JWT obtenido.
3. Presionar el botón **Authorize** en Swagger.
4. Pegar el token con el formato:

```
Bearer TU_TOKEN
```

---

# Estructura del proyecto

```
src
├── main
│   ├── java
│   │   └── com.example.demo
│   │       ├── config
│   │       ├── controller
│   │       ├── entity
│   │       ├── repository
│   │       ├── service
│   │       └── security
│   └── resources
│       ├── application.properties
│       └── ...
```

---

# Notas

- La API utiliza autenticación JWT para los endpoints protegidos.
- Swagger se encuentra habilitado para facilitar las pruebas de los servicios.
- Verificar que MySQL esté ejecutándose antes de iniciar el proyecto.

- La API utiliza autenticación JWT para los endpoints protegidos.
- Swagger se encuentra habilitado para facilitar las pruebas de los servicios.
- Verificar que MySQL esté ejecutándose antes de iniciar el proyecto.
