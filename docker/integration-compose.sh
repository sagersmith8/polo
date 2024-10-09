#!/bin/bash

dir=$(dirname "$0")

compose_files=(-f "$dir"/docker-compose.yml)
docker compose "${compose_files[@]}" "$@"
