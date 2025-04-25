# API Reactiva con Kafka

Este proyecto es un ejemplo educativo que combina una API REST reactiva con integración a Apache Kafka. Está diseñado para enseñar conceptos básicos de Kafka y cómo integrarlo en una aplicación basada en Spring Boot.

---

## **Características del Proyecto**

1. **CRUD de Productos**:
   - Operaciones para crear, leer, actualizar y eliminar productos.
   - Persistencia en MongoDB utilizando Spring Data Reactive MongoDB.

2. **Integración con Kafka**:
   - Productor para enviar mensajes a un tópico de Kafka.
   - Consumidor para recibir y procesar mensajes desde Kafka.

3. **Caso de Uso Real**:
   - Actualización de inventario con envío de eventos a Kafka.
   - Ejemplo práctico de cómo Kafka puede ser utilizado en aplicaciones del mundo real.

---

## **Requisitos Previos**

- **Docker**: Para ejecutar Kafka, Zookeeper y MongoDB.
- **Java 17**: Para ejecutar la aplicación Spring Boot.
- **Maven**: Para gestionar dependencias y construir el proyecto.

---

## **Configuración del Entorno**

1. **Clonar el Repositorio**:
   ```bash
   git clone <URL_DEL_REPOSITORIO>
   cd api-reactiva-kafka
   ```

2. **Levantar los Servicios con Docker**:
   ```bash
   docker compose up -d
   ```
   Esto iniciará los contenedores de Kafka, Zookeeper y MongoDB.

3. **Ejecutar la Aplicación**:
   ```bash
   ./mvnw spring-boot:run
   ```

---

## **Endpoints Disponibles**

### **CRUD de Productos**
- **GET /items**: Obtiene todos los productos.
- **GET /items/{id}**: Obtiene un producto por su ID.
- **POST /items**: Crea un nuevo producto.
- **PUT /items/{id}**: Actualiza un producto existente.
- **DELETE /items/{id}**: Elimina un producto por su ID.

### **Kafka**
- **POST /items/kafka/send**: Envía un mensaje al tópico de Kafka.
- **PATCH /items/{id}/inventory**: Actualiza el inventario de un producto y envía un evento a Kafka.

---

## **Conceptos Básicos de Kafka**

### **¿Qué es Kafka?**
Apache Kafka es una plataforma de mensajería distribuida diseñada para manejar flujos de datos en tiempo real. Es ampliamente utilizada para construir sistemas de procesamiento de eventos y análisis en tiempo real.

### **Componentes Clave**
1. **Tópicos**:
   - Los datos en Kafka se organizan en tópicos.
   - Un tópico es similar a una cola donde los productores envían mensajes y los consumidores los leen.

2. **Productores**:
   - Aplicaciones que envían mensajes a los tópicos.

3. **Consumidores**:
   - Aplicaciones que leen mensajes de los tópicos.

4. **Brokers**:
   - Servidores que almacenan los mensajes y gestionan los tópicos.

5. **Grupos de Consumidores**:
   - Los consumidores pueden agruparse para procesar mensajes en paralelo.

### **Ventajas de Kafka**
- **Procesamiento en Tiempo Real**: Ideal para sistemas que requieren análisis o monitoreo en tiempo real.
- **Escalabilidad**: Kafka puede manejar grandes volúmenes de datos y escalar horizontalmente.
- **Persistencia**: Los mensajes se almacenan en disco, lo que permite procesarlos más tarde si es necesario.
- **Desacoplamiento**: Kafka actúa como un intermediario entre productores y consumidores, permitiendo que trabajen de manera independiente.

---

## **Caso de Uso: Actualización de Inventario**

Cuando se actualiza el inventario de un producto, se envía un evento a Kafka con los detalles del cambio. Este evento puede ser consumido por otros servicios para realizar tareas adicionales, como:
- Enviar notificaciones.
- Actualizar dashboards en tiempo real.
- Generar reportes de inventario.

Ejemplo de evento enviado a Kafka:
```json
{
  "productId": "12345",
  "productName": "Producto A",
  "oldQuantity": 100,
  "newQuantity": 50,
  "updatedAt": "2025-04-25T12:55:03.403389"
}
```

---

## **Próximos Pasos**

1. **Extender la Funcionalidad**:
   - Agregar más consumidores para procesar eventos de diferentes maneras.
   - Integrar Kafka con sistemas de análisis como Apache Spark.

2. **Monitoreo**:
   - Usar herramientas como Kafka Manager o Prometheus para monitorear el rendimiento.

3. **Explorar Particiones**:
   - Configurar múltiples particiones en los tópicos para mejorar la paralelización.

---

¡Disfruta explorando las capacidades de Kafka con este proyecto educativo! Si tienes preguntas o necesitas ayuda, no dudes en contactarme.
