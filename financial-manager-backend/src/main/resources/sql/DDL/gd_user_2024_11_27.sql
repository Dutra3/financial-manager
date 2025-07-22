CREATE TABLE gd_user (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(150) NOT NULL UNIQUE,
    password TEXT NOT NULL
);
