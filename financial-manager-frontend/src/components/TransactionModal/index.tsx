import { useState, useEffect } from "react";
import { Modal, Box, Typography, TextField, Button, Select, MenuItem, FormControl, InputLabel, Alert } from "@mui/material";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { format, parseISO } from "date-fns";
import { createTransaction, updateTransaction, TransactionResponse } from "../../api/transactionApi";
import { getCategories, Category } from "../../api/categoryApi";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { formatAmountInput, parseAmountInput } from "../../utils/formatAmount";

const PLACEHOLDER_USER_ID = "00000000-0000-0000-0000-000000000000";

interface TransactionModalProps {
    open: boolean;
    onClose: () => void;
    transaction?: TransactionResponse | null;
}

const modalStyle = {
    position: "absolute" as const,
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    width: { xs: "90%", sm: 500 },
    bgcolor: "var(--primary-color)",
    border: "1px solid var(--text-color)",
    boxShadow: 24,
    p: 4,
    maxHeight: "90vh",
    overflow: "auto",
};

const TransactionModal = ({ open, onClose, transaction }: TransactionModalProps) => {
    const queryClient = useQueryClient();
    const isEdit = !!transaction;

    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [amount, setAmount] = useState("");
    const [paymentDate, setPaymentDate] = useState<Date | null>(null);
    const [categoryId, setCategoryId] = useState("");
    const [message, setMessage] = useState("");

    const { data: categories = [], isLoading: categoriesLoading } = useQuery({
        queryKey: ["categories"],
        queryFn: () => getCategories() as Promise<Category[]>,
    });

    useEffect(() => {
        if (open && transaction) {
            setName(transaction.name);
            setDescription(transaction.description);
            setAmount(transaction.amount.toFixed(2));
            setPaymentDate(parseISO(transaction.paymentDate));
            const matched = categories.find((c) => c.name === transaction.category);
            setCategoryId(matched?.id || "");
        } else if (open) {
            setName("");
            setDescription("");
            setAmount("");
            setPaymentDate(null);
            setCategoryId("");
        }
        setMessage("");
    }, [open, transaction, categories]);

    const mutation = useMutation({
        mutationFn: () => {
            const payload = {
                id: isEdit ? transaction!.id : "",
                name,
                description,
                amount: parseAmountInput(amount),
                paymentDate: paymentDate ? format(paymentDate, "yyyy-MM-dd") : "",
                categoryId,
            };
            return isEdit
                ? updateTransaction(transaction!.id, payload)
                : createTransaction(payload);
        },
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["transactions", PLACEHOLDER_USER_ID] });
            setMessage(isEdit ? "Transaction updated successfully!" : "Transaction created successfully!");
            setTimeout(() => onClose(), 1000);
        },
        onError: (err: any) => {
            const action = isEdit ? "update" : "create";
            setMessage(`Error: ${err?.response?.data?.message || "Failed to " + action + " transaction"}`);
        },
    });

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        mutation.mutate();
    };

    return (
        <Modal open={open} onClose={onClose}>
            <Box sx={modalStyle}>
                <Typography variant="h6" sx={{ marginBottom: 2, color: "var(--text-color)" }}>
                    {isEdit ? "Edit Transaction" : "Create Transaction"}
                </Typography>
                <form onSubmit={handleSubmit}>
                    <Box sx={{ display: "flex", flexDirection: "column", gap: 2 }}>
                        <TextField
                            label="Name"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            required
                            size="small"
                            sx={{ input: { color: "var(--text-color)" }, label: { color: "var(--text-color)" } }}
                        />
                        <TextField
                            label="Notes"
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            size="small"
                            multiline
                            rows={2}
                            sx={{ input: { color: "var(--text-color)" }, label: { color: "var(--text-color)" } }}
                        />
                        <Box sx={{ display: "flex", gap: 2 }}>
                            <TextField
                                label="Amount"
                                value={amount}
                                onChange={(e) => setAmount(formatAmountInput(e.target.value, amount))}
                                required
                                size="small"
                                placeholder="0.00"
                                inputMode="decimal"
                                sx={{ flex: 1, input: { color: "var(--text-color)" }, label: { color: "var(--text-color)" } }}
                            />
                            <DatePicker
                                label="Payment Date"
                                value={paymentDate}
                                onChange={(date) => setPaymentDate(date)}
                                minDate={new Date(1900, 0, 1)}
                                maxDate={new Date(2100, 11, 31)}
                                shouldDisableDate={(date) => {
                                    const min = new Date(1900, 0, 1);
                                    const max = new Date(2100, 11, 31);
                                    return date < min || date > max;
                                }}
                                slotProps={{
                                    textField: {
                                        size: "small",
                                        required: true,
                                        onBlur: (e) => {
                                            const raw = e.target.value;
                                            if (!raw) return;
                                            const d = new Date(raw);
                                            if (isNaN(d.getTime())) return;
                                            const min = new Date(1900, 0, 1);
                                            const max = new Date(2100, 11, 31);
                                            if (d < min) setPaymentDate(min);
                                            else if (d > max) setPaymentDate(max);
                                        },
                                        sx: {
                                            flex: 1,
                                            "& .MuiInputBase-root": { color: "var(--text-color)" },
                                            "& .MuiOutlinedInput-notchedOutline": { borderColor: "var(--text-color)" },
                                            "& .MuiSvgIcon-root": { color: "var(--text-color)" },
                                            label: { color: "var(--text-color)" },
                                        },
                                    },
                                }}
                            />
                        </Box>
                        <FormControl size="small" fullWidth>
                            <InputLabel sx={{ color: "var(--text-color)" }}>Category</InputLabel>
                            <Select
                                value={categoryId}
                                onChange={(e) => setCategoryId(e.target.value)}
                                label="Category"
                                required
                                disabled={categoriesLoading}
                                sx={{
                                    color: "var(--text-color)",
                                    "& .MuiOutlinedInput-notchedOutline": { borderColor: "var(--text-color)" },
                                    "& .MuiSvgIcon-root": { color: "var(--text-color)" },
                                }}
                            >
                                {categories.map((cat) => (
                                    <MenuItem key={cat.id} value={cat.id}>
                                        {cat.name} ({cat.type})
                                    </MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                        <Box sx={{ display: "flex", gap: 2, marginTop: 1 }}>
                            <Button type="submit" variant="contained" disabled={mutation.isPending || !paymentDate} sx={{ flex: 1 }}>
                                {mutation.isPending ? "Saving..." : isEdit ? "Save Changes" : "Create"}
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

export { TransactionModal };
