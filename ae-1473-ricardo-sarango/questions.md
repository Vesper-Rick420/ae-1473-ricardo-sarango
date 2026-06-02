# Preguntas — Arquitectura en Capas

## 1. ¿Qué es un controlador y de qué se encarga?
Un controlador es la capa que recibe los requests HTTP del cliente. Se encarga de extraer los datos del request (por ejemplo, el body con `@RequestBody`), delegar el trabajo al servicio y devolver la respuesta al cliente. No contiene lógica de negocio.

## 2. ¿Qué responsabilidad tiene la capa de servicio?
La capa de servicio contiene la lógica de negocio de la aplicación. Es la que decide qué hacer con los datos: normalizar texto, calcular valores derivados, ordenar resultados, mapear entre entidades y DTOs, etc. También orquesta las llamadas al repositorio.

## 3. ¿Qué hace el repositorio y de qué se encarga?
El repositorio es la capa que se comunica directamente con la base de datos. Se encarga de guardar, buscar, actualizar y eliminar registros. En Spring Data JPA basta con declarar una interfaz que extiende `JpaRepository` y el framework provee la implementación automáticamente.

## 4. ¿Qué es una entidad y a qué se mapea en la base de datos?
Una entidad es una clase anotada con `@Entity` que representa una tabla en la base de datos. Cada instancia de la clase corresponde a una fila de esa tabla, y cada campo de la clase corresponde a una columna.

## 5. ¿Para qué sirve un DTO y por qué no devolvemos la entidad directamente?
Un DTO (Data Transfer Object) es un objeto que define exactamente qué datos entran o salen de la API. No se devuelve la entidad directamente para no exponer detalles internos del modelo de base de datos, para poder incluir campos calculados (como `slug` o `finalPrice`) y para tener control total sobre el contrato de la API.

## 6. ¿Cuál es la diferencia entre un `Request` y un `Response`?
Un `Request` representa los datos que llegan desde el cliente en el body de la petición HTTP. Un `Response` representa los datos que la API devuelve al cliente. El `Response` puede incluir campos que no existen en el `Request`, como el `id` generado por la base de datos o valores calculados.

## 7. ¿Por qué separamos la aplicación en capas? Menciona una ventaja.
Separamos la aplicación en capas para que cada parte tenga una única responsabilidad (principio de responsabilidad única). Una ventaja clara es el mantenimiento: si cambia la lógica de negocio, solo se modifica el servicio sin tocar el controlador ni el repositorio.

## 8. ¿Qué anotación se usa para marcar un controlador REST? ¿Y un servicio?
Para un controlador REST se usa `@RestController`. Para un servicio se usa `@Service`. Ambas hacen que Spring registre esas clases como beans y las gestione en su contenedor de inyección de dependencias.

## 9. ¿Qué hace `@RequestBody` en un endpoint?
`@RequestBody` le indica a Spring que debe leer el cuerpo (body) del request HTTP, deserializarlo desde JSON al tipo de objeto indicado en el parámetro del método, y pasarlo como argumento al controlador.

## 10. ¿Cuál es el flujo que sigue un request desde que llega hasta que se guarda en la base de datos?
1. El cliente envía un `POST` con un JSON en el body.
2. El **Controlador** recibe el request, deserializa el body a un `BookRequest` gracias a `@RequestBody` y llama al servicio.
3. El **Servicio** normaliza los datos, construye la entidad `Book` y llama a `repository.save()`.
4. El **Repositorio** persiste la entidad en la base de datos H2 y devuelve la entidad con el `id` generado.
5. El **Servicio** mapea la entidad a un `BookResponse` (calculando `slug` y `finalPrice`) y lo retorna al controlador.
6. El **Controlador** devuelve el `BookResponse` serializado como JSON al cliente con status `200 OK`.
