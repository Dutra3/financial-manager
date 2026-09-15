import { useState, useMemo } from "react";
import { Header } from "../../components/Header";
import { SideBar } from "../../components/SideBar";
import { TransactionModal } from "../../components/TransactionModal";
import { CategoryModal } from "../../components/CategoryModal";
import { DeleteConfirmationModal } from "../../components/DeleteConfirmationModal";
import { getTransactions, TransactionResponse } from "../../api/transactionApi";
import { getCategories, Category } from "../../api/categoryApi";
import { exportTransactions, downloadCSV } from "../../api/exportApi";
import { useQuery } from "@tanstack/react-query";
import { Box, Typography, Button, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, IconButton, CircularProgress, Select, MenuItem, FormControl, InputLabel, Tooltip } from "@mui/material";
import { FiEdit2, FiTrash2, FiPlusCircle, FiTag, FiDownload } from "react-icons/fi";
import "./Transactions.css";

const PLACEHOLDER_USER_ID = "00000000-0000-0000-0000-000000000000";

const formatAmount = (amount: number, type: string) => {
    const prefix = type === "DEBIT" ? "-" : "+";
    return `${prefix}$${amount.toLocaleString("en-US", { minimumFractionDigits: 2, maximumFractionDigits: 2 })}`;
};

const formatDate = (dateStr: string) => {
    const d = new Date(dateStr);
    return d.toLocaleDateString("en-US", { year: "numeric", month: "short", day: "numeric" });
};

