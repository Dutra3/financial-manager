import { useState } from "react";
import { Box, Typography, TextField, Button, Select, MenuItem, FormControl, InputLabel, IconButton, Alert } from "@mui/material";
import DeleteOutlineIcon from "@mui/icons-material/DeleteOutline";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { getAccounts, createAccount, deleteAccount, Account } from "../../api/accountApi";
import { getUserId } from "../../utils/auth";
import { formatAmountInput, parseAmountInput } from "../../utils/formatAmount";

const formatCurrency = (amount: number) =>
    new Intl.NumberFormat("en-US", { style: "currency", currency: "USD" }).format(amount);

const AccountsSection = () => {
    const queryClient = useQueryClient();
    const [name, setName] = useState("");
    const [type, setType] = useState("CHECKING");
    const [initialBalance, setInitialBalance] = useState("");
    const [showForm, setShowForm] = useState(false);
    const [error, setError] = useState("");

    const { data: accounts = [] } = useQuery<Account[]>({
        queryKey: ["accounts", getUserId()],
        queryFn: () => getAccounts(getUserId()),
    });

    const invalidate = () => queryClient.invalidateQueries({ queryKey: ["accounts", getUserId()] });

    const createMutation = useMutation({
        mutationFn: () => createAccount({ name, type, initialBalance: parseAmountInput(initialBalance) }),
        onSuccess: () => {
            invalidate();
            setName("");
            setInitialBalance("");
            setShowForm(false);
        },
        onError: () => setError("Failed to create account"),
    });

    const deleteMutation = useMutation({
        mutationFn: deleteAccount,
        onSuccess: invalidate,
    });

    const inputSx = { input: { color: "var(--text-color)" }, label: { color: "var(--text-color)" } };
    const selectSx = {
        color: "var(--text-color)",
        "& .MuiOutlinedInput-notchedOutline": { borderColor: "var(--text-color)" },
        "& .MuiSvgIcon-root": { color: "var(--text-color)" },
    };

    return (
        <div className="profile-section">
            <Box sx={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
                <h2>Accounts</h2>
                <Button size="small" variant="outlined" onClick={() => setShowForm(!showForm)}
                    sx={{ color: "var(--text-color)", borderColor: "var(--text-color)" }}>
                    {showForm ? "Cancel" : "Add Account"}
                </Button>
            </Box>

            {accounts.length === 0 && !showForm && (
                <Typography sx={{ color: "var(--text-color)", opacity: 0.7, fontSize: "0.9rem" }}>
                    No accounts yet. Add one to track balances separately.
                </Typography>
            )}

            {accounts.map((acc) => (
                <Box key={acc.id} sx={{
                    display: "flex", justifyContent: "space-between", alignItems: "center",
                    padding: "8px 0", borderBottom: "1px solid rgba(128,128,128,0.15)",
                }}>
                    <Box>
                        <Typography sx={{ color: "var(--text-color)" }}>{acc.name}</Typography>
                        <Typography sx={{ color: "var(--text-color)", opacity: 0.6, fontSize: "0.8rem" }}>
                            {acc.type}
                        </Typography>
                    </Box>
                    <Box sx={{ display: "flex", alignItems: "center", gap: 1 }}>
                        <Typography sx={{ color: "var(--text-color)", fontWeight: "bold" }}>
                            {formatCurrency(acc.initialBalance)}
                        </Typography>
                        <IconButton size="small" onClick={() => deleteMutation.mutate(acc.id)}
                            sx={{ color: "var(--text-color)" }}>
                            <DeleteOutlineIcon fontSize="small" />
                        </IconButton>
                    </Box>
                </Box>
            ))}

            {showForm && (
                <Box sx={{ display: "flex", flexDirection: "column", gap: 2, marginTop: 2 }}>
                    <TextField label="Account Name" value={name} onChange={(e) => setName(e.target.value)}
                        required size="small" sx={inputSx} />
                    <FormControl size="small" fullWidth>
                        <InputLabel sx={{ color: "var(--text-color)" }}>Type</InputLabel>
                        <Select value={type} label="Type" onChange={(e) => setType(e.target.value)} sx={selectSx}>
                            <MenuItem value="CHECKING">Checking</MenuItem>
                            <MenuItem value="SAVINGS">Savings</MenuItem>
                            <MenuItem value="CASH">Cash</MenuItem>
                            <MenuItem value="INVESTMENT">Investment</MenuItem>
                        </Select>
                    </FormControl>
                    <TextField label="Initial Balance" value={initialBalance}
                        onChange={(e) => setInitialBalance(formatAmountInput(e.target.value, initialBalance))}
                        required size="small" inputMode="decimal" sx={inputSx} />
                    <Button variant="contained" onClick={() => createMutation.mutate()}
                        disabled={!name || !initialBalance || createMutation.isPending}>
                        {createMutation.isPending ? "Saving..." : "Create Account"}
                    </Button>
                </Box>
            )}

            {error && <Alert severity="error" sx={{ marginTop: 1 }}>{error}</Alert>}
        </div>
    );
};

export { AccountsSection };
