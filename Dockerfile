FROM ghcr.io/iwa-consolti/iwa-distroless-java-17-base:1.0.2

WORKDIR /app

COPY target/gymki-api.jar /app/gymki-api.jar

CMD ["/app/gymki-api.jar"]

EXPOSE 8081