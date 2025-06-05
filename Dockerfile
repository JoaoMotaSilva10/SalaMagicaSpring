# ---- Fase de Build ----
FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /app
COPY . .

# Dá permissão de execução ao mvnw e instala dependências Maven
RUN chmod +x mvnw && \
    ./mvnw dependency:go-offline -B && \
    ./mvnw clean package -DskipTests

# ---- Fase Final ----
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Otimizações para o Render (plano Free)
ENV JAVA_OPTS="-Xmx256m -Xss512k -XX:MaxRAMPercentage=75"
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]