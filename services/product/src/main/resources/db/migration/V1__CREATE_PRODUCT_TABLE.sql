CREATE TABLE products
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)          NULL,
    `description` LONGTEXT              NULL,
    price         NUMERIC(38,2)          NULL,
    created_at    datetime              NULL,
    CONSTRAINT pk_products PRIMARY KEY (id)
);