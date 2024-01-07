create table address
(
    address_id  UUID        NOT NULL,
    city        VARCHAR(32) NOT NULL,
    country     VARCHAR(32) NOT NULL,
    address     VARCHAR(32) NOT NULL,
    postal_code VARCHAR(32) NOT NULL,
    PRIMARY KEY (address_id)
);
