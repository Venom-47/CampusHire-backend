# Step 1: Lightweight Java 25 Runtime
FROM eclipse-temurin:25-jre

WORKDIR /app

# Step 2: Copy the compiled JAR
COPY target/CampusPlacmentTracker-0.0.1-SNAPSHOT.jar app.jar

# Step 3: Expose Spring Boot's port
EXPOSE 8080

# Step 4: Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]