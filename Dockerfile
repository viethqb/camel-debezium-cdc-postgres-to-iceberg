FROM eclipse-temurin:17-jdk-alpine as build

WORKDIR /app
COPY . .
#RUN mvn clean package -Dcheckstyle.skip -DskipTests
RUN --mount=type=cache,target=/root/.m2 ./mvnw install -DskipTests

FROM eclipse-temurin:17-jdk-alpine

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
WORKDIR /app
COPY --from=build /app/target .

ENTRYPOINT ["java", "-jar", "/app/debeziumspostgres-0.0.1-SNAPSHOT.jar"]