const Transactions = () => {
    const [transactionModalOpen, setTransactionModalOpen] = useState(false);
    const [categoryModalOpen, setCategoryModalOpen] = useState(false);
    const [deleteModalOpen, setDeleteModalOpen] = useState(false);
    const [selectedTransaction, setSelectedTransaction] = useState<TransactionResponse | null>(null);
    const [categoryFilter, setCategoryFilter] = useState("all");
    const [typeFilter, setTypeFilter] = useState("all");

    const { data: transactions = [], isLoading, error } = useQuery({
        queryKey: ["transactions", PLACEHOLDER_USER_ID],
        queryFn: ({ signal }) => getTransactions(PLACEHOLDER_USER_ID, signal),
    });

    const { data: categories = [] } = useQuery({
        queryKey: ["categories"],
        queryFn: () => getCategories() as Promise<Category[]>,
    });

    const filteredTransactions = useMemo(() => {
        return transactions.filter((t) => {
            if (categoryFilter !== "all" && t.category !== categoryFilter) return false;
            if (typeFilter !== "all" && t.type !== typeFilter) return false;
            return true;
        });
    }, [transactions, categoryFilter, typeFilter]);

    const openCreateModal = () => {
        setSelectedTransaction(null);
        setTransactionModalOpen(true);
    };

    const openEditModal = (transaction: TransactionResponse) => {
        setSelectedTransaction(transaction);
        setTransactionModalOpen(true);
    };

    const openDeleteModal = (transaction: TransactionResponse) => {
        setSelectedTransaction(transaction);
        setDeleteModalOpen(true);
    };

    const handleExport = async () => {
        try {
            const data = await exportTransactions("00000000-0000-0000-0000-000000000000");
            downloadCSV(data, "transactions.csv");
        } catch (err) {
            console.error("Export failed", err);
        }
    };

    return (
        <main className="transactions-container">
            <SideBar page="transactions"/>
            <div className="transaction-content">
                <Header title="Olá, Gabriel. Você está no menu de Transações. Aqui você encontra suas despesas e receitas passadas, além de poder cadastrar novos dados de despesas e receitas."/>

                <Box sx={{ display: "flex", gap: 2, marginBottom: 3, flexWrap: "wrap", alignItems: "center" }}>
                    <Button
                        variant="contained"
                        startIcon={<FiPlusCircle />}
                        onClick={openCreateModal}
                    >
                        Add Transaction
                    </Button>
                    <Button
                        variant="outlined"
                        startIcon={<FiTag />}
                        onClick={() => setCategoryModalOpen(true)}
                        sx={{ color: "var(--text-color)", borderColor: "var(--text-color)" }}
                    >
                        Create Category
                    </Button>
                    <Button
                        variant="outlined"
                        startIcon={<FiDownload />}
                        onClick={handleExport}
                        sx={{ color: "var(--text-color)", borderColor: "var(--text-color)" }}
                    >
                        Export CSV
                    </Button>
                    <Box sx={{ display: "flex", gap: 2, marginLeft: "auto" }}>
                        <FormControl size="small" sx={{ minWidth: 150 }}>
                            <InputLabel sx={{ color: "var(--text-color)" }}>Category</InputLabel>
                            <Select
                                value={categoryFilter}
                                onChange={(e) => setCategoryFilter(e.target.value)}
                                label="Category"
                                sx={{
                                    color: "var(--text-color)",
                                    "& .MuiOutlinedInput-notchedOutline": { borderColor: "var(--text-color)" },
                                    "& .MuiSvgIcon-root": { color: "var(--text-color)" },
                                }}
                            >
                                <MenuItem value="all">All Categories</MenuItem>
                                {categories.map((cat) => (
                                    <MenuItem key={cat.id} value={cat.name}>{cat.name}</MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                        <FormControl size="small" sx={{ minWidth: 120 }}>
                            <InputLabel sx={{ color: "var(--text-color)" }}>Type</InputLabel>
                            <Select
                                value={typeFilter}
                                onChange={(e) => setTypeFilter(e.target.value)}
                                label="Type"
                                sx={{
                                    color: "var(--text-color)",
                                    "& .MuiOutlinedInput-notchedOutline": { borderColor: "var(--text-color)" },
                                    "& .MuiSvgIcon-root": { color: "var(--text-color)" },
                                }}
                            >
                                <MenuItem value="all">All Types</MenuItem>
                                <MenuItem value="DEBIT">Debit</MenuItem>
                                <MenuItem value="CREDIT">Credit</MenuItem>
                            </Select>
                        </FormControl>
                    </Box>
                </Box>

                {isLoading ? (
                    <Box sx={{ display: "flex", justifyContent: "center", padding: 4 }}>
                        <CircularProgress />
                    </Box>
                ) : error ? (
                    <Typography color="error">Failed to load transactions.</Typography>
                ) : transactions.length === 0 ? (
                    <Paper sx={{ padding: 4, textAlign: "center", backgroundColor: "var(--primary-color)" }}>
                        <Typography sx={{ color: "var(--text-color)" }}>No transactions found. Click "Add Transaction" to create one.</Typography>
                    </Paper>
                ) : filteredTransactions.length === 0 ? (
                    <Paper sx={{ padding: 4, textAlign: "center", backgroundColor: "var(--primary-color)" }}>
                        <Typography sx={{ color: "var(--text-color)" }}>No transactions match the selected filters.</Typography>
                    </Paper>
                ) : (
                    <TableContainer component={Paper} sx={{ backgroundColor: "var(--primary-color)" }}>
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Name</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Notes</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Amount</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Date</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Category</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Type</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }} align="right">Actions</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {filteredTransactions.map((t) => (
                                    <TableRow key={t.id}>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{t.name}</TableCell>
                                        <TableCell sx={{ color: "var(--text-color)", maxWidth: 200, overflow: "hidden", textOverflow: "ellipsis", whiteSpace: "nowrap" }}>
                                            <Tooltip title={t.description} arrow>
                                                <span>{t.description || "—"}</span>
                                            </Tooltip>
                                        </TableCell>
                                        <TableCell sx={{ color: t.type === "DEBIT" ? "#f44336" : "#4caf50", fontWeight: "bold" }}>
                                            {formatAmount(t.amount, t.type)}
                                        </TableCell>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{formatDate(t.paymentDate)}</TableCell>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{t.category}</TableCell>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{t.type}</TableCell>
                                        <TableCell align="right">
                                            <IconButton onClick={() => openEditModal(t)} size="small" sx={{ color: "var(--text-color)" }}>
                                                <FiEdit2 />
                                            </IconButton>
                                            <IconButton onClick={() => openDeleteModal(t)} size="small" sx={{ color: "#f44336" }}>
                                                <FiTrash2 />
                                            </IconButton>
                                        </TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                )}
            </div>

            <TransactionModal
                open={transactionModalOpen}
                onClose={() => setTransactionModalOpen(false)}
                transaction={selectedTransaction}
            />
            <CategoryModal
                open={categoryModalOpen}
                onClose={() => setCategoryModalOpen(false)}
            />
            <DeleteConfirmationModal
                open={deleteModalOpen}
                onClose={() => setDeleteModalOpen(false)}
                transaction={selectedTransaction}
            />
        </main>
    );
};

export { Transactions };
