CREATE TABLE bank_users (
    id BIGSERIAL PRIMARY KEY,
    role VARCHAR(50),
        --Auth
        cif VARCHAR(10) NOT NULL UNIQUE CONSTRAINT chk_cif_length CHECK (length(cif) = 10),
        hashed_password VARCHAR(255) NOT NULL,
        pesel VARCHAR(11) NOT NULL UNIQUE CONSTRAINT chk_pesel_length CHECK (length(pesel) = 11),
        reset_token VARCHAR(255),
            --SecurityLock
                lock_type VARCHAR(50) NOT NULL,
                retries_left INT NOT NULL,
                unlock_time TIMESTAMP,
        --Profile
        first_name VARCHAR(100) NOT NULL,
        last_name VARCHAR(100) NOT NULL,
        email VARCHAR(255) NOT NULL UNIQUE,
        telephone_number VARCHAR(20) UNIQUE,
        country VARCHAR(100),
        address VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE accounts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    account_number VARCHAR(26) NOT NULL UNIQUE CONSTRAINT chk_account_number CHECK (length(account_number) = 26),
    balance DECIMAL(19, 2) NOT NULL DEFAULT 0.00,
    currency VARCHAR(3) NOT NULL DEFAULT 'PLN',
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_accounts_user_id FOREIGN KEY (user_id) REFERENCES bank_users(id)
);

CREATE TABLE bank_transactions (
    id BIGSERIAL PRIMARY KEY,
    initiatorId BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    receiver_account_number VARCHAR(26) NOT NULL CONSTRAINT chk_account_number CHECK (length(receiver_account_number) = 26),
    sender_account_number VARCHAR(26) NOT NULL CONSTRAINT chk_account_number CHECK (length(sender_account_number) = 26),
    receiver_name VARCHAR(100) NOT NULL,
    sender_name VARCHAR(100) NOT NULL,
    receiver_amount DECIMAL(19, 2) NOT NULL,
    sender_amount DECIMAL(19, 2) NOT NULL,
    receiver_currency VARCHAR(3) NOT NULL,
    sender_currency VARCHAR(3) NOT NULL,
    exchange_rate DECIMAL(19, 4),
    exchange_fee DECIMAL(19, 2),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);