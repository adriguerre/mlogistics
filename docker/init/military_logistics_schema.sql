-- =============================================================
--  MILITARY LOGISTICS DATABASE SCHEMA
--  Engine: PostgreSQL 15+
-- =============================================================

-- ─────────────────────────────────────────────
--  ENUMS
-- ─────────────────────────────────────────────
CREATE TYPE base_status           AS ENUM ('ACTIVE', 'INACTIVE', 'DECOMMISSIONED');
CREATE TYPE unit_type             AS ENUM ('SQUAD', 'PLATOON', 'COMPANY', 'BATTALION', 'BRIGADE', 'DIVISION');
CREATE TYPE branch_type           AS ENUM ('ARMY', 'NAVY', 'AIR_FORCE', 'MARINES', 'SPECIAL_FORCES');
CREATE TYPE personnel_status      AS ENUM ('ACTIVE', 'ON_LEAVE', 'DEPLOYED', 'WOUNDED', 'KIA', 'DISCHARGED');
CREATE TYPE vehicle_type          AS ENUM ('TRUCK', 'HUMVEE', 'APC', 'HELICOPTER', 'CARGO_PLANE', 'SHIP', 'DRONE');
CREATE TYPE vehicle_status        AS ENUM ('AVAILABLE', 'IN_MISSION', 'MAINTENANCE', 'DECOMMISSIONED');
CREATE TYPE equip_classification  AS ENUM ('UNCLASSIFIED', 'CONFIDENTIAL', 'SECRET', 'TOP_SECRET');
CREATE TYPE supplier_type         AS ENUM ('GOVERNMENT', 'CONTRACTOR', 'ALLIED_NATION', 'INTERNAL');
CREATE TYPE order_status          AS ENUM ('DRAFT', 'PENDING_APPROVAL', 'APPROVED', 'REJECTED', 'IN_TRANSIT', 'DELIVERED', 'CANCELLED');
CREATE TYPE order_priority        AS ENUM ('ROUTINE', 'URGENT', 'CRITICAL', 'EMERGENCY');
CREATE TYPE shipment_status       AS ENUM ('PREPARING', 'IN_TRANSIT', 'DELAYED', 'DELIVERED', 'LOST');
CREATE TYPE item_condition        AS ENUM ('NEW', 'GOOD', 'FAIR', 'DAMAGED', 'DESTROYED');
CREATE TYPE mission_classification AS ENUM ('UNCLASSIFIED', 'CONFIDENTIAL', 'SECRET', 'TOP_SECRET');
CREATE TYPE mission_status        AS ENUM ('PLANNING', 'ACTIVE', 'SUSPENDED', 'COMPLETED', 'ABORTED');
CREATE TYPE resource_status       AS ENUM ('ALLOCATED', 'DEPLOYED', 'RETURNED', 'LOST');
CREATE TYPE maintenance_type      AS ENUM ('PREVENTIVE', 'CORRECTIVE', 'EMERGENCY', 'INSPECTION');
CREATE TYPE maintenance_status    AS ENUM ('SCHEDULED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED');
CREATE TYPE squad_type            AS ENUM ('INFANTRY', 'ENGINEERS', 'MARINES', 'SPECIAL_FORCES', 'LOGISTICS', 'MEDICAL', 'ARTILLERY', 'RECONNAISSANCE');

-- =============================================================
--  DOMAIN: ORGANISATION
-- =============================================================

CREATE TABLE rank (
    id          UUID          PRIMARY KEY DEFAULT gen_random_uuid(),
    code        VARCHAR(20)   NOT NULL UNIQUE,
    title       VARCHAR(100)  NOT NULL,
    branch      branch_type   NOT NULL,
    precedence  SMALLINT      NOT NULL CHECK (precedence > 0),
    created_at  TIMESTAMPTZ   NOT NULL DEFAULT now()
);

CREATE TABLE base (
    id                  UUID          PRIMARY KEY DEFAULT gen_random_uuid(),
    code                VARCHAR(20)   NOT NULL UNIQUE,
    name                VARCHAR(150)  NOT NULL,
    country             VARCHAR(100)  NOT NULL,
    region              VARCHAR(100),
    latitude            DOUBLE PRECISION,
    longitude           DOUBLE PRECISION,
    status              base_status   NOT NULL DEFAULT 'ACTIVE',
    commanding_unit_id  UUID,                          -- FK añadida tras crear unit
    equipment_slots     INT           NOT NULL DEFAULT 0 CHECK (equipment_slots >= 0),
    fuel_capacity_l     INT           NOT NULL DEFAULT 0 CHECK (fuel_capacity_l  >= 0),
    created_at          TIMESTAMPTZ   NOT NULL DEFAULT now(),
    updated_at          TIMESTAMPTZ   NOT NULL DEFAULT now()
);

