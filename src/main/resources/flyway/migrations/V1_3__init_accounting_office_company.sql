create table company
(
    company_id   UUID        NOT NULL,
    company_name VARCHAR(32) NOT NULL,
    nip          VARCHAR(10) NOT NULL,
    address_id   UUID        NOT NULL,
    PRIMARY KEY (company_id),
    UNIQUE (nip),
    CONSTRAINT fk_company_address
        FOREIGN KEY (address_id)
            REFERENCES address (address_id)
);