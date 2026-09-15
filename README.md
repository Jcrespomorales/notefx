# NoteFX

Aplicación de escritorio desarrollada en JavaFX para la creación y gestión de notas en Markdown con renderizado en tiempo real.

## 🚀 Sobre el proyecto

NoteFX es una aplicación diseñada para ofrecer una experiencia de escritura fluida en Markdown, permitiendo visualizar instantáneamente el resultado renderizado mientras se edita el contenido.

Este proyecto fue desarrollado con el objetivo de profundizar en el desarrollo de aplicaciones de escritorio utilizando JavaFX, aplicar una arquitectura en capas y trabajar con persistencia local mediante SQLite.

## ✨ Características

- Creación, edición y eliminación de notas.
- Almacenamiento persistente mediante SQLite.
- Editor Markdown integrado.
- Renderizado en tiempo real utilizando WebView.
- Gestión manual del guardado para un mayor control sobre los cambios.
- Interfaz adaptable con distintos modos de visualización:
  - Editor expandido.
  - Vista previa expandida.
  - Vista dividida 50/50.

## 🖼️ Capturas

<img width="1287" height="893" alt="captura-notefx" src="https://github.com/user-attachments/assets/6fad9660-00b6-4367-bc0d-093c02ace946" />


---


El proyecto sigue una arquitectura por capas para mantener una clara separación de responsabilidades.

```text
src/main/java
│
├── main
├── controller
├── service
├── model
├── repository
├── dao
└── database
```

### Responsabilidades principales

- **Controller:** gestión de eventos e interacción con la interfaz.
- **Service:** lógica de negocio.
- **Repository:** contratos de acceso a datos.
- **DAO:** implementación de persistencia.
- **Model:** entidades de dominio.
- **DBConnection:** configuración y acceso a SQLite.

## 🛠️ Tecnologías utilizadas

- Java
- JavaFX
- FXML
- Maven
- SQLite
- JUnit 5
- Markdown
- WebView

## 🧪 Testing

Las pruebas unitarias están enfocadas en la lógica de negocio y componentes independientes de la interfaz gráfica.

Pruebas implementadas:

- `MarkdownService.toHtml()`
- `NoteService.crearNote()`
- `NoteService.eliminarPorId()`

## 📚 Aspectos de aprendizaje

Durante el desarrollo de este proyecto trabajé en:

- Diseño de interfaces de escritorio con JavaFX.
- Aplicación de arquitectura por capas.
- Persistencia de datos con SQLite.
- Renderizado dinámico de contenido Markdown.
- Testing de servicios y lógica de negocio.
- Gestión de dependencias con Maven.

## ▶️ Ejecución

### Requisitos

- Java 21
- Maven

### Clonar el repositorio

```bash
git clone https://github.com/Jcrespomorales/notefx.git
```

### Ejecutar

```bash
mvn clean javafx:run
```

### Generar JAR

```bash
mvn clean package
```

## 👨‍💻 Autor

Desarrollado como proyecto personal para seguir mejorando habilidades en desarrollo de software con Java, JavaFX y arquitectura de aplicaciones de escritorio.
