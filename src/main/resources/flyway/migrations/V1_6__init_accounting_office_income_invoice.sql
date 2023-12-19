create table income_invoice
(
    invoice_id  UUID           NOT NULL,
    date        TIMESTAMP WITH TIME ZONE,
    currency    VARCHAR(5)     NOT NULL,
    amount      NUMERIC(19, 2) NOT NULL,
    customer_id UUID           NOT NULL,
    PRIMARY KEY (invoice_id),
    CONSTRAINT fk_income_invoice_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer (customer_id)
);
