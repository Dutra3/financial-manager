CREATE TABLE IF NOT EXISTS gd_password_reset_token (
    id          uuid                    NOT NULL,
    user_id     uuid                    NOT NULL,
    token_hash  varchar(64)             NOT NULL,
    expires_at  timestamp               NOT NULL,
    used        boolean                 NOT NULL DEFAULT false,
    created_at  timestamp               NOT NULL DEFAULT now(),
    CONSTRAINT gd_password_reset_token_pkey PRIMARY KEY (id),
    CONSTRAINT fk_gd_password_reset_token_user FOREIGN KEY (user_id) REFERENCES gd_user (id)
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_gd_password_reset_token_hash ON gd_password_reset_token (token_hash);
