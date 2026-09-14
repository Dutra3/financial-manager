CREATE TABLE gd_wallet(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    amount numeric(8, 4) NOT NULL,
    user_id UUID NOT NULL,
    CONSTRAINT gd_wallet_fk_gd_user
        FOREIGN KEY (user_id) REFERENCES gd_user (id)
);

CREATE TABLE gd_wallet_bonds(
    wallet_id UUID NOT NULL,
    bond_id  UUID NOT NULL,
    PRIMARY KEY (wallet_id, bond_id),
    CONSTRAINT gd_wallet_bonds_fk_gd_wallet
        FOREIGN KEY (wallet_id) REFERENCES gd_wallet (id),
    CONSTRAINT gd_wallet_bonds_fk_gd_bond
        FOREIGN KEY (bond_id) REFERENCES gd_bond (id)
);

CREATE TABLE gd_wallet_stocks(
    wallet_id UUID NOT NULL,
    stock_id  UUID NOT NULL,
    PRIMARY KEY (wallet_id, stock_id),
    CONSTRAINT gd_wallet_stocks_fk_gd_wallet
        FOREIGN KEY (wallet_id) REFERENCES gd_wallet (id),
    CONSTRAINT gd_wallet_stocks_fk_gd_stock
        FOREIGN KEY (stock_id) REFERENCES gd_stock (id)
);

CREATE TABLE gd_wallet_reits(
    wallet_id UUID NOT NULL,
    reit_id   UUID NOT NULL,
    PRIMARY KEY (wallet_id, reit_id),
    CONSTRAINT gd_wallet_reits_fk_gd_wallet
        FOREIGN KEY (wallet_id) REFERENCES gd_wallet (id),
    CONSTRAINT gd_wallet_reits_fk_gd_reit
        FOREIGN KEY (reit_id) REFERENCES gd_reit (id)
);
