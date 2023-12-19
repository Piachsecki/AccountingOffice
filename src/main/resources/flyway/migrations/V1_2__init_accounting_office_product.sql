create table product
(
    product_id   UUID           NOT NULl,
    product_name VARCHAR(32)    NOT NULL,
    amount       numeric(19, 2) NOT NULL,
    currency     VARCHAR(5)     NOT NULL,
    PRIMARY KEY (product_id)
);