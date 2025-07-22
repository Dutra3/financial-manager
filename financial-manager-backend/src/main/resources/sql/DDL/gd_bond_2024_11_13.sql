CREATE TABLE gd_bond (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    type VARCHAR(30),
    industry_segment VARCHAR(255),
    price NUMERIC(8, 4)
);