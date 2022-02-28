FROM alpine:3.15
RUN apk add --no-cache openjdk17
COPY build/libs/ecommerce-0.0.2.jar /app/
WORKDIR /app/
ENTRYPOINT ["java"]
CMD ["-jar", "/app/ecommerce-0.0.2.jar"]
