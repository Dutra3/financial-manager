import { Box, Typography, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from "@mui/material";
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
            <Typography variant="h6" sx={{ marginBottom: 1, color: "var(--text-color)" }}>
                Recent Transactions
            </Typography>
            <TableContainer component={Paper} sx={{ backgroundColor: "var(--primary-color)" }}>
                <Table size="small">
                    <TableHead>
                        <TableRow>
                            <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Name</TableCell>
                            <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Category</TableCell>
                            <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Date</TableCell>
                            <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }} align="right">Amount</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {recent.map((t) => {
                            const amount = convertCurrency(t.amount, rates, currency);
                            return (
                                <TableRow key={t.id}>
                                    <TableCell sx={{ color: "var(--text-color)" }}>{t.name}</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)" }}>{t.category}</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)" }}>{formatDate(t.paymentDate)}</TableCell>
                                    <TableCell sx={{ color: t.type === "CREDIT" ? "#4caf50" : "#f44336" }} align="right">
                                        {t.type === "CREDIT" ? "+" : "-"}{formatCurrency(amount, currency)}
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
