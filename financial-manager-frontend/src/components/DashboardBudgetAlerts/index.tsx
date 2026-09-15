import { useQuery } from "@tanstack/react-query";
import { getBudgetAlerts, BudgetAlert } from "../../api/budgetApi";
import { Box, Typography, Paper, Alert } from "@mui/material";
import { FiAlertCircle } from "react-icons/fi";

const PLACEHOLDER_USER_ID = "00000000-0000-0000-0000-000000000000";

const formatCurrency = (value: number) => `$${value.toLocaleString("en-US", { minimumFractionDigits: 2, maximumFractionDigits: 2 })}`;

const DashboardBudgetAlerts = () => {
    const now = new Date();
    const currentMonth = now.getMonth() + 1;
    const currentYear = now.getFullYear();

    const { data: alerts = [] } = useQuery<BudgetAlert[]>({
        queryKey: ["budgetAlerts", PLACEHOLDER_USER_ID, currentMonth, currentYear],
        queryFn: () => getBudgetAlerts(PLACEHOLDER_USER_ID, currentMonth, currentYear),
        retry: false,
    });

    const warningAlerts = alerts.filter(a => a.status === "WARNING" || a.status === "EXCEEDED");

    if (warningAlerts.length === 0) {
        return null;
    }

    return (
        <Paper sx={{ padding: 3, backgroundColor: "var(--primary-color)" }}>
            <Box sx={{ display: "flex", alignItems: "center", gap: 1, marginBottom: 2 }}>
                <FiAlertCircle color="#ff9800" />
                <Typography variant="h6" sx={{ color: "var(--text-color)" }}>Budget Alerts</Typography>
            </Box>

            <Box sx={{ display: "flex", flexDirection: "column", gap: 1 }}>
                {warningAlerts.map((alert) => (
                    <Alert
                        key={alert.categoryId}
                        severity={alert.status === "EXCEEDED" ? "error" : "warning"}
                        icon={false}
                    >
                        <strong>{alert.categoryName}</strong>: Spent {formatCurrency(alert.spentAmount)} of {formatCurrency(alert.budgetAmount)} ({alert.percentage.toFixed(1)}%)
                        {alert.status === "EXCEEDED" ? " — Budget exceeded!" : " — Approaching limit!"}
                    </Alert>
                ))}
            </Box>
        </Paper>
    );
};

export { DashboardBudgetAlerts };
