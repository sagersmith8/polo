CREATE TABLE users (
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    created_tx BIGINT NOT NULL,
    begin_tx BIGINT NOT NULL,
    end_tx BIGINT,
    FOREIGN KEY (created_tx) REFERENCES transactions(transaction_id),
    FOREIGN KEY (begin_tx) REFERENCES transactions(transaction_id),
    FOREIGN KEY (end_tx) REFERENCES transactions(transaction_id)
);

CREATE INDEX idx_user_email_begin_tx ON users (email, begin_tx);
CREATE INDEX idx_user_email_end_tx ON users (email, end_tx);

CREATE INDEX idx_user_phone_number_begin_tx ON users (phone_number, begin_tx);
CREATE INDEX idx_user_phone_number_end_tx ON users (phone_number, end_tx);
