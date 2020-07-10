#!/bin/bash

DOCKER_BUILDKIT=1 docker build -t repayment-service:1.0 .

docker run -it repayment-service:1.0 /bin/bash
