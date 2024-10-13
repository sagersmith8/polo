CREATE TABLE transactions (
    transaction_id BIGSERIAL PRIMARY KEY, -- Automatically increments
    transaction_timestamp TIMESTAMP DEFAULT NOW() -- Timestamp for reference
);
