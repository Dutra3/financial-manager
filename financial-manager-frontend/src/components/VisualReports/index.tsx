import { Box, Typography, Grid, Paper } from "@mui/material";
import {
    PieChart, Pie, Cell, Tooltip, Legend,
    LineChart, Line, XAxis, YAxis, CartesianGrid, ResponsiveContainer,
} from "recharts";
import { TransactionResponse } from "../../api/transactionApi";
import { MonthlyData } from "../../hooks/useMonthlyData";
import { Currency } from "../../utils/currency";
import { convertCurrency } from "../../utils/currency";

interface VisualReportsProps {
    transactions: TransactionResponse[];
    monthlyData: MonthlyData[];
    rates: Record<string, number> | undefined;
    currency: Currency;
}

const PIE_COLORS = ["#8884d8", "#83a6ed", "#8dd1e1", "#82ca9d", "#a8d08d", "#d4a96a", "#e0a8a8", "#d884a8"];

const LOCALE: Record<string, string> = {
    USD: "en-US", BRL: "pt-BR", EUR: "de-DE", CAD: "en-CA", GBP: "en-GB",
};

const formatCurrency = (amount: number, currency: string) => {
    const locale = LOCALE[currency] || "en-US";
    return new Intl.NumberFormat(locale, {
        style: "currency", currency, minimumFractionDigits: 2,
    }).format(amount);
};

const buildPieData = (transactions: TransactionResponse[], rates: Record<string, number> | undefined, currency: Currency) => {
    const map = new Map<string, number>();
    transactions
        .filter((t) => t.type === "DEBIT")
        .forEach((t) => {
            const amount = convertCurrency(t.amount || 0, rates, currency);
            map.set(t.category, (map.get(t.category) || 0) + amount);
        });
    return Array.from(map.entries())
        .map(([name, value]) => ({ name, value }))
        .sort((a, b) => b.value - a.value);
};

const buildLineData = (monthlyData: MonthlyData[], rates: Record<string, number> | undefined, currency: Currency) => {
    return monthlyData.map((m) => ({
        month: m.month,
        income: convertCurrency(m.income, rates, currency),
        expenses: convertCurrency(m.expenses, rates, currency),
    }));
};

const VisualReports: React.FC<VisualReportsProps> = ({ transactions, monthlyData, rates, currency }) => {
    const pieData = buildPieData(transactions, rates, currency);
    const lineData = buildLineData(monthlyData, rates, currency);

    const hasPieData = pieData.length > 0;
    const hasLineData = lineData.length > 0;

    return (
        <Box sx={{ marginBottom: 3 }}>
            <Typography variant="h6" sx={{ marginBottom: 2, color: "var(--text-color)" }}>
                Visual Reports
            </Typography>
            <Grid container spacing={3}>
                {/* Pie Chart - Expenses by Category */}
                <Grid item xs={12} md={6}>
                    <Paper sx={{ padding: 2, backgroundColor: "var(--primary-color)" }}>
                        <Typography variant="subtitle2" sx={{ marginBottom: 1, color: "var(--text-color)" }}>
                            Expenses by Category
                        </Typography>
                        {hasPieData ? (
                            <ResponsiveContainer width="100%" height={300}>
                                <PieChart>
                                    <Pie
                                        data={pieData}
                                        dataKey="value"
                                        nameKey="name"
                                        cx="50%"
                                        cy="50%"
                                        outerRadius={100}
                                        label={(entry: any) => entry.name}
                                    >
                                        {pieData.map((_, i) => (
                                            <Cell key={i} fill={PIE_COLORS[i % PIE_COLORS.length]} />
                                        ))}
                                    </Pie>
                                    <Tooltip formatter={(value: any) => formatCurrency(Number(value), currency)} />
                                </PieChart>
                            </ResponsiveContainer>
                        ) : (
                            <Typography sx={{ color: "var(--text-color)", textAlign: "center", padding: 4 }}>
                                No expense data to display.
                            </Typography>
                        )}
                    </Paper>
                </Grid>

                {/* Line Chart - Income vs Expenses Trend */}
                <Grid item xs={12} md={6}>
                    <Paper sx={{ padding: 2, backgroundColor: "var(--primary-color)" }}>
                        <Typography variant="subtitle2" sx={{ marginBottom: 1, color: "var(--text-color)" }}>
                            Income vs Expenses Trend
                        </Typography>
                        {hasLineData ? (
                            <ResponsiveContainer width="100%" height={300}>
                                <LineChart data={lineData} margin={{ top: 5, right: 20, left: 20, bottom: 5 }}>
                                    <CartesianGrid strokeDasharray="3 3" stroke="rgba(128,128,128,0.3)" />
                                    <XAxis dataKey="month" stroke="var(--text-color)" />
                                    <YAxis
                                        stroke="var(--text-color)"
                                        tickFormatter={(v: number) => formatCurrency(v, currency)}
                                        width={105}
                                    />
                                    <Tooltip formatter={(value: any) => formatCurrency(Number(value), currency)} />
                                    <Legend />
                                    <Line type="monotone" dataKey="income" stroke="#4caf50" name="Income" />
                                    <Line type="monotone" dataKey="expenses" stroke="#f44336" name="Expenses" />
                                </LineChart>
                            </ResponsiveContainer>
                        ) : (
                            <Typography sx={{ color: "var(--text-color)", textAlign: "center", padding: 4 }}>
                                No monthly data to display.
                            </Typography>
                        )}
                    </Paper>
                </Grid>
            </Grid>
        </Box>
    );
};

export { VisualReports };
