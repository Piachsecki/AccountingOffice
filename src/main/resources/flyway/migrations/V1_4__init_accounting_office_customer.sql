create table customer
(
    customer_id           UUID                     NOT NULL,
    name                  VARCHAR(32)              NOT NULL,
    surname               VARCHAR(32)              NOT NULL,
    join_date             TIMESTAMP WITH TIME ZONE NOT NULL,
    entrepreneurship_form VARCHAR(32)              NOT NULL,
    nip                   VARCHAR(10)              NOT NULL,
    tax_payment_form      VARCHAR(32)              NOT NULL,
    tax_rate              VARCHAR(32)              NOT NULL,
    address_id            UUID                     NOT NULL,
    PRIMARY KEY (customer_id),
    UNIQUE (nip),
    CONSTRAINT fk_customer_address
        FOREIGN KEY (address_id)
            REFERENCES address (address_id)
);