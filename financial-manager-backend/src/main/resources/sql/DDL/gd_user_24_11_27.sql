CREATE TABLE gd_user (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    fullName VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password TEXT NOT NULL
);
