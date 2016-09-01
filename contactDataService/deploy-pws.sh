#!/bin/bash
CF_APP='contactDataService'
CF_APPS_DOMAIN='cfapps.io'
CF_ROUTE_NAME=$CF_APP

mvn clean install package
if [ "$?" -ne "0" ]; then
  exit $?
fi

#DEPLOYED_VERSION_CMD=$(CF_COLOR=false cf a | grep $CF_APP- | cut -d" " -f1| cut -d"-" -f2)
DEPLOYED_VERSION_CMD=$(CF_COLOR=false cf routes | awk -v ROUTE_NAME="$CF_ROUTE_NAME" '$2 ~ ROUTE_NAME { print $4; }' | cut -d"," -f1 | cut -d"-" -f2)

DEPLOYED_VERSION_CMD=$(CF_COLOR=false cf routes | grep $CF_APP | awk '{ print $4; }' | cut -d"," -f1 | cut -d"-" -f2)
DEPLOYED_VERSION="$DEPLOYED_VERSION_CMD"
echo "Deployed Version: $DEPLOYED_VERSION"
CURRENT_VERSION="blue"
if [ ! -z "$DEPLOYED_VERSION" -a "$DEPLOYED_VERSION" == "blue" ]; then
  CURRENT_VERSION="green"
fi
# push a new version and map the route
cf cs cleardb spark p-mysql
cf cs cloudamqp lemur p-rabbitmq
cf p "$CF_APP-$CURRENT_VERSION"
cf map-route "$CF_APP-$CURRENT_VERSION" $CF_APPS_DOMAIN -n $CF_ROUTE_NAME
if [ ! -z "$DEPLOYED_VERSION" ]; then
  # Unmap the route and delete
  cf unmap-route "$CF_APP-$DEPLOYED_VERSION" $CF_APPS_DOMAIN -n $CF_ROUTE_NAME
  # Scaling down
  cf scale "$CF_APP-$DEPLOYED_VERSION" -i 1
fi
app_fqdn=`cf app "$CF_APP-$CURRENT_VERSION" | awk '/urls: / {print $2}' | sed 's/,//g'`
csJSONStr={\"tag\":\"contact-service\",\"uri\":\"$app_fqdn\"}
echo \"$csJSONStr\"

cf cups contact-service -p \"$csJSONStr\"
if [ "$?" -ne "0" ]; then
  cf uups contact-service -p \"$csJSONStr\"
  if [ "$?" -ne "0" ]; then
    exit $?
  fi
fi
