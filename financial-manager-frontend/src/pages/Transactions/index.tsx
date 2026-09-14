import { useState } from "react";
import { Header } from "../../components/Header";
import { SideBar } from "../../components/SideBar";
import { createCategory, getCategories, Category, CategoryType } from "../../api/categoryApi";
import { createTransaction } from "../../api/transactionApi";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { Box, Typography, TextField, Button, Select, MenuItem, FormControl, InputLabel, Paper, Alert } from "@mui/material";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { format } from "date-fns";
import { formatAmountInput, parseAmountInput } from "../../utils/formatAmount";
import "./Transactions.css";

const PLACEHOLDER_USER_ID = "00000000-0000-0000-0000-000000000000";

const Transactions = () => {
    const queryClient = useQueryClient();
    const [categoryName, setCategoryName] = useState("");
    const [categoryType, setCategoryType] = useState<CategoryType>("debit");
    const [categoryMessage, setCategoryMessage] = useState("");
    const [transactionName, setTransactionName] = useState("");
    const [description, setDescription] = useState("");
    const [amount, setAmount] = useState("");
    const [paymentDate, setPaymentDate] = useState<Date | null>(null);
    const [categoryId, setCategoryId] = useState("");
    const [transactionMessage, setTransactionMessage] = useState("");

    const { data: categories = [], isLoading: categoriesLoading } = useQuery({
        queryKey: ["categories"],
        queryFn: () => getCategories() as Promise<Category[]>,
    });

    const categoryMutation = useMutation({
        mutationFn: (newCategory: Category) => createCategory(newCategory),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["categories"] });
            setCategoryMessage("Category created successfully!");
            setCategoryName("");
        },
        onError: (err: any) => {
            setCategoryMessage(`Error: ${err?.response?.data?.message || "Failed to create category"}`);
        },
    });

    const transactionMutation = useMutation({
        mutationFn: () => createTransaction({
            id: "",
            name: transactionName,
            description,
            amount: parseAmountInput(amount),
            paymentDate: paymentDate ? format(paymentDate, "yyyy-MM-dd") : "",
            categoryId,
        }),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["transactions", PLACEHOLDER_USER_ID] });
            setTransactionMessage("Transaction created successfully!");
            setTransactionName("");
            setDescription("");
            setAmount("");
            setPaymentDate(null);
            setCategoryId("");
        },
        onError: (err: any) => {
            setTransactionMessage(`Error: ${err?.response?.data?.message || "Failed to create transaction"}`);
        },
    });

    const handleCategorySubmit = (e: React.FormEvent) => {
        e.preventDefault();
        categoryMutation.mutate({ id: "", name: categoryName, type: categoryType });
    };

    const handleTransactionSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        transactionMutation.mutate();
    };

    return (
        <main className="transactions-container">
            <SideBar page="transactions"/>
            <div className="transaction-content">
                <Header title="Olá, Gabriel. Você está no menu de Transações. Aqui você encontra suas despesas e receitas passadas, além de poder cadastrar novos dados de despesas e receitas."/>

                <Box sx={{ display: "flex", gap: 3, flexWrap: "wrap" }}>
                    <Paper sx={{ flex: "1 1 300px", padding: 3, backgroundColor: "var(--primary-color)" }}>
                        <Typography variant="h6" sx={{ marginBottom: 2, color: "var(--text-color)" }}>
                            Create Category
                        </Typography>
                        <form onSubmit={handleCategorySubmit}>
                            <Box sx={{ display: "flex", flexDirection: "column", gap: 2 }}>
                                <TextField
                                    label="Name"
                                    value={categoryName}
                                    onChange={(e) => setCategoryName(e.target.value)}
                                    required
                                    size="small"
                                    sx={{ input: { color: "var(--text-color)" }, label: { color: "var(--text-color)" } }}
                                />
                                <FormControl size="small" fullWidth>
                                    <InputLabel sx={{ color: "var(--text-color)" }}>Type</InputLabel>
                                    <Select
                                        value={categoryType}
                                        onChange={(e) => setCategoryType(e.target.value as CategoryType)}
                                        label="Type"
                                        sx={{
                                            color: "var(--text-color)",
                                            "& .MuiOutlinedInput-notchedOutline": { borderColor: "var(--text-color)" },
                                            "& .MuiSvgIcon-root": { color: "var(--text-color)" },
                                        }}
                                    >
                                        <MenuItem value="debit">Debit</MenuItem>
                                        <MenuItem value="credit">Credit</MenuItem>
                                    </Select>
                                </FormControl>
                                <Button type="submit" variant="contained" disabled={categoryMutation.isPending}>
                                    {categoryMutation.isPending ? "Creating..." : "Create Category"}
                                </Button>
                            </Box>
                        </form>
                        {categoryMessage && (
                            <Alert severity={categoryMessage.startsWith("Error") ? "error" : "success"} sx={{ marginTop: 2 }}>
                                {categoryMessage}
                            </Alert>
                        )}
                    </Paper>

                    <Paper sx={{ flex: "2 1 400px", padding: 3, backgroundColor: "var(--primary-color)" }}>
                        <Typography variant="h6" sx={{ marginBottom: 2, color: "var(--text-color)" }}>
                            Create Transaction
                        </Typography>
                        <form onSubmit={handleTransactionSubmit}>
                            <Box sx={{ display: "flex", flexDirection: "column", gap: 2 }}>
                                <TextField
                                    label="Name"
                                    value={transactionName}
                                    onChange={(e) => setTransactionName(e.target.value)}
                                    required
                                    size="small"
                                    sx={{ input: { color: "var(--text-color)" }, label: { color: "var(--text-color)" } }}
                                />
                                <TextField
                                    label="Description"
                                    value={description}
                                    onChange={(e) => setDescription(e.target.value)}
                                    required
                                    size="small"
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
                                <Button type="submit" variant="contained" disabled={transactionMutation.isPending || !paymentDate}>
                                    {transactionMutation.isPending ? "Creating..." : "Create Transaction"}
                                </Button>
                            </Box>
                        </form>
                        {transactionMessage && (
                            <Alert severity={transactionMessage.startsWith("Error") ? "error" : "success"} sx={{ marginTop: 2 }}>
                                {transactionMessage}
                            </Alert>
                        )}
                    </Paper>
                </Box>
            </div>
        </main>
    );
};

export { Transactions };
