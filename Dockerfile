FROM ghcr.io/iwa-consolti/iwa-distroless-java-17-base:1.0.2

WORKDIR /app

COPY target/demostripe-api.jar /app/demostripe-api.jar

CMD ["/app/demostripe-api.jar"]

EXPOSE 8081