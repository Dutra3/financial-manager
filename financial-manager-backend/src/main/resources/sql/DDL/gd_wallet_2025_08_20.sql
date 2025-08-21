CREATE TABLE gd_wallet(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    amount numeric(8, 4) NOT NULL,
    bond_id  UUID,
    stock_id  UUID,
    reit_id  UUID,
    user_id UUID NOT NULL,
    CONSTRAINT gd_wallet_fk_gd_bond
        FOREIGN KEY (bond_id) REFERENCES gd_bond (id),
    CONSTRAINT gd_wallet_fk_gd_stock
        FOREIGN KEY (stock_id) REFERENCES gd_stock (id),
    CONSTRAINT gd_wallet_fk_gd_reit
        FOREIGN KEY (reit_id) REFERENCES gd_reit (id),
    CONSTRAINT gd_wallet_fk_gd_user
        FOREIGN KEY (user_id) REFERENCES gd_user (id)
);