CREATE TABLE IF NOT EXISTS `economy_users` (
    user_id CHAR(36) NOT NULL PRIMARY KEY,
    user_name VARCHAR(16) NOT NULL,
    wallet_balance DOUBLE NOT NULL
);