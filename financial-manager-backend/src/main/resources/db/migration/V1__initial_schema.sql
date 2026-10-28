-- Initial schema for the current entity model.
-- All statements are idempotent: on the existing development database they only
-- create the missing tables (gd_asset, gd_asset_transaction, gd_refresh_token);
-- on a fresh database they create the whole schema.

CREATE TABLE IF NOT EXISTS gd_user (
    id          uuid         NOT NULL,
    email       varchar(150) NOT NULL,
    password    varchar(255),
    provider    varchar(50),
    provider_id varchar(255),
    CONSTRAINT gd_user_pkey PRIMARY KEY (id)
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_gd_user_email ON gd_user (email);

CREATE TABLE IF NOT EXISTS gd_category (
    id   uuid         NOT NULL,
    name varchar(100) NOT NULL,
    type varchar(30)  NOT NULL,
    CONSTRAINT gd_category_pkey PRIMARY KEY (id),
    CONSTRAINT gd_category_type_check CHECK (type IN ('DEBIT', 'CREDIT'))
);

CREATE TABLE IF NOT EXISTS gd_profile (
    id              uuid           NOT NULL,
    financial_goal  numeric(13, 4) NOT NULL,
    initial_balance numeric(13, 4) NOT NULL,
    name            varchar(255)   NOT NULL,
    net_salary      numeric(13, 4) NOT NULL,
    payday          integer        NOT NULL,
    profession      varchar(100),
    user_id         uuid           NOT NULL,
    CONSTRAINT gd_profile_pkey PRIMARY KEY (id),
    CONSTRAINT fk_gd_profile_user FOREIGN KEY (user_id) REFERENCES gd_user (id)
);

CREATE TABLE IF NOT EXISTS gd_transaction (
    id           uuid           NOT NULL,
    amount       numeric(13, 4) NOT NULL,
    description  varchar(255),
    name         varchar(100)   NOT NULL,
    payment_date date           NOT NULL,
    category_id  uuid           NOT NULL,
    user_id      uuid           NOT NULL,
    CONSTRAINT gd_transaction_pkey PRIMARY KEY (id),
    CONSTRAINT fk_gd_transaction_category FOREIGN KEY (category_id) REFERENCES gd_category (id),
    CONSTRAINT fk_gd_transaction_user FOREIGN KEY (user_id) REFERENCES gd_user (id)
);

CREATE TABLE IF NOT EXISTS gd_goal (
    id            uuid           NOT NULL,
    description   varchar(255),
    is_achieved   boolean        NOT NULL,
    name          varchar(100)   NOT NULL,
    target_amount numeric(13, 4) NOT NULL,
    target_date   date,
    user_id       uuid           NOT NULL,
    CONSTRAINT gd_goal_pkey PRIMARY KEY (id),
    CONSTRAINT fk_gd_goal_user FOREIGN KEY (user_id) REFERENCES gd_user (id)
);

CREATE TABLE IF NOT EXISTS gd_budget (
    id           uuid           NOT NULL,
    amount       numeric(13, 4) NOT NULL,
    budget_month integer        NOT NULL,
    budget_year  integer        NOT NULL,
    category_id  uuid           NOT NULL,
    user_id      uuid           NOT NULL,
    CONSTRAINT gd_budget_pkey PRIMARY KEY (id),
    CONSTRAINT fk_gd_budget_category FOREIGN KEY (category_id) REFERENCES gd_category (id),
    CONSTRAINT fk_gd_budget_user FOREIGN KEY (user_id) REFERENCES gd_user (id)
);

CREATE TABLE IF NOT EXISTS gd_asset (
    id               uuid           NOT NULL,
    ticker           varchar(30),
    name             varchar(100)   NOT NULL,
    asset_type       varchar(30)    NOT NULL,
    description      varchar(255),
    type             varchar(30),
    industry_segment varchar(255),
    price            numeric(13, 4),
    tag_along        numeric(13, 4),
    pe_ratio         numeric(13, 4),
    dividend_yield   numeric(13, 4),
    pb_ratio         numeric(13, 4),
    last_dividend    numeric(13, 4),
    is_besst         boolean,
    is_new_market    boolean,
    CONSTRAINT gd_asset_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS gd_asset_transaction (
    id               uuid           NOT NULL,
    asset_id         uuid           NOT NULL,
    user_id          uuid           NOT NULL,
    transaction_type varchar(30)    NOT NULL,
    quantity         numeric(13, 4),
    price            numeric(13, 4),
    transaction_date date           NOT NULL,
    CONSTRAINT gd_asset_transaction_pkey PRIMARY KEY (id),
    CONSTRAINT fk_gd_asset_transaction_asset FOREIGN KEY (asset_id) REFERENCES gd_asset (id),
    CONSTRAINT fk_gd_asset_transaction_user FOREIGN KEY (user_id) REFERENCES gd_user (id)
);

CREATE TABLE IF NOT EXISTS gd_refresh_token (
    id         uuid                  NOT NULL,
    user_id    uuid                  NOT NULL,
    token_hash varchar(64)           NOT NULL,
    expires_at timestamp             NOT NULL,
    revoked    boolean               NOT NULL,
    created_at timestamp             NOT NULL,
    CONSTRAINT gd_refresh_token_pkey PRIMARY KEY (id),
    CONSTRAINT uk_gd_refresh_token_hash UNIQUE (token_hash),
    CONSTRAINT fk_gd_refresh_token_user FOREIGN KEY (user_id) REFERENCES gd_user (id)
);
