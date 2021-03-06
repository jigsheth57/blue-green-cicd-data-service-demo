#!/bin/bash

set -e

# login to PWS
echo "Login to PCF ....."

cf login -a $API_ENDPOINT -u $USERNAME -p $PASSWORD -o $ORG -s $SPACE --skip-ssl-validation

# DEPLOYED_VERSION_CMD=$(CF_COLOR=false cf a | grep $CF_APP_NAME- | cut -d" " -f1| cut -d"-" -f2)
DEPLOYED_VERSION_CMD=$(CF_COLOR=false cf routes | grep $CF_ROUTE_NAME | awk '{ print $4; }' | cut -d"," -f1 | cut -d"-" -f2)
DEPLOYED_VERSION="$DEPLOYED_VERSION_CMD"
echo "Deployed Version: $DEPLOYED_VERSION"
CURRENT_VERSION="blue"
if [[ ! -z "$DEPLOYED_VERSION" ]] && [[ "$DEPLOYED_VERSION" == *"blue"* ]]; then
  CURRENT_VERSION="green"
fi
echo "New Version: $CURRENT_VERSION"
# push a new version and map the route
# cf cs cleardb spark p-mysql
# cf cs cloudamqp lemur p-rabbitmq
cf cs p-mysql 100mb p-mysql
cf cs p-rabbitmq standard p-rabbitmq
cf p "$CF_APP_NAME-$CURRENT_VERSION" -p target/contactDataService-0.0.1-SNAPSHOT.jar -f manifest-2.yml
cf map-route "$CF_APP_NAME-$CURRENT_VERSION" $CF_APPS_DOMAIN -n $CF_ROUTE_NAME
if [[ ! -z "$DEPLOYED_VERSION" ]]; then
  # Unmap the route and delete
  cf unmap-route "$CF_APP_NAME-$DEPLOYED_VERSION" $CF_APPS_DOMAIN -n $CF_ROUTE_NAME
  # Scaling down
  cf scale "$CF_APP_NAME-$DEPLOYED_VERSION" -i 1
fi
