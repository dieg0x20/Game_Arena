# Fase de build com Maven
FROM maven:3.9.4-eclipse-temurin-21-alpine AS builder
WORKDIR /backend_gamearena
COPY . .
RUN mvn clean package -DskipTests

# Fase de execução com o JRE
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copia o JAR gerado na fase de build para a fase de execução
COPY --from=builder /backend_gamearena/target/gamearena-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta 8080 para acesso
EXPOSE 8080

# Comando de inicialização da aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]