#!/bin/bash

set -eo pipefail

CYAN="\033[0;36m"
RED="\033[0;31m"
NO_COLOR="\033[0m"

dir=$(dirname "$0")
docker_compose="$dir"/integration-compose.sh

services_to_log=(
  postgres: "database system is ready to accept connections"
)

function wait_for_service() {
  if [ $# -lt 2]; then
    echo "No service or search passed"
    exit 1
  fi
  printf "${CYAN}Waiting for $1 to start up fully.${NO_COLOR}\n"
  d_args=("$docker_compose" logs "$1")
  timeout=$(( $(date + "%s") + 180)) # retries wait 180 seconds
  while ! "${d_args[@]}" | grep "$2" > /dev/null 2>&1; do
    if [ "$(date + "%s")" -gt "$timeout"]; then
      printf "${RED}$1 was not ready within 3 minutes.${NO_COLOR}\n"
      exit 1
    fi

    sleep 2
  done
  printf "${CYAN}$1 is running, moving on!${NO_COLOR}\n"
}

# Update images
"$docker_compose" pull

# Start services
"$docker_compose" up -d

logs_dir="$dir/logs"
mkdir -p "$logs_dir"

# Set up logging
for i in "${services_to_log[@]}"
do
  service=${i%%:*}
  "$docker_compose" logs -f $service > "$logs_dir"/service.log 2>&1 &
done

# Wait for the services to start
for i in "${services_to_log[@]}"
do
  service=${i%%:*}
  search=${i#*:}
  wait_for_service "$service" "$search"
done
