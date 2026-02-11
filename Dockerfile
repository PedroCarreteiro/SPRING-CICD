#BUILD
#Usar imagem que tem o Maven instalado para compilar o projeto
FROM maven:3.9.6-eclipse-temurin-17 AS build

#Definir a pasta de trabalho dentro do container temporário
WORKDIR /app

#Copiar o arquivo de dependências e baixar elas
COPY pom.xml .
RUN mvn dependency:go-offline

#Copiar o resto do código fonte
COPY src ./src

#Compila o projeto e gera o arquivo .jar
RUN mvn clean package -DskipTests

#RUNTIME
#Usar uma imagem leve apenas com o Java (sem Maven) para rodar
FROM eclipse-temurin:17-jdk-alpine

#Definir diretório
WORKDIR /app

#Copiar só o arquivo .jar gerado no estágio anterior
COPY --from=build /app/target/*.jar app.jar

#Expor a porta
EXPOSE 8080

#Comando que roda quando o container inicia
ENTRYPOINT ["java", "-jar", "app.jar"]