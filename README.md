# mlogistics

Military logistics management API built with Spring Boot. Designed to track and manage the operational resources of a military force: bases, units, personnel, vehicles, equipment, supply orders, shipments, missions, and maintenance records.

> 🚧 Work in progress: This project is currently under active development. Endpoints, data models and features may change at any time. New functionality will be added progressively.

## Tech stack

| Layer | Technology |
|---|---|
| Backend | Spring Boot 4.0 / Java 21 |
| Database | PostgreSQL 15 |
| ORM | Spring Data JPA (Hibernate) |
| Messaging | Apache Kafka 4.2 (KRaft mode) |
| Observability | Datadog Agent v7 + JMXFetch |
| Containers | Docker / Docker Compose |

## Domain

17 entities organized around the core logistics workflow:

```
Rank → Personnel → Unit → Base
                        ↓
              Vehicle  Squad  Equipment
                        ↓
         SupplyOrder → SupplyOrderItem
         Shipment    → ShipmentItem
         Mission     → MissionResource
                        MaintenanceRecord
```

- `Base` tracks capacity via two dimensions: `equipment_slots` (discrete items) and `fuel_capacity_l` (liters of JP-8/diesel)
- `Squad` can be located at a base (`current_base_id`), inside a vehicle in transit (`current_vehicle_id`), or in the field (both null) — enforced with a CHECK constraint

## Kafka

Kafka runs in **KRaft mode** (no Zookeeper) with two listeners:

| Listener | Address | Purpose |
|---|---|---|
| `PLAINTEXT` | `kafka:9092` | Internal Docker-to-Docker traffic |
| `PLAINTEXT_HOST` | `localhost:29092` | External clients / local development |

> **Work in progress** — event publishing for domain actions (shipment dispatched, squad embarked, supply order status changes, etc.) is being incrementally added.

### Datadog integration

The Datadog Agent is deployed as a sidecar container using the `latest-jmx` image. It collects Kafka broker metrics via **JMX over RMI** (port `9999`) and forwards them to the Datadog platform.

Metrics collected include:
- Broker throughput — `bytes_in_per_sec`, `bytes_out_per_sec`
- Message rates — `messages_in_per_sec`
- Replication lag — `under_replicated_partitions`
- Consumer group lag
- JVM heap, GC pause times, thread counts

Configuration: [`datadog/conf.d/kafka.d/`](datadog/conf.d/kafka.d/)

## Getting started

### Prerequisites

- Docker & Docker Compose

### Environment variables

### Run

Under normal circumstances the project connects to a real external database. However, for the purpose of showcasing the full behaviour of the API, a snapshot of the database at a specific point in time has been included in the repository (`docker/init/init.sql`). This allows anyone to run the project locally with realistic data without needing access to the original database.

```bash
docker compose up --build -d
```

Services started:
- `postgres` → `localhost:5432`
- `kafka` → `localhost:29092`
- `datadog-agent` (metrics forwarded to Datadog)


### API

Base URL: `http://localhost:8080/mlogistics/api/v1`

Import [`docs/mlogistics_postman_collection.json`](docs/mlogistics_postman_collection.json) into Postman — all entity IDs from the sample data are pre-loaded as collection variables.

## Project structure

```
src/main/java/com/logistics/mlogistics/
├── controllers/   REST controllers (one per entity)
├── domain/        JPA entities + enums
├── repository/    Spring Data repositories
└── service/       Business logic + partial update pattern

datadog/
└── conf.d/kafka.d/   JMX metric collection config
```

## Kafka — Inventory Low Stock Alert

When an inventory update causes `qty_available` to drop below `reorder_threshold`, a `LowStockEvent` is automatically published to the Kafka topic `inventory.low-stock`. A consumer picks it up and logs a `WARN` alert, visible in Datadog Logs Explorer.

**To test:**
1. `docker compose up -d`
2. `PUT /inventory/{id}` with `qty_available` below `reorder_threshold`
3. Check logs: `docker compose logs -f app`

**Expected logs:**

INFO  [KAFKA-EVENT] Low stock event sent — inventory=... equipment='Assault Rifle' qty=20 threshold=50

WARN  [ALERT KAFKA-EVENT] Low stock detected — equipment='Assault Rifle' at base='Fort Alpha' | available=20 / threshold=50
