# AppChat

Aplicación de escritorio de mensajería local desarrollada en Java con interfaz gráfica (Window Builder), gestión de usuarios y contactos, persistencia de datos, y exportación de conversaciones a PDF.

## Objetivo y contexto

Este trabajo se realizó como un proyecto académico en una asignatura de ingeniería informática. El proyecto se desarrolló para poner en práctica la separación de responsabilidades y la aplicación de patrones de diseño en una aplicación con interfaz gráfica y almacenamiento persistente.

El trabajo abarcó el modelado de requisitos, la implementación de la interfaz, la lógica de negocio, la integración con el servicio de persistencia y la elaboración de documentación técnica y de usuario.

## Alcance de la aplicación

AppChat se ejecuta localmente. Dentro del alcance de esta práctica, la gestión de usuarios, contactos y mensajes se realiza en el entorno local de la aplicación. No se implementa comunicación de mensajería entre equipos ni una arquitectura cliente-servidor con varios clientes conectados simultáneamente. La separación entre presentación, lógica de negocio y persistencia es una separación lógica de responsabilidades dentro de la aplicación.

Estas limitaciones se establecieron por parte de la asignatura para cumplir los límites de tiempo del proyecto. Estos aspectos se estudiaron y se desarrollaron en proyectos de otras asignaturas de la carrera.

## Funcionalidades
- Registro e inicio de sesión de usuarios.
- Gestión de contactos y grupos de destinatarios.
- Envío y consulta de mensajes de texto y emoticonos.
- Búsqueda de mensajes mediante filtros combinables.
- Gestión de la modalidad Premium y descuentos.
- Exportación de conversaciones a PDF para usuarios Premium.

## Tecnologías

- Lenguaje de programación: Java
- Interfaz gráfica: Window Builder
- Persistencia de datos: Se usa un servicio de persistencia que crearon los profesores de la asignatura.
- Gestión de dependencias: Maven
- Control de versiones: Git (GitHub)
- Entorno de desarrollo: Eclipse IDE

## Arquitectura

La arquitectura de la aplicación se organiza según un modelo de tres capas: vista, modelo, y persistencia. Además, incluye un controlador que separa la vista del modelo.

- **Capa del modelo**: Contiene las clases relacionadas con la lógica del negocio. Las clases de esta capa solo interactúan entre sí, ofreciendo métodos para que otras capas puedan acceder. No accede ni conoce al resto de capas.
- **Capa de presentación** (vista): Se encarga de la entrada/salida, es decir, muestra las ventanas y captura los eventos para reaccionar en base a estos. Sus clases acceden a objetos del modelo, pero únicamente para acceder a atributos de este, si necesita hacer acciones más complejas (como atributos de atributos, o filtrar), o necesita hacer alguna acción que es responsabilidad del modelo, llama a un método del controlador para que este se encargue.
- **Capa persistencia**: Utiliza el patrón DAO para desacoplar la capa de almacenamiento del resto de la aplicación. Las clases tienen acceso a los objetos del dominio que almacenan, ya que deben acceder a sus atributos para almacenarlos en la base de datos; y crear los objetos al sacarlos de la base de datos.
- **Controlador**: Actúa de intermediario entre las capas del modelo y la presentación. Además, se encarga de utilizar la capa de persistencia para persistir los objetos del dominio. Por último, guarda el estado de sesión, conteniendo un usuario actual cuando este se ha logeado.

En una interacción típica del sistema, el usuario interactúa con la capa de la vista, que delega en el controlador la acción necesaria (ya sea enviar un mensaje, registrar un usuario, hacer login, ...) para que este interactúe con el dominio para realizarla; interactuando también con la capa de persistencia si se ha modificado o creado un objeto que se deba almacenar.

## Patrones de diseño utilizados

- **DAO**: Usado para desacoplar la aplicación del sistema de almacenamiento persistente utilizado.
- **Singleton**: Se usa en el controlador, el repositorio de usuarios, y la clase que exporta los chats a PDF, para garantizar que solo exista una instancia de estas clases. También se usa para la factoría y adaptadores de persistencia (patrón DAO).
- **Builder**: Para el diseño del generador de PDF, se ha utilizado el patrón Builder porque es común en aplicaciones que generan documentos que se pueda elegir el formato de salida, y aunque de momento sólo se genera PDFs, podría ampliarse a otros formatos en el futuro.
- **Adaptador**: Se usa para utilizar el servicio IText, convirtiendo su interfaz a la interfaz que la aplicación necesita para generar PDFs.
- **Fachada**: Se usa en el controlador
- **Estrategia**: Se usa para aplicar distintos tipos de descuento a la modalidad Premium.
- **Null Object**: Se usa para representar la ausencia de descuento en la modalidad Premium.
- **Patrones GRASP**: Se han aplicado los principios GRASP de Experto, Creador y Controlador.

## Información del proyecto

Asignatura: Práctica de Tecnologías de Desarrollo de Software (TDS)

Curso: 2024-2025

Carrera: Ingeniería Informática

Nota obtenida en la asignatura: 10/10 (matrícula de honor)