import { useState } from "react";
import { Box, Typography, TextField, Button, Select, MenuItem, FormControl, InputLabel, IconButton, Alert } from "@mui/material";
import DeleteOutlineIcon from "@mui/icons-material/DeleteOutline";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { getAccounts, createAccount, createTransfer, deleteAccount, Account } from "../../api/accountApi";
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
    const [showTransfer, setShowTransfer] = useState(false);
    const [fromId, setFromId] = useState("");
    const [toId, setToId] = useState("");
    const [transferAmount, setTransferAmount] = useState("");
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

    const transferMutation = useMutation({
        mutationFn: () => createTransfer({
            fromAccountId: fromId,
            toAccountId: toId,
            amount: parseAmountInput(transferAmount),
            date: new Date().toISOString().slice(0, 10),
        }),
        onSuccess: () => {
            invalidate();
            queryClient.invalidateQueries({ queryKey: ["transactions"] });
            setTransferAmount("");
            setShowTransfer(false);
        },
        onError: (err: any) => setError(err?.response?.data?.message || "Transfer failed"),
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
                <Box sx={{ display: "flex", gap: 1 }}>
                    {accounts.length >= 2 && (
                        <Button size="small" variant="outlined" onClick={() => { setShowTransfer(!showTransfer); setShowForm(false); }}
                            sx={{ color: "var(--text-color)", borderColor: "var(--text-color)" }}>
                            {showTransfer ? "Cancel" : "Transfer"}
                        </Button>
                    )}
                    <Button size="small" variant="outlined" onClick={() => { setShowForm(!showForm); setShowTransfer(false); }}
                        sx={{ color: "var(--text-color)", borderColor: "var(--text-color)" }}>
                        {showForm ? "Cancel" : "Add Account"}
                    </Button>
                </Box>
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
                            {acc.type} · initial {formatCurrency(acc.initialBalance)}
                        </Typography>
                    </Box>
                    <Box sx={{ display: "flex", alignItems: "center", gap: 1 }}>
                        <Typography sx={{ color: "var(--text-color)", fontWeight: "bold" }}>
                            {formatCurrency(acc.currentBalance ?? acc.initialBalance)}
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

            {showTransfer && (
                <Box sx={{ display: "flex", flexDirection: "column", gap: 2, marginTop: 2 }}>
                    <FormControl size="small" fullWidth>
                        <InputLabel sx={{ color: "var(--text-color)" }}>From</InputLabel>
                        <Select value={fromId} label="From" onChange={(e) => setFromId(e.target.value)} sx={selectSx}>
                            {accounts.map((a) => <MenuItem key={a.id} value={a.id}>{a.name}</MenuItem>)}
                        </Select>
                    </FormControl>
                    <FormControl size="small" fullWidth>
                        <InputLabel sx={{ color: "var(--text-color)" }}>To</InputLabel>
                        <Select value={toId} label="To" onChange={(e) => setToId(e.target.value)} sx={selectSx}>
                            {accounts.filter((a) => a.id !== fromId).map((a) => <MenuItem key={a.id} value={a.id}>{a.name}</MenuItem>)}
                        </Select>
                    </FormControl>
                    <TextField label="Amount" value={transferAmount}
                        onChange={(e) => setTransferAmount(formatAmountInput(e.target.value, transferAmount))}
                        required size="small" inputMode="decimal" sx={inputSx} />
                    <Button variant="contained" onClick={() => transferMutation.mutate()}
                        disabled={!fromId || !toId || !transferAmount || transferMutation.isPending}>
                        {transferMutation.isPending ? "Sending..." : "Transfer"}
                    </Button>
                </Box>
            )}

            {error && <Alert severity="error" sx={{ marginTop: 1 }}>{error}</Alert>}
        </div>
    );
};

export { AccountsSection };
