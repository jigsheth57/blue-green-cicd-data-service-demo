#!/usr/bin/env bash
set -e

rabbitmq-server start -detached

# wait for rabbitmq server to start up
sleep 20

rabbitmqctl status

cd lab-repo/contactDataService

mvn clean test -Djava.version=1.7
