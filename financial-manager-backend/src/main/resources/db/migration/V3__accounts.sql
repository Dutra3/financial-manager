CREATE TABLE IF NOT EXISTS gd_account (
    id              uuid           NOT NULL,
    name            varchar(100)   NOT NULL,
    account_type    varchar(30)    NOT NULL,
    initial_balance numeric(13, 4) NOT NULL,
    user_id         uuid           NOT NULL,
    CONSTRAINT gd_account_pkey PRIMARY KEY (id),
    CONSTRAINT fk_gd_account_user FOREIGN KEY (user_id) REFERENCES gd_user (id)
);

ALTER TABLE gd_transaction ADD COLUMN IF NOT EXISTS account_id uuid;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint WHERE conname = 'fk_gd_transaction_account'
    ) THEN
        ALTER TABLE gd_transaction
            ADD CONSTRAINT fk_gd_transaction_account
            FOREIGN KEY (account_id) REFERENCES gd_account (id);
    END IF;
END $$;