CREATE TABLE unit (
    id              UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    code            VARCHAR(30)  NOT NULL UNIQUE,
    name            VARCHAR(150) NOT NULL,
    type            unit_type    NOT NULL,
    size_headcount  INT          CHECK (size_headcount >= 0),
    parent_unit_id  UUID         REFERENCES unit(id) ON DELETE SET NULL,
    home_base_id    UUID         NOT NULL REFERENCES base(id),
    commander_id    UUID,                              -- FK añadida tras crear personnel
    created_at      TIMESTAMPTZ  NOT NULL DEFAULT now()
);

-- Back-fill FK en base una vez que unit existe
ALTER TABLE base
    ADD CONSTRAINT fk_base_commanding_unit
    FOREIGN KEY (commanding_unit_id) REFERENCES unit(id) ON DELETE SET NULL;

CREATE TABLE personnel (
    id          UUID              PRIMARY KEY DEFAULT gen_random_uuid(),
    service_id  VARCHAR(50)       NOT NULL UNIQUE,
    first_name  VARCHAR(100)      NOT NULL,
    last_name   VARCHAR(100)      NOT NULL,
    rank_id     UUID              NOT NULL REFERENCES rank(id),
    unit_id     UUID              REFERENCES unit(id) ON DELETE SET NULL,
    base_id     UUID              REFERENCES base(id) ON DELETE SET NULL,
    status      personnel_status  NOT NULL DEFAULT 'ACTIVE',
    created_at  TIMESTAMPTZ       NOT NULL DEFAULT now()
);

-- Back-fill FK en unit una vez que personnel existe
ALTER TABLE unit
    ADD CONSTRAINT fk_unit_commander
    FOREIGN KEY (commander_id) REFERENCES personnel(id) ON DELETE SET NULL;

CREATE TABLE vehicle (
    id                  UUID            PRIMARY KEY DEFAULT gen_random_uuid(),
    plate               VARCHAR(50)     NOT NULL UNIQUE,
    model               VARCHAR(150)    NOT NULL,
    type                vehicle_type    NOT NULL,
    base_id             UUID            REFERENCES base(id) ON DELETE SET NULL,
    unit_id             UUID            REFERENCES unit(id) ON DELETE SET NULL,
    max_payload_kg      NUMERIC(10,2)   CHECK (max_payload_kg > 0),
    status              vehicle_status  NOT NULL DEFAULT 'AVAILABLE',
    current_base_id     UUID            REFERENCES base(id) ON DELETE SET NULL,
    passenger_capacity  INT             CHECK (passenger_capacity >= 0),
    created_at          TIMESTAMPTZ     NOT NULL DEFAULT now()
);

-- =============================================================
--  DOMAIN: EQUIPMENT & INVENTORY
-- =============================================================

CREATE TABLE equipment_category (
    id          UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    code        VARCHAR(30)  NOT NULL UNIQUE,
    name        VARCHAR(100) NOT NULL,
    description TEXT
);

CREATE TABLE equipment (
    id                        UUID                 PRIMARY KEY DEFAULT gen_random_uuid(),
    sku                       VARCHAR(60)          NOT NULL UNIQUE,
    name                      VARCHAR(150)         NOT NULL,
    model                     VARCHAR(150),
    manufacturer              VARCHAR(150),
    category_id               UUID                 NOT NULL REFERENCES equipment_category(id),
    unit_weight_kg            NUMERIC(10,3)        CHECK (unit_weight_kg >= 0),
    unit_cost_usd             NUMERIC(14,2)        CHECK (unit_cost_usd >= 0),
    classification            equip_classification NOT NULL DEFAULT 'UNCLASSIFIED',
    requires_maintenance      BOOLEAN              NOT NULL DEFAULT FALSE,
    maintenance_interval_days INT                  CHECK (maintenance_interval_days > 0),
    created_at                TIMESTAMPTZ          NOT NULL DEFAULT now()
);

CREATE TABLE inventory (
    id                 UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    equipment_id       UUID        NOT NULL REFERENCES equipment(id),
    base_id            UUID        REFERENCES base(id) ON DELETE SET NULL,
    unit_id            UUID        REFERENCES unit(id) ON DELETE SET NULL,
    qty_total          INT         NOT NULL DEFAULT 0 CHECK (qty_total >= 0),
    qty_available      INT         NOT NULL DEFAULT 0 CHECK (qty_available >= 0),
    qty_reserved       INT         NOT NULL DEFAULT 0 CHECK (qty_reserved >= 0),
    qty_damaged        INT         NOT NULL DEFAULT 0 CHECK (qty_damaged >= 0),
    reorder_threshold  INT         NOT NULL DEFAULT 0 CHECK (reorder_threshold >= 0),
    updated_at         TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uq_inventory_location UNIQUE (equipment_id, base_id, unit_id)
);

