import { Box, Typography, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from "@mui/material";
import { MonthlyData } from "../../hooks/useMonthlyData";
import { Currency } from "../../utils/currency";
import { convertCurrency } from "../../utils/currency";

interface MonthlyOverviewProps {
    data: MonthlyData[];
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

const MonthlyOverview: React.FC<MonthlyOverviewProps> = ({ data, loading, error, rates, currency }) => {
    if (loading) return <Typography>Loading monthly data...</Typography>;
    if (error) return <Typography color="error">{error}</Typography>;
    if (data.length === 0) return <Typography>No transaction data available.</Typography>;

    return (
        <Box sx={{ marginBottom: 3 }}>
            <Typography variant="h6" sx={{ marginBottom: 1, color: "var(--text-color)" }}>
                Monthly Data Overview
            </Typography>
            <TableContainer component={Paper} sx={{ backgroundColor: "var(--primary-color)" }}>
                <Table size="small">
                    <TableHead>
                        <TableRow>
                            <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Month</TableCell>
                            <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }} align="right">Income</TableCell>
                            <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }} align="right">Expenses</TableCell>
                            <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }} align="right">Net</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {data.map((row) => {
                            const income = convertCurrency(row.income, rates, currency);
                            const expenses = convertCurrency(row.expenses, rates, currency);
                            const net = income - expenses;
                            return (
                                <TableRow key={row.month}>
                                    <TableCell sx={{ color: "var(--text-color)" }}>{row.month}</TableCell>
                                    <TableCell sx={{ color: "#4caf50" }} align="right">{formatCurrency(income, currency)}</TableCell>
                                    <TableCell sx={{ color: "#f44336" }} align="right">{formatCurrency(expenses, currency)}</TableCell>
                                    <TableCell sx={{ color: net >= 0 ? "#4caf50" : "#f44336" }} align="right">
                                        {formatCurrency(net, currency)}
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

export { MonthlyOverview };
