# Используйте официальный образ OpenJDK 21 в качестве базового для сборки
FROM openjdk:21-jdk AS builder
# Установите рабочую директорию
WORKDIR /build
# Скопируйте Maven Wrapper
COPY mvnw .
COPY .mvn .mvn
# Скопируйте файлы проекта
COPY pom.xml .
COPY src ./src
# Дайте права на выполнение для mvnw
RUN chmod +x mvnw
# Соберите проект и создайте JAR файл (оптимизировано: отключаем тесты, если они не нужны на этом этапе)
RUN ./mvnw clean package -DskipTests

# Используйте минимальный образ для выполнения
FROM openjdk:21-jdk-slim
# Установите рабочую директорию
WORKDIR /app
# Скопируйте JAR файл из предыдущего этапа
COPY --from=builder /build/target/*.jar app.jar
# Включите метаданные для образа (рекомендуется)
LABEL org.opencontainers.image.source=https://github.com/finedefinition/nyb-project
# Установите точку входа для выполнения JAR файла
ENTRYPOINT ["java", "-jar", "/app/app.jar"]