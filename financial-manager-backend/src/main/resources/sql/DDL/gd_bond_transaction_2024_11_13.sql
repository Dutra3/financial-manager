CREATE TABLE gd_bond_transaction (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    bond_id UUID NOT NULL,
    quantity NUMERIC(8, 4),
    price NUMERIC(8, 4),
    transaction_date DATE NOT NULL,
    CONSTRAINT fk_bond FOREIGN KEY (bond_id) REFERENCES gd_bond (id)
);