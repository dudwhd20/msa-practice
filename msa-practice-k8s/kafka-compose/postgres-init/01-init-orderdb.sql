CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'CREATED',
    created_at TIMESTAMP DEFAULT now()
);

CREATE TABLE order_item (
    id SERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    product_name VARCHAR(255),
    quantity INT NOT NULL,
    price INT NOT NULL
);

CREATE TABLE order_outbox_event (
    id SERIAL PRIMARY KEY,
    event_type VARCHAR(255),
    event_version VARCHAR(255),
    trace_id VARCHAR(255),
    payload TEXT,
    created_at TIMESTAMP DEFAULT now()
);
