CREATE TABLE gd_etf_transaction (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    etf_id UUID NOT NULL,
    quantity NUMERIC(8, 4),
    price NUMERIC(8, 4),
    transaction_date DATE NOT NULL,
    CONSTRAINT fk_etf FOREIGN KEY (etf_id) REFERENCES gd_etf (id)
);