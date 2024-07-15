#!/bin/bash

# Start Keycloak server in the background
/opt/keycloak/bin/kc.sh start-dev &

# Wait for the Keycloak server to be up and running
until $(curl --output /dev/null --silent --head --fail http://localhost:8080/health); do
    printf '.'
    sleep 5
done

# Run the CLI script to create the realm, roles, and users
/opt/keycloak/scripts/create-realm.sh

# Keep the container running
wait
