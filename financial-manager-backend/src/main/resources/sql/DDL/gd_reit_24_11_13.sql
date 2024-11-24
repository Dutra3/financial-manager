CREATE TABLE gd_reit (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    ticker VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    type VARCHAR(255),
    industry_segment VARCHAR(255),
    price NUMERIC(8, 4)
);