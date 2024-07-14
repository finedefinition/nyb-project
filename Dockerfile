# Используйте официальный образ OpenJDK 17 в качестве базового
FROM openjdk:17-jdk-alpine

# Установите рабочую директорию
WORKDIR /app

# Скопируйте JAR файл вашего приложения в контейнер
COPY target/*.jar app.jar

# Установите точку входа для выполнения JAR файла
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
