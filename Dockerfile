# ---------- Estágio 1: build com Maven ----------
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build
# Copia o pom.xml primeiro para aproveitar o cache das dependências
COPY pom.xml .
RUN mvn -B dependency:go-offline
# Copia o código-fonte e gera o .jar
COPY src ./src
RUN mvn -B clean package -DskipTests

# ---------- Estágio 2: imagem final (somente JRE) ----------
# Usa uma imagem oficial do Java 17 (leve) como base
FROM eclipse-temurin:17-jre-alpine
# Define o diretório de trabalho dentro do container
WORKDIR /app
# Copia o .jar gerado no estágio de build (nome definido pelo pom.xml: artifactId-version.jar)
COPY --from=build /build/target/dsc-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
# Comando que será executado quando o container iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]
