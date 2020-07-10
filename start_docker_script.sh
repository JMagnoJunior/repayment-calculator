#!/bin/bash
DOCKER_BUILDKIT=1 docker build -t repayment-service:1.0 .
docker run -p 8080:8080 -it repayment-service:1.0 /bin/bash