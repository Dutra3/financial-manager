import { useState, useEffect } from "react";
import { Modal, Box, Typography, TextField, Button, Alert, MenuItem, Select, FormControl, InputLabel } from "@mui/material";
import { Category, getCategories } from "../../api/categoryApi";
import { formatAmountInput, parseAmountInput } from "../../utils/formatAmount";

interface BudgetModalProps {
    open: boolean;
    onClose: () => void;
    onSubmit: (categoryId: string, amount: number, month: number, year: number) => Promise<void>;
}

const modalStyle = {
    position: "absolute" as const,
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    width: { xs: "90%", sm: 450 },
    bgcolor: "var(--primary-color)",
    border: "1px solid var(--text-color)",
    boxShadow: 24,
    p: 4,
};

const BudgetModal = ({ open, onClose, onSubmit }: BudgetModalProps) => {
    const [categories, setCategories] = useState<Category[]>([]);
    const [categoryId, setCategoryId] = useState("");
    const [amount, setAmount] = useState("");
    const [month, setMonth] = useState(new Date().getMonth() + 1);
    const [year, setYear] = useState(new Date().getFullYear());
    const [message, setMessage] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);

    useEffect(() => {
        if (open) {
            getCategories().then(setCategories).catch(() => {});
            setCategoryId("");
            setAmount("");
            setMonth(new Date().getMonth() + 1);
            setYear(new Date().getFullYear());
            setMessage("");
        }
    }, [open]);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!categoryId) return;
        setIsSubmitting(true);
        setMessage("");
        try {
            await onSubmit(categoryId, parseAmountInput(amount), month, year);
            setMessage("Budget created successfully!");
            setTimeout(() => onClose(), 1000);
        } catch (err: any) {
            setMessage(`Error: ${err?.response?.data?.message || "Failed to create budget"}`);
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <Modal open={open} onClose={onClose}>
            <Box sx={modalStyle}>
                <Typography variant="h6" sx={{ marginBottom: 2, color: "var(--text-color)" }}>
                    Create Budget
                </Typography>
                <form onSubmit={handleSubmit}>
                    <Box sx={{ display: "flex", flexDirection: "column", gap: 2 }}>
                        <FormControl size="small" fullWidth required>
                            <InputLabel sx={{ color: "var(--text-color)" }}>Category</InputLabel>
                            <Select
                                value={categoryId}
                                onChange={(e) => setCategoryId(e.target.value)}
                                label="Category"
                                sx={{
                                    color: "var(--text-color)",
                                    "& .MuiOutlinedInput-notchedOutline": { borderColor: "var(--text-color)" },
                                    "& .MuiSvgIcon-root": { color: "var(--text-color)" },
                                }}
                            >
                                {categories.map((cat) => (
                                    <MenuItem key={cat.id} value={cat.id}>{cat.name} ({cat.type})</MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                        <TextField
                            label="Budget Amount"
                            value={amount}
                            onChange={(e) => setAmount(formatAmountInput(e.target.value, amount))}
                            required
                            size="small"
                            inputMode="decimal"
                            sx={{ input: { color: "var(--text-color)" }, label: { color: "var(--text-color)" } }}
                        />
                        <Box sx={{ display: "flex", gap: 2 }}>
                            <FormControl size="small" fullWidth>
                                <InputLabel sx={{ color: "var(--text-color)" }}>Month</InputLabel>
                                <Select
                                    value={month}
                                    onChange={(e) => setMonth(e.target.value as number)}
                                    label="Month"
                                    sx={{
                                        color: "var(--text-color)",
                                        "& .MuiOutlinedInput-notchedOutline": { borderColor: "var(--text-color)" },
                                        "& .MuiSvgIcon-root": { color: "var(--text-color)" },
                                    }}
                                >
                                    {Array.from({ length: 12 }, (_, i) => i + 1).map((m) => (
                                        <MenuItem key={m} value={m}>{m}</MenuItem>
                                    ))}
                                </Select>
                            </FormControl>
                            <TextField
                                label="Year"
                                value={year}
                                onChange={(e) => setYear(parseInt(e.target.value.replace(/[^0-9]/g, "").slice(0, 4)) || new Date().getFullYear())}
                                size="small"
                                inputMode="numeric"
                                sx={{ input: { color: "var(--text-color)" }, label: { color: "var(--text-color)" }, flex: 1 }}
                            />
                        </Box>
                        <Box sx={{ display: "flex", gap: 2, marginTop: 1 }}>
                            <Button type="submit" variant="contained" disabled={isSubmitting || !categoryId} sx={{ flex: 1 }}>
                                {isSubmitting ? "Saving..." : "Create Budget"}
                            </Button>
                            <Button variant="outlined" onClick={onClose} sx={{ flex: 1, color: "var(--text-color)", borderColor: "var(--text-color)" }}>
                                Cancel
                            </Button>
                        </Box>
                    </Box>
                </form>
                {message && (
                    <Alert severity={message.startsWith("Error") ? "error" : "success"} sx={{ marginTop: 2 }}>
                        {message}
                    </Alert>
                )}
            </Box>
        </Modal>
    );
};

export { BudgetModal };
