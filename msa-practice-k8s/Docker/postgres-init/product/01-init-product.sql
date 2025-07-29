CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price INTEGER NOT NULL,
    stock_quantity INTEGER NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
