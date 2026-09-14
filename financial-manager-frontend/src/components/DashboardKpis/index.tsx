import { Box, Typography, Card as MuiCard, CardContent } from "@mui/material";
import type { KpisData } from "../../hooks/useDashboardKpis";
import { Currency } from "../../utils/currency";
import { convertCurrency } from "../../utils/currency";

interface DashboardKpisProps {
    kpis: KpisData;
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

const formatDelta = (delta: number, currency: string) => {
    const sign = delta >= 0 ? "+" : "";
    return `${sign}${formatCurrency(Math.abs(delta), currency)}`;
};

const DashboardKpis: React.FC<DashboardKpisProps> = ({ kpis, rates, currency }) => {
    const { savingsRate, avgMonthlyExpense, topCategory, monthComparison } = kpis;

    return (
        <Box sx={{ display: "flex", gap: 2, flexWrap: "wrap", marginBottom: 3 }}>
            {/* Savings Rate */}
            <MuiCard sx={{ backgroundColor: "var(--primary-color)", flex: "1 1 200px" }}>
                <CardContent>
                    <Typography variant="caption" sx={{ color: "var(--text-color)", opacity: 0.7 }}>
                        Savings Rate
                    </Typography>
                    <Typography variant="h5" sx={{ color: savingsRate >= 0 ? "#4caf50" : "#f44336" }}>
                        {savingsRate.toFixed(1)}%
                    </Typography>
                </CardContent>
            </MuiCard>

            {/* Average Monthly Expense */}
            <MuiCard sx={{ backgroundColor: "var(--primary-color)", flex: "1 1 200px" }}>
                <CardContent>
                    <Typography variant="caption" sx={{ color: "var(--text-color)", opacity: 0.7 }}>
                        Avg Monthly Expense
                    </Typography>
                    <Typography variant="h5" sx={{ color: "var(--text-color)" }}>
                        {formatCurrency(convertCurrency(avgMonthlyExpense, rates, currency), currency)}
                    </Typography>
                </CardContent>
            </MuiCard>

            {/* Top Category */}
            <MuiCard sx={{ backgroundColor: "var(--primary-color)", flex: "1 1 200px" }}>
                <CardContent>
                    <Typography variant="caption" sx={{ color: "var(--text-color)", opacity: 0.7 }}>
                        Top Spending Category
                    </Typography>
                    <Typography variant="h6" sx={{ color: "var(--text-color)" }}>
                        {topCategory ? topCategory.name : "—"}
                    </Typography>
                    {topCategory && (
                        <Typography variant="body2" sx={{ color: "var(--text-color)", opacity: 0.7 }}>
                            {formatCurrency(convertCurrency(topCategory.amount, rates, currency), currency)}
                        </Typography>
                    )}
                </CardContent>
            </MuiCard>

            {/* Month vs Month */}
            <MuiCard sx={{ backgroundColor: "var(--primary-color)", flex: "1 1 200px" }}>
                <CardContent>
                    <Typography variant="caption" sx={{ color: "var(--text-color)", opacity: 0.7 }}>
                        vs Last Month
                    </Typography>
                    {monthComparison ? (
                        <Box sx={{ display: "flex", flexDirection: "column", gap: 0.5 }}>
                            <Typography variant="body2" sx={{ color: "#4caf50" }}>
                                Income: {formatDelta(convertCurrency(monthComparison.incomeDelta, rates, currency), currency)}
                            </Typography>
                            <Typography variant="body2" sx={{ color: monthComparison.expensesDelta <= 0 ? "#4caf50" : "#f44336" }}>
                                Expenses: {formatDelta(convertCurrency(monthComparison.expensesDelta, rates, currency), currency)}
                            </Typography>
                        </Box>
                    ) : (
                        <Typography variant="body2" sx={{ color: "var(--text-color)" }}>—</Typography>
                    )}
                </CardContent>
            </MuiCard>
        </Box>
    );
};

export { DashboardKpis };
