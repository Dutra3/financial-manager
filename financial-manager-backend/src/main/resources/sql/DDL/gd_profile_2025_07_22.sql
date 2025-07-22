CREATE TABLE gd_profile(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    profession VARCHAR(100),
    netSalary numeric(8, 4) NOT NULL,
    payDay INTEGER NOT NULL,
    initialBalance numeric(8, 4) NOT NULL,
    financialGoal numeric(8, 4) NOT NULL,
    user_id UUID NOT NULL,
    CONSTRAINT gd_profile_fk_gd_user
        FOREIGN KEY (user_id) REFERENCES gd_user (id)
);