-- =============================================================
--  DOMAIN: SUPPLY CHAIN
-- =============================================================

CREATE TABLE supplier (
    id          UUID          PRIMARY KEY DEFAULT gen_random_uuid(),
    code        VARCHAR(30)   NOT NULL UNIQUE,
    name        VARCHAR(150)  NOT NULL,
    type        supplier_type NOT NULL,
    country     VARCHAR(100),
    is_approved BOOLEAN       NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMPTZ   NOT NULL DEFAULT now()
);

CREATE TABLE supply_order (
    id                   UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    order_number         VARCHAR(60)    NOT NULL UNIQUE,
    requesting_unit_id   UUID           REFERENCES unit(id) ON DELETE SET NULL,
    requesting_base_id   UUID           REFERENCES base(id) ON DELETE SET NULL,
    supplier_id          UUID           NOT NULL REFERENCES supplier(id),
    approved_by          UUID           REFERENCES personnel(id) ON DELETE SET NULL,
    status               order_status   NOT NULL DEFAULT 'DRAFT',
    priority             order_priority NOT NULL DEFAULT 'ROUTINE',
    required_by          DATE,
    total_cost_usd       NUMERIC(16,2)  CHECK (total_cost_usd >= 0),
    notes                TEXT,
    created_at           TIMESTAMPTZ    NOT NULL DEFAULT now()
);

CREATE TABLE supply_order_item (
    id               UUID          PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id         UUID          NOT NULL REFERENCES supply_order(id) ON DELETE CASCADE,
    equipment_id     UUID          NOT NULL REFERENCES equipment(id),
    qty_requested    INT           NOT NULL CHECK (qty_requested > 0),
    qty_approved     INT                    CHECK (qty_approved >= 0),
    unit_price_usd   NUMERIC(14,2)          CHECK (unit_price_usd >= 0)
);

-- =============================================================
--  DOMAIN: TRANSPORT & SHIPMENTS
-- =============================================================

CREATE TABLE shipment (
    id                   UUID             PRIMARY KEY DEFAULT gen_random_uuid(),
    tracking_code        VARCHAR(80)      NOT NULL UNIQUE,
    order_id             UUID             REFERENCES supply_order(id) ON DELETE SET NULL,
    origin_base_id       UUID             REFERENCES base(id) ON DELETE SET NULL,
    destination_base_id  UUID             REFERENCES base(id) ON DELETE SET NULL,
    vehicle_id           UUID             REFERENCES vehicle(id) ON DELETE SET NULL,
    driver_id            UUID             REFERENCES personnel(id) ON DELETE SET NULL,
    status               shipment_status  NOT NULL DEFAULT 'PREPARING',
    total_weight_kg      NUMERIC(12,2)    CHECK (total_weight_kg >= 0),
    dispatched_at        TIMESTAMPTZ,
    estimated_arrival_at TIMESTAMPTZ,
    delivered_at         TIMESTAMPTZ,
    created_at           TIMESTAMPTZ      NOT NULL DEFAULT now()
);

CREATE TABLE shipment_item (
    id            UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    shipment_id   UUID           NOT NULL REFERENCES shipment(id) ON DELETE CASCADE,
    equipment_id  UUID           NOT NULL REFERENCES equipment(id),
    quantity      INT            NOT NULL CHECK (quantity > 0),
    condition     item_condition NOT NULL DEFAULT 'NEW'
);

-- =============================================================
--  DOMAIN: OPERATIONS / MISSIONS
-- =============================================================

CREATE TABLE mission (
    id                  UUID                   PRIMARY KEY DEFAULT gen_random_uuid(),
    codename            VARCHAR(100)           NOT NULL UNIQUE,
    commanding_unit_id  UUID                   REFERENCES unit(id) ON DELETE SET NULL,
    base_id             UUID                   REFERENCES base(id) ON DELETE SET NULL,
    classification      mission_classification NOT NULL DEFAULT 'CONFIDENTIAL',
    status              mission_status         NOT NULL DEFAULT 'PLANNING',
    target_latitude     DOUBLE PRECISION,
    target_longitude    DOUBLE PRECISION,
    start_at            TIMESTAMPTZ,
    end_at              TIMESTAMPTZ,
    objective           TEXT,
    created_at          TIMESTAMPTZ            NOT NULL DEFAULT now()
);

