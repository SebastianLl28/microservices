# ==================== MULTI-STAGE BUILD ====================

# Etapa 1: Build
FROM maven:3.9-eclipse-temurin-17-alpine AS build

WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY src ./src

# Construir el proyecto
RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Crear usuario no privilegiado
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiar el JAR construido
COPY --from=build /app/target/*.jar app.jar

# Exponer puerto específico del dispatcher
EXPOSE 8082

# Configuración de JVM
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Punto de entrada
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
