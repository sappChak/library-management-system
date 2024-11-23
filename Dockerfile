# Use a lightweight JDK base image for both build and runtime
FROM eclipse-temurin:17-jdk-alpine AS build

# Set the working directory
WORKDIR /app

# Copy only files needed for dependency resolution first (to leverage caching)
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Ensure Gradle wrapper is executable
RUN chmod +x gradlew

# Download dependencies (caching this step improves build speed)
RUN ./gradlew --no-daemon dependencies

# Copy the source code after dependencies are resolved
COPY src src

# Build the application
RUN ./gradlew clean build --no-daemon

# Final stage: Use a minimal JRE image for runtime
FROM eclipse-temurin:17-jre-alpine

# Set the working directory
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

