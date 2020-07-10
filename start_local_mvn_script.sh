#!/bin/bash

mvn clean install

java -jar target/repayment-schedule-service-1.0-SNAPSHOT.jar
