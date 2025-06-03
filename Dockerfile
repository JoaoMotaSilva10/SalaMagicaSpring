# ---- Fase de Build ----
FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /app
COPY . .

# Build do projeto com Maven (ignora testes para acelerar)
RUN ./mvnw clean package -DskipTests

# ---- Fase Final ----
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Variáveis para otimizar memória (crucial para o plano Free do Render)
ENV JAVA_OPTS="-Xmx256m -Xss512k -XX:CICompilerCount=2"

# Porta exposta (Render usa a variável $PORT)
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]