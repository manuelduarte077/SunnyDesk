# SunnyDesk

Una aplicación de pronóstico del clima desarrollada con Kotlin y Compose Desktop, implementando Clean Architecture.

## Arquitectura

El proyecto sigue los principios de Clean Architecture, dividiendo la aplicación en tres capas principales:

### Capa de Dominio (Domain)
- Contiene las entidades de negocio, casos de uso e interfaces de repositorio
- Independiente de frameworks y librerías externas
- Define las reglas de negocio y contratos

### Capa de Datos (Data)
- Implementa las interfaces de repositorio definidas en la capa de dominio
- Gestiona las fuentes de datos (API, base de datos, etc.)
- Contiene mappers para convertir DTOs a entidades de dominio

### Capa de Presentación (Presentation)
- Contiene los componentes de UI y ViewModels
- Implementa la lógica de presentación y manejo de estados
- Utiliza Jetpack Compose para construir la interfaz de usuario

## Características

- Búsqueda de clima por ciudad
- Visualización del clima actual
- Pronóstico para los próximos 5 días
- Interfaz de usuario moderna y responsiva

## Tecnologías

- Kotlin
- Compose Desktop
- Coroutines
- ViewModel
- Ktor Client

## Cómo ejecutar

1. Clona el repositorio
2. Abre el proyecto en IntelliJ IDEA
3. Ejecuta la tarea `run` de Gradle

## API

La aplicación utiliza [WeatherAPI](https://www.weatherapi.com/) para obtener datos meteorológicos.
