-- Vehicle Renting System - PostgreSQL Schema

CREATE TABLE IF NOT EXISTS users (
    id          VARCHAR(50) PRIMARY KEY,
    username    VARCHAR(100) UNIQUE NOT NULL,
    password    VARCHAR(255) NOT NULL,
    role        VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'CUSTOMER'))
);

CREATE TABLE IF NOT EXISTS customers (
    id      VARCHAR(50) PRIMARY KEY,
    name    VARCHAR(200) NOT NULL,
    email   VARCHAR(200) NOT NULL,
    phone   VARCHAR(50) NOT NULL,
    CONSTRAINT fk_customer_user FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS vehicles (
    id              VARCHAR(50) PRIMARY KEY,
    type            VARCHAR(20) NOT NULL CHECK (type IN ('Car', 'Bike')),
    model           VARCHAR(200) NOT NULL,
    base_price      DECIMAL(10, 2) NOT NULL,
    has_sunroof     BOOLEAN NOT NULL DEFAULT FALSE,
    has_bluetooth   BOOLEAN NOT NULL DEFAULT FALSE,
    has_helmet      BOOLEAN NOT NULL DEFAULT FALSE,
    has_carrier     BOOLEAN NOT NULL DEFAULT FALSE,
    has_gps         BOOLEAN NOT NULL DEFAULT FALSE,
    has_insurance   BOOLEAN NOT NULL DEFAULT FALSE,
    has_child_seat  BOOLEAN NOT NULL DEFAULT FALSE,
    status          VARCHAR(30) NOT NULL DEFAULT 'Available'
        CHECK (status IN ('Available', 'Booked', 'UnderMaintenance'))
);

CREATE TABLE IF NOT EXISTS bookings (
    booking_id   VARCHAR(50) PRIMARY KEY,
    customer_id  VARCHAR(50) NOT NULL REFERENCES customers(id),
    vehicle_id   VARCHAR(50) NOT NULL REFERENCES vehicles(id),
    total_amount DECIMAL(10, 2) NOT NULL,
    days         INTEGER NOT NULL,
    late_fee     DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    status       VARCHAR(20) NOT NULL CHECK (status IN ('CONFIRMED', 'CANCELLED', 'COMPLETED')),
    created_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_bookings_customer ON bookings(customer_id);
CREATE INDEX IF NOT EXISTS idx_bookings_vehicle ON bookings(vehicle_id);
CREATE INDEX IF NOT EXISTS idx_vehicles_status ON vehicles(status);

-- Default admin (password: admin123)
INSERT INTO users (id, username, password, role)
VALUES ('ADM001', 'admin', 'admin123', 'ADMIN')
ON CONFLICT (username) DO NOTHING;

-- Demo vehicles
INSERT INTO vehicles (id, type, model, base_price, has_sunroof, has_bluetooth, has_helmet, has_carrier, has_gps, has_insurance, has_child_seat, status)
VALUES ('CAR001', 'Car', 'Toyota Corolla', 50.0, TRUE, TRUE, FALSE, FALSE, TRUE, TRUE, FALSE, 'Available')
ON CONFLICT (id) DO NOTHING;

INSERT INTO vehicles (id, type, model, base_price, has_sunroof, has_bluetooth, has_helmet, has_carrier, has_gps, has_insurance, has_child_seat, status)
VALUES ('CAR002', 'Car', 'BMW X5', 120.0, TRUE, TRUE, FALSE, FALSE, TRUE, TRUE, TRUE, 'Available')
ON CONFLICT (id) DO NOTHING;

INSERT INTO vehicles (id, type, model, base_price, has_sunroof, has_bluetooth, has_helmet, has_carrier, has_gps, has_insurance, has_child_seat, status)
VALUES ('BIKE001', 'Bike', 'Honda CB500', 20.0, FALSE, FALSE, TRUE, TRUE, TRUE, FALSE, FALSE, 'Available')
ON CONFLICT (id) DO NOTHING;
