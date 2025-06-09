# -------- Build Stage --------
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

ARG SPRING_PROFILES_ACTIVE

# Copy everything and build with Maven
# COPY . .

COPY pom.xml .
COPY mvnw .
COPY .mvn/ .mvn/

RUN ./mvnw dependency:go-offline -B

COPY src ./src

RUN ./mvnw clean package -DskipTests -P ${SPRING_PROFILES_ACTIVE}

# -------- Runtime Stage (JRE only) --------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy only the built jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
