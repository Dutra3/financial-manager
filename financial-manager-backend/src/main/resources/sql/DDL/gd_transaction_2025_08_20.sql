CREATE TABLE gd_transaction(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    amount numeric(8, 4) NOT NULL,
    payment_date DATE NOT NULL,
    category_id  UUID NOT NULL,
    user_id UUID NOT NULL,
    CONSTRAINT gd_transaction_fk_gd_category
        FOREIGN KEY (category_id) REFERENCES gd_category (id),
    CONSTRAINT gd_transaction_fk_gd_user
        FOREIGN KEY (user_id) REFERENCES gd_user (id)
);