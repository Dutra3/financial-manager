import { Box, Typography, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Chip } from "@mui/material";
import { Link } from "react-router-dom";
import { TransactionResponse } from "../../api/transactionApi";
import { Currency } from "../../utils/currency";
import { convertCurrency } from "../../utils/currency";

interface RecentTransactionsProps {
    transactions: TransactionResponse[];
    loading: boolean;
    error: string | null;
    rates: Record<string, number> | undefined;
    currency: Currency;
}

const LOCALE: Record<string, string> = {
    USD: "en-US",
    BRL: "pt-BR",
    EUR: "de-DE",
    CAD: "en-CA",
    GBP: "en-GB",
};

const CATEGORY_COLORS = ["#8884d8", "#83a6ed", "#8dd1e1", "#82ca9d", "#a8d08d", "#d4a96a", "#e0a8a8", "#d884a8"];

const categoryColor = (name: string) => {
    let hash = 0;
    for (let i = 0; i < name.length; i++) hash = name.charCodeAt(i) + ((hash << 5) - hash);
    return CATEGORY_COLORS[Math.abs(hash) % CATEGORY_COLORS.length];
};

const formatCurrency = (amount: number, currency: string) => {
    const locale = LOCALE[currency] || "en-US";
    return new Intl.NumberFormat(locale, {
        style: "currency",
        currency,
        minimumFractionDigits: 2,
    }).format(amount);
};

const formatDate = (dateStr: string) => {
    return new Date(dateStr).toLocaleDateString("en-US", {
        month: "short",
        day: "numeric",
        year: "numeric",
    });
};

const RecentTransactions: React.FC<RecentTransactionsProps> = ({ transactions, loading, error, rates, currency }) => {
    if (loading) return <Typography>Loading recent transactions...</Typography>;
    if (error) return <Typography color="error">{error}</Typography>;
    if (transactions.length === 0) return <Typography>No recent transactions.</Typography>;

    const recent = [...transactions]
        .sort((a, b) => new Date(b.paymentDate).getTime() - new Date(a.paymentDate).getTime())
        .slice(0, 5);

    return (
        <Box sx={{ marginBottom: 3 }}>
            <Box sx={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: 1 }}>
                <Typography variant="h6" sx={{ color: "var(--text-color)" }}>
                    Recent Transactions
                </Typography>
                <Link to="/transactions" style={{ textDecoration: "none" }}>
                    <Typography sx={{ color: "var(--secondary-color)", fontSize: "0.9rem", fontWeight: 600, "&:hover": { textDecoration: "underline" } }}>
                        View all →
                    </Typography>
                </Link>
            </Box>
            <TableContainer component={Paper} sx={{ backgroundColor: "var(--primary-color)" }}>
                <Table size="small">
                    <TableHead>
                        <TableRow sx={{ "& th": { backgroundColor: "var(--hover-color)" } }}>
                            <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Name</TableCell>
                            <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Category</TableCell>
                            <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Date</TableCell>
                            <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }} align="right">Amount</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {recent.map((t) => {
                            const amount = convertCurrency(t.amount, rates, currency);
                            const isCredit = t.type === "CREDIT";
                            return (
                                <TableRow
                                    key={t.id}
                                    sx={{
                                        "&:nth-of-type(odd)": { backgroundColor: "rgba(128,128,128,0.04)" },
                                        "&:hover": { backgroundColor: "var(--hover-color)" },
                                        "& td": { borderBottom: "1px solid var(--border-color)" },
                                    }}
                                >
                                    <TableCell sx={{ color: "var(--text-color)" }}>{t.name}</TableCell>
                                    <TableCell>
                                        <Chip
                                            label={t.category}
                                            size="small"
                                            sx={{
                                                backgroundColor: categoryColor(t.category),
                                                color: "#fff",
                                                fontWeight: 600,
                                                fontSize: "0.75rem",
                                            }}
                                        />
                                    </TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", opacity: 0.8 }}>{formatDate(t.paymentDate)}</TableCell>
                                    <TableCell sx={{ color: isCredit ? "#4caf50" : "#f44336", fontWeight: 600 }} align="right">
                                        {isCredit ? "+" : "-"}{formatCurrency(amount, currency)}
                                    </TableCell>
                                </TableRow>
                            );
                        })}
                    </TableBody>
                </Table>
            </TableContainer>
        </Box>
    );
};

export { RecentTransactions };
