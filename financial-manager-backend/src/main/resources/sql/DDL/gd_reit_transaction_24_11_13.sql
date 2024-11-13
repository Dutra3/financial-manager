CREATE TABLE gd_reit_transaction (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    reit_id UUID NOT NULL,
    quantity NUMERIC(8, 4),
    price NUMERIC(8, 4),
    transaction_date DATE NOT NULL,
    CONSTRAINT fk_reit FOREIGN KEY (reit_id) REFERENCES gd_reit (id)
);