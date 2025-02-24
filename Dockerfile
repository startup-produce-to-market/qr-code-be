FROM openjdk:17-alpine

# Add app user
ARG APPLICATION_USER=appuser
RUN adduser --no-create-home -u 1000 -D $APPLICATION_USER

# Configure working directory
RUN mkdir /app && \
    chown -R $APPLICATION_USER /app

USER 1000

# Confgiure environment variables
ENV QR_GENERATOR_CLIENT_KEY=$QR_GENERATOR_CLIENT_KEY
ENV QR_GENERATOR_CLIENT_SECRET=$QR_GENERATOR_CLIENT_SECRET
ENV QR_STORAGE_AUTH_FILE=$QR_STORAGE_AUTH_FILE

COPY --chown=1000:1000 ./target/qr-code-1.0-SNAPSHOT.jar /app/app.jar

WORKDIR /app

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/app/app.jar" ]
