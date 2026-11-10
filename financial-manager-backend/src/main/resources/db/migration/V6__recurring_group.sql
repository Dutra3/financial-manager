ALTER TABLE gd_transaction ADD COLUMN IF NOT EXISTS recurring_group_id uuid;
CREATE INDEX IF NOT EXISTS idx_gd_transaction_recurring_group ON gd_transaction (recurring_group_id);
