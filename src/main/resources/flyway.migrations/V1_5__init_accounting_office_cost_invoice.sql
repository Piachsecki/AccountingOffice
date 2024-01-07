create table cost_invoice
(
    invoice_id  UUID           NOT NULL,
    date        TIMESTAMP WITH TIME ZONE,
    currency    VARCHAR(6)     NOT NULL,
    amount      NUMERIC(19, 2) NOT NULL,
    company_id  UUID           NOT NULL,
    product_id  UUID           NOT NULL,
    customer_id UUID           NOT NULL,
    PRIMARY KEY (invoice_id),
    CONSTRAINT fk_cost_invoice_product
        FOREIGN KEY (product_id)
            REFERENCES product (product_id),
    CONSTRAINT fk_cost_invoice_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer (customer_id),
    CONSTRAINT fk_cost_invoice_company
        FOREIGN KEY (company_id)
            REFERENCES company (company_id)
);