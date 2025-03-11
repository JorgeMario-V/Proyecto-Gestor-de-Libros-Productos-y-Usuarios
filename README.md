# 📦 Proyecto Spring Boot WebFlux con Seguridad, Internacionalización, API REST y Pruebas Automatizadas

Este proyecto implementa una aplicación backend moderna usando **Spring Boot 3.4.3**, **Spring Security**, **Spring WebFlux (Programación Reactiva)**, **Internacionalización (i18n)** y **Pruebas Automatizadas con JUnit + WebTestClient**.

---

## 🚀 Tecnologías utilizadas

- ✅ Spring Boot 3.4.3
- ✅ Spring WebFlux
- ✅ Spring Security
- ✅ Internacionalización (i18n)
- ✅ Maven
- ✅ Java 23
- ✅ JUnit 5 + WebTestClient

---

## 📂 Estructura del proyecto

demo/ ├── src/ │ ├── main/ │ │ ├── java/com/example/demo/ │ │ │ ├── controller/ │ │ │ ├── model/ │ │ │ ├── service/ │ │ │ ├── config/ │ │ │ └── DemoApplication.java │ │ └── resources/ │ │ ├── application.properties │ │ ├── messages.properties │ │ └── messages_en.properties │ └── test/ │ └── java/com/example/demo/ │ └── tests (controladores y servicios)


---

## 🌍 Internacionalización

- `messages.properties` → Español por defecto
- `messages_en.properties` → Inglés
- Se configura con `Accept-Language` en las peticiones HTTP (ej: `Accept-Language: en` o `es`).

---

## 🔐 Seguridad

Autenticación básica HTTP con Spring Security:
- Usuario: `admin`
- Clave: `admin`

Endpoints protegidos con roles y configuración por `SecurityConfig.java`.

---

## 🔗 Endpoints principales

/api/productos
Entonces, los endpoints válidos son:

Método	URL	Descripción
GET	http://localhost:8080/api/productos	Listar todos los productos
GET	http://localhost:8080/api/productos/1	Obtener producto por ID (ejemplo: "1")
POST	http://localhost:8080/api/productos	Crear nuevo producto
PUT	http://localhost:8080/api/productos/{id}	Actualizar un producto existente
DELETE	http://localhost:8080/api/productos/{id}	Eliminar un producto

(Idem para `/usuarios` y `/libros`).

---

## ✅ Pruebas automatizadas

Se incluyen pruebas con **JUnit 5** y **WebTestClient** para validar los controladores y servicios de forma reactiva.

Ubicación: `src/test/java/com/example/demo/`

---

## ▶️ Cómo ejecutar

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/JorgeMario-V/Proyecto-Gestor-de-Libros-Productos-y-Usuarios.git
   cd demo
 EFDHDSFHDSHF