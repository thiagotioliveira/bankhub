FROM amazoncorretto:21-alpine-jdk

# Definir diretório de trabalho
WORKDIR /app

# Copiar o arquivo JAR para o contêiner
COPY api/target/*.jar app.jar

# Expor a porta 8080
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]