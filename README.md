# API de empleados — TechNova, S.A.

Proyecto Spring Boot de la guía *Creación de endpoints y respuestas JSON con Spring Boot*.
Almacenamiento en memoria (sin base de datos) para concentrarse en endpoints, DTO y JSON.

## Requisitos

- JDK 21
- Maven (o el que trae IntelliJ IDEA)

Si usa otra versión de JDK, cambie `<java.version>` en el `pom.xml`.

## Cómo abrirlo en IntelliJ IDEA

1. Descomprima el archivo.
2. **File → Open** y seleccione la carpeta `api-empleados` (no un archivo suelto).
3. Espere a que Maven descargue las dependencias.
4. Ejecute `ApiEmpleadosApplication`.

Desde la terminal: `mvn spring-boot:run`

La API queda en `http://localhost:8080/api/empleados`.

## Estructura de paquetes

```
ni.edu.uam.api_empleados
├── ApiEmpleadosApplication.java   Clase principal
├── controllers                    Rutas y solicitudes HTTP
│   └── EmpleadoController.java
├── dto                            Datos que entran y salen de la API
│   └── EmpleadoDTO.java
├── services                       Lógica de gestión de empleados
│   └── EmpleadoService.java
└── exceptions                     Errores y respuestas JSON
    └── GlobalExceptionHandler.java
```

## Endpoints

| Método | Ruta                  | Acción     | Respuesta esperada        |
|--------|-----------------------|------------|---------------------------|
| GET    | /api/empleados        | Listar     | 200 OK                    |
| GET    | /api/empleados/{id}   | Buscar     | 200 OK o 404 Not Found    |
| POST   | /api/empleados        | Registrar  | 201 Created o 400         |
| PUT    | /api/empleados/{id}   | Actualizar | 200 OK, 400 o 404         |
| DELETE | /api/empleados/{id}   | Eliminar   | 200 OK o 404 Not Found    |

## JSON válido para POST y PUT

```json
{
  "nombres": "Ana María",
  "apellidos": "López Pérez",
  "cargo": "Analista de sistemas",
  "salario": 18500.00
}
```

## JSON de prueba de validación (responde 400)

```json
{
  "nombres": "",
  "apellidos": "Pérez",
  "cargo": "",
  "salario": 0
}
```

Falla porque `nombres` y `cargo` están vacíos (`@NotBlank`, y además incumplen el mínimo de
`@Size`) y porque `salario` es 0, mientras `@DecimalMin(inclusive = false)` exige un valor
mayor que 0. `@Valid` detiene la petición antes de llegar al servicio y
`GlobalExceptionHandler` arma el JSON con el detalle campo por campo.

## Pruebas rápidas con curl

```bash
curl -X POST http://localhost:8080/api/empleados \
  -H "Content-Type: application/json" \
  -d '{"nombres":"Ana María","apellidos":"López Pérez","cargo":"Analista de sistemas","salario":18500.00}'

curl http://localhost:8080/api/empleados
curl http://localhost:8080/api/empleados/1
curl -X DELETE http://localhost:8080/api/empleados/1
```

En `postman/api-empleados.postman_collection.json` hay una colección lista para importar en
Postman con las siete pruebas (incluidos los casos 400 y 404).
