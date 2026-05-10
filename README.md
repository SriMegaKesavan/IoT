# 🌡️ IoT Device Monitoring Dashboard + AI

End-to-end IoT platform built with Spring Boot, MQTT, React, and local LLM integration.

## What it does
- Registers IoT devices via MQTT
- Ingests real-time telemetry (temperature, humidity, battery)
- Stores data in PostgreSQL
- Serves REST API for the React dashboard
- AI chatbot answers questions about device health using Ollama/phi3

## Tech Stack
Spring Boot 3.x · MQTT (Eclipse Mosquitto) · PostgreSQL · Docker · React 18 · Ollama (phi3) · Spring AI · JWT Auth

## Architecture
IoT Device → MQTT Broker → Spring Boot Backend → PostgreSQL → REST API → React Dashboard → AI Chatbot

## How to run
docker run -d --name mqtt-broker -p 1883:1883 eclipse-mosquitto
docker run -d --name iot-postgres -e POSTGRES_DB=iot_db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 postgres:16
# Then run the Spring Boot backend and React frontend