CREATE TABLE mission_resource (
    id            UUID            PRIMARY KEY DEFAULT gen_random_uuid(),
    mission_id    UUID            NOT NULL REFERENCES mission(id) ON DELETE CASCADE,
    unit_id       UUID            REFERENCES unit(id) ON DELETE SET NULL,
    equipment_id  UUID            REFERENCES equipment(id) ON DELETE SET NULL,
    vehicle_id    UUID            REFERENCES vehicle(id) ON DELETE SET NULL,
    quantity      INT             NOT NULL DEFAULT 1 CHECK (quantity > 0),
    status        resource_status NOT NULL DEFAULT 'ALLOCATED'
);

-- =============================================================
--  DOMAIN: MAINTENANCE
-- =============================================================

CREATE TABLE maintenance_record (
    id            UUID               PRIMARY KEY DEFAULT gen_random_uuid(),
    equipment_id  UUID               REFERENCES equipment(id) ON DELETE SET NULL,
    vehicle_id    UUID               REFERENCES vehicle(id) ON DELETE SET NULL,
    performed_by  UUID               REFERENCES personnel(id) ON DELETE SET NULL,
    base_id       UUID               REFERENCES base(id) ON DELETE SET NULL,
    type          maintenance_type   NOT NULL,
    status        maintenance_status NOT NULL DEFAULT 'SCHEDULED',
    description   TEXT,
    cost_usd      NUMERIC(12,2)      CHECK (cost_usd >= 0),
    outcome       TEXT,
    scheduled_at  TIMESTAMPTZ        NOT NULL,
    completed_at  TIMESTAMPTZ,
    CONSTRAINT chk_maintenance_target CHECK (
        equipment_id IS NOT NULL OR vehicle_id IS NOT NULL
    )
);

-- =============================================================
--  DOMAIN: SQUADS
-- =============================================================

-- Un squad es una unidad táctica completa (Rangers, Ingenieros, etc.)
-- Puede estar en una base, en un vehículo (en tránsito), o en campo abierto.
-- Nunca puede estar en base y en vehículo al mismo tiempo.
CREATE TABLE squad (
    id                  UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    name                VARCHAR(150) NOT NULL,
    type                squad_type   NOT NULL,
    unit_id             UUID         REFERENCES unit(id) ON DELETE SET NULL,
    personnel_count     INT          NOT NULL CHECK (personnel_count > 0),
    current_base_id     UUID         REFERENCES base(id) ON DELETE SET NULL,
    current_vehicle_id  UUID         REFERENCES vehicle(id) ON DELETE SET NULL,
    created_at          TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at          TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT chk_squad_location CHECK (
        NOT (current_base_id IS NOT NULL AND current_vehicle_id IS NOT NULL)
    )
);

-- =============================================================
--  INDEXES
-- =============================================================
CREATE INDEX idx_unit_home_base         ON unit(home_base_id);
CREATE INDEX idx_personnel_unit         ON personnel(unit_id);
CREATE INDEX idx_personnel_rank         ON personnel(rank_id);
CREATE INDEX idx_vehicle_base           ON vehicle(base_id);
CREATE INDEX idx_vehicle_current_base   ON vehicle(current_base_id);
CREATE INDEX idx_inventory_equipment    ON inventory(equipment_id);
CREATE INDEX idx_inventory_base         ON inventory(base_id);
CREATE INDEX idx_supply_order_status    ON supply_order(status);
CREATE INDEX idx_supply_order_supplier  ON supply_order(supplier_id);
CREATE INDEX idx_shipment_status        ON shipment(status);
CREATE INDEX idx_shipment_order         ON shipment(order_id);
CREATE INDEX idx_mission_status         ON mission(status);
CREATE INDEX idx_mission_resource       ON mission_resource(mission_id);
CREATE INDEX idx_maintenance_status     ON maintenance_record(status);
CREATE INDEX idx_maintenance_equip      ON maintenance_record(equipment_id);
CREATE INDEX idx_maintenance_vehicle    ON maintenance_record(vehicle_id);
CREATE INDEX idx_squad_unit             ON squad(unit_id);
CREATE INDEX idx_squad_current_base     ON squad(current_base_id);

-- ─── Kafka Events ───────────────────────────────────────────────────────────
CREATE TABLE kafka_events (
    id         uuid           DEFAULT gen_random_uuid() NOT NULL,
    event_type VARCHAR(10)    NOT NULL,
    message    character(150) NOT NULL,
    date       timestamp      NOT NULL,
    CONSTRAINT kafka_events_pkey PRIMARY KEY (id)
);
