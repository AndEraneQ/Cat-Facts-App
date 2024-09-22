#!/bin/bash

cleanup() {
  echo "Stopping applications..."
  sleep 2

  for pid in $(ps aux | grep '[j]ava' | awk '{print $2}'); do
    echo "Stopping Java process with PID: $pid"
    kill "$pid"
  done

  for pid in $(ps aux | grep '[n]pm' | awk '{print $2}'); do
    echo "Stopping NPM process with PID: $pid"
    kill "$pid"
  done

  sleep 2
  exit 0
}

trap cleanup EXIT

echo "Starting Spring Boot application..."
(cd server && ./mvnw spring-boot:run) &
sleep 10

echo "Starting React application..."
(cd client && npm run dev) &
sleep 5

echo "Opening browser..."
start http://localhost:5173

wait
