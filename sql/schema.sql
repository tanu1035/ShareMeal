CREATE DATABASE IF NOT EXISTS sharemeal;
USE sharemeal;

CREATE TABLE users (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    email         VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role          VARCHAR(10)  NOT NULL,          -- DONOR / NGO / ADMIN
    phone         VARCHAR(15),
    address       VARCHAR(255),
    active        BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at    DATETIME     NOT NULL
);

CREATE TABLE donations (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    donor_id       BIGINT       NOT NULL,
    food_name      VARCHAR(100) NOT NULL,
    category       VARCHAR(50),
    quantity       INT          NOT NULL,
    unit           VARCHAR(20)  NOT NULL,
    prepared_time  DATETIME,
    expiry_time    DATETIME     NOT NULL,
    pickup_address VARCHAR(255),
    status         VARCHAR(15)  NOT NULL,         -- AVAILABLE / REQUESTED / APPROVED / PICKED_UP / EXPIRED / CANCELLED
    created_at     DATETIME     NOT NULL,
    FOREIGN KEY (donor_id) REFERENCES users(id)
);

CREATE TABLE requests (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    donation_id   BIGINT      NOT NULL,
    ngo_id        BIGINT      NOT NULL,
    requested_qty INT         NOT NULL,
    status        VARCHAR(15) NOT NULL,           -- PENDING / APPROVED / REJECTED / COMPLETED
    requested_at  DATETIME    NOT NULL,
    approved_at   DATETIME,
    FOREIGN KEY (donation_id) REFERENCES donations(id),
    FOREIGN KEY (ngo_id) REFERENCES users(id)
);

CREATE TABLE pickups (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    request_id    BIGINT   NOT NULL UNIQUE,
    picked_up_at  DATETIME NOT NULL,
    confirmed_by  BIGINT   NOT NULL,
    FOREIGN KEY (request_id) REFERENCES requests(id),
    FOREIGN KEY (confirmed_by) REFERENCES users(id)
);

CREATE TABLE activity_log (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id   BIGINT,
    action    VARCHAR(255) NOT NULL,
    timestamp DATETIME     NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
