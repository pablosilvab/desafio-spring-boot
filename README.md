# **API de Gestión de Tareas**

Este proyecto es una **API REST** para la gestión de tareas, desarrollada con **Spring Boot** y **Java 17**.

## 🚀 **Tecnologías Utilizadas**

- **Java 17**
- **Spring Boot 3.4.3**
- **Spring Data JPA** (para la interacción con la base de datos)
- **MapStruct 1.6.0** (para mapeo de objetos de entidad a DTO y viceversa)
- **Open Api Generator 7.12.0** (para la generación automática de código a partir de la especificación OpenAPI)
- **Lombok** (para reducir código repetitivo)
- **Swagger** (para la documentación de la API)

## 📌 **Características**

Esta API proporciona los siguientes endpoints:

| Método | Endpoint      | Descripción              |  
|--------|---------------|--------------------------|  
| POST   | `/login`      | Autenticarse             |  
| GET    | `/tasks`      | Obtener todas las tareas |  
| GET    | `/tasks/{id}` | Obtener una tarea por ID |  
| POST   | `/tasks`      | Crear una nueva tarea    |  
| PUT    | `/tasks/{id}` | Actualizar una tarea     |  
| DELETE | `/tasks/{id}` | Eliminar una tarea       |  

## 🛠️ **Configuración y Uso**

### 1️⃣ Clonar el repositorio
```bash
git clone https://github.com/pablosilvab/desafio-spring-boot.git
cd desafio-spring-boot
```

### 2️⃣ Generar código de OpenAPI y MapStruct
```bash
mvn clean compile
```

### 3️⃣ Ejecutar con Maven
```bash
mvn spring-boot:run
```

## 📄 Documentación de la API

Puedes explorar y probar la API utilizando las siguientes herramientas:

### Swagger UI
Accede a la documentación interactiva generada automáticamente por Swagger:
```bash
http://localhost:8080/swagger-ui/index.html
```
### Postman
Para probar la API en Postman, puedes importar la colección desde el siguiente enlace:[Documentación en Postman](https://documenter.getpostman.com/view/1979123/2sAYkBthJ3)
o importar la colección `desafio-spring-boot.postman_collection.json` manualmente. 

