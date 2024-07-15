# Use the official Keycloak image as the base image
FROM quay.io/keycloak/keycloak:latest as builder

# Switch to root user to ensure we have permissions to change the script
USER root

# Copy the realm configuration file to the container
COPY realm-export.json /opt/keycloak/data/import/realm-export.json

# Switch back to the keycloak user
USER keycloak

# Set environment variables for Keycloak configuration
ENV KEYCLOAK_ADMIN=admin
ENV KEYCLOAK_ADMIN_PASSWORD=admin
ENV KEYCLOAK_IMPORT=/opt/keycloak/data/import/realm-export.json

# Use the default entrypoint to start Keycloak
ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start-dev", "--import-realm"]
