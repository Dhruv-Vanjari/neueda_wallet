-- Create User Table
CREATE TABLE user (
    id SERIAL PRIMARY KEY,       -- Primary key, auto-incremented
    name VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    email_id VARCHAR(255) UNIQUE NOT NULL,
    wallet_id_list INTEGER[]     -- Array of wallet IDs
);

-- Create Wallet Table
CREATE TABLE wallet (
    wallet_id SERIAL PRIMARY KEY,  -- Primary key, auto-incremented
    balance DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    user_id INTEGER NOT NULL,       -- Foreign key to user table
    wallet_name VARCHAR(100) NOT NULL,
    security_key VARCHAR(255) NOT NULL,
    transactions_id_list INTEGER[], -- Array of transaction IDs
    CONSTRAINT fk_user
        FOREIGN KEY(user_id)
        REFERENCES user(id)
);

-- Create Transaction Table
CREATE TABLE transaction (
    id SERIAL PRIMARY KEY,       -- Primary key, auto-incremented
    amount DECIMAL(10, 2) NOT NULL,
    type VARCHAR(50) NOT NULL,   -- Type of transaction (e.g., debit, credit)
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    to_wallet INTEGER NOT NULL,  -- Foreign key to wallet table
    CONSTRAINT fk_wallet
        FOREIGN KEY(to_wallet)
        REFERENCES wallet(wallet_id)
);