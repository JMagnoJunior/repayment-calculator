#!/bin/bash

mvn dependency:copy-dependencies

mvn install

java -cp target/repayment-schedule-service-1.0-SNAPSHOT.jar:target/dependency/* com.magnojr.repaymentscheduleservice.RepaymentScheduleServiceApplication
