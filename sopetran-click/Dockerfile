# Etapa de construcción (Build)
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
# Dar permisos de ejecución al wrapper de maven
RUN chmod +x ./mvnw
# Compilar el proyecto saltando las pruebas
RUN ./mvnw clean package -DskipTests

# Etapa de ejecución (Run)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copiar el jar compilado de la etapa anterior
COPY --from=build /app/target/*.jar app.jar
# Exponer el puerto
EXPOSE 10000
# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
