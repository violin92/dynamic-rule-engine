CREATE SEQUENCE rule_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE rule(
    id BIGINT PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    condition VARCHAR(255) NOT NULL,
    action VARCHAR(255) NOT NULL
)