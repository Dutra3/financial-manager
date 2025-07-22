CREATE TABLE gd_stock_transaction (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    stock_id UUID NOT NULL,
    quantity NUMERIC(8, 4),
    price NUMERIC(8, 4),
    transaction_date DATE NOT NULL,
    CONSTRAINT fk_stock FOREIGN KEY (stock_id) REFERENCES gd_stock (id)
);