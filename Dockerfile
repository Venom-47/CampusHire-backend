# Step 1: Lightweight Java 25 Runtime
FROM eclipse-temurin:25-jre

WORKDIR /app

# Step 2: Copy the compiled JAR (using wildcard *.jar)
COPY target/*.jar app.jar

# Step 3: Expose Spring Boot port
EXPOSE 8080

# Step 4: Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]