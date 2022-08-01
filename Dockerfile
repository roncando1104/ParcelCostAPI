FROM openjdk:8
ADD target/parcel-cost-api-1.0.0.jar parcel-cost-api-1.0.0.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "parcel-cost-api-1.0.0.jar"]