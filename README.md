## Repayment Schedule Service

A basic service to generate repayment plans.

ps: There is no documentation on this code because I consider a a better approach to put the api spec.
in a external project following the open api approach.

## How to run this service:

This is a simples java application. You can run it on a Docker container (recommended) or directly from your environment
This are the steps for each option:

### Docker
The easy way to run this application is running in a docker container.
for that you just need to have docker installed and execute the `start_docker_script.sh` file.

### Maven
If you do not have docker, you can use maven instead. You will needs the java 10 version (or superior)  installed.
I am not using any plugin to put all dependencies on the same .jar, so it is necessary to put all dependencies together.

Follow the steps for each environment:

#### linux/mac you can run:
run the script: `start_local_mvn_script.sh` or type

    > mvn install
    > java -jar target/repayment-schedule-service-1.0-SNAPSHOT.jar


### Endpoint 

POST /generate-plan - generate repayment plan
     
    > curl --location --request POST 'localhost:8080/generate-plan' \
      --header 'Content-Type: application/json' \
      --data-raw '{
         "loanAmount" : "5000",
        "nominalRate" : "5.0",
        "duration" : 24,
        "startDate" : "2018-01-01T00:00:01Z"
     }
    