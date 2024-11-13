CREATE TABLE gd_stock (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    ticker VARCHAR(30) NOT NULL,
    description VARCHAR(255),
    type VARCHAR(30),
    industry_segment VARCHAR(255),
    tag_along NUMERIC(4, 2),
    price NUMERIC(8, 4),
    is_besst BOOLEAN NOT NULL,
    is_new_market BOOLEAN NOT NULL
);