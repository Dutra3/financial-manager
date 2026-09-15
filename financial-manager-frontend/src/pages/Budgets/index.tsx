import { useState } from "react";
import { Header } from "../../components/Header";
import { SideBar } from "../../components/SideBar";
import { BudgetModal } from "../../components/BudgetModal";
import { getBudgets, createBudget, deleteBudget, getBudgetAlerts, Budget as BudgetType, BudgetAlert as BudgetAlertType } from "../../api/budgetApi";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { Box, Typography, Button, Paper, CircularProgress, IconButton, Tooltip, Alert, LinearProgress } from "@mui/material";
import { FiPlusCircle, FiTrash2 } from "react-icons/fi";
import "./Budgets.css";

const PLACEHOLDER_USER_ID = "00000000-0000-0000-0000-000000000000";

const formatCurrency = (value: number) => `$${value.toLocaleString("en-US", { minimumFractionDigits: 2, maximumFractionDigits: 2 })}`;

const Budgets = () => {
    const queryClient = useQueryClient();
    const [modalOpen, setModalOpen] = useState(false);

    const now = new Date();
    const currentMonth = now.getMonth() + 1;
    const currentYear = now.getFullYear();

    const { data: budgets = [], isLoading } = useQuery<BudgetType[]>({
        queryKey: ["budgets", PLACEHOLDER_USER_ID],
        queryFn: () => getBudgets(PLACEHOLDER_USER_ID),
        retry: false,
    });

    const { data: alerts = [] } = useQuery<BudgetAlertType[]>({
        queryKey: ["budgetAlerts", PLACEHOLDER_USER_ID, currentMonth, currentYear],
        queryFn: () => getBudgetAlerts(PLACEHOLDER_USER_ID, currentMonth, currentYear),
        retry: false,
    });

    const createMutation = useMutation({
        mutationFn: (b: BudgetType) => createBudget(b),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["budgets"] });
            queryClient.invalidateQueries({ queryKey: ["budgetAlerts"] });
        },
    });

    const deleteMutation = useMutation({
        mutationFn: (id: string) => deleteBudget(id),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["budgets"] });
            queryClient.invalidateQueries({ queryKey: ["budgetAlerts"] });
        },
    });

    const handleCreate = async (categoryId: string, amount: number, month: number, year: number) => {
        await createMutation.mutateAsync({
            id: "", categoryId, amount, month, year
        } as BudgetType);
    };

    const warningAlerts = alerts.filter(a => a.status === "WARNING" || a.status === "EXCEEDED");

    return (
        <main className="budgets-container">
            <SideBar page="budgets"/>
            <div className="budgets-content">
                <Header title="Olá, Gabriel. Você está na aba de Orçamentos. Aqui você define limites de gastos por categoria."/>

                {warningAlerts.length > 0 && (
                    <Box sx={{ marginBottom: 3, display: "flex", flexDirection: "column", gap: 1 }}>
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
                )}

                <Box sx={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: 2 }}>
                    <Typography variant="h5" sx={{ color: "var(--text-color)" }}>
                        Budgets
                    </Typography>
                    <Button
                        variant="contained"
                        startIcon={<FiPlusCircle />}
                        onClick={() => setModalOpen(true)}
                    >
                        Add Budget
                    </Button>
                </Box>

                {isLoading ? (
                    <Box sx={{ display: "flex", justifyContent: "center", padding: 4 }}>
                        <CircularProgress />
                    </Box>
                ) : budgets.length === 0 ? (
                    <Paper sx={{ padding: 4, textAlign: "center", backgroundColor: "var(--primary-color)" }}>
                        <Typography sx={{ color: "var(--text-color)" }}>No budgets found. Click "Add Budget" to create one.</Typography>
                    </Paper>
                ) : (
                    <Box sx={{ display: "flex", flexDirection: "column", gap: 2 }}>
                        {budgets.map((budget) => {
                            const alert = alerts.find(a => a.categoryId === budget.categoryId
                                && a.budgetAmount === budget.amount);
                            const spent = alert ? alert.spentAmount : 0;
                            const percentage = budget.amount > 0 ? Math.min((spent / budget.amount) * 100, 100) : 0;
                            return (
                                <Paper key={budget.id} sx={{ padding: 3, backgroundColor: "var(--primary-color)" }}>
                                    <Box sx={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
                                        <Box>
                                            <Typography variant="h6" sx={{ color: "var(--text-color)" }}>
                                                {budget.categoryName || "Unknown Category"}
                                            </Typography>
                                            <Typography sx={{ color: "var(--text-color)", opacity: 0.7, fontSize: "0.9rem" }}>
                                                {budget.month}/{budget.year}
                                            </Typography>
                                        </Box>
                                        <Box sx={{ display: "flex", alignItems: "center", gap: 2 }}>
                                            <Typography variant="h6" sx={{ color: "var(--text-color)" }}>
                                                {formatCurrency(budget.amount)}
                                            </Typography>
                                            <Tooltip title="Delete budget">
                                                <IconButton onClick={() => deleteMutation.mutate(budget.id)} size="small">
                                                    <FiTrash2 color="var(--text-color)" />
                                                </IconButton>
                                            </Tooltip>
                                        </Box>
                                    </Box>
                                    {alert && (
                                        <Box sx={{ marginTop: 2 }}>
                                            <Box sx={{ display: "flex", justifyContent: "space-between", marginBottom: 0.5 }}>
                                                <Typography sx={{ color: "var(--text-color)", fontSize: "0.85rem" }}>
                                                    Spent: {formatCurrency(spent)} / {formatCurrency(budget.amount)}
                                                </Typography>
                                                <Typography sx={{ color: "var(--text-color)", fontSize: "0.85rem", fontWeight: "bold" }}>
                                                    {percentage.toFixed(1)}%
                                                </Typography>
                                            </Box>
                                            <LinearProgress
                                                variant="determinate"
                                                value={percentage}
                                                sx={{
                                                    height: 10,
                                                    borderRadius: 5,
                                                    backgroundColor: "rgba(255,255,255,0.1)",
                                                    "& .MuiLinearProgress-bar": {
                                                        backgroundColor: percentage >= 100 ? "#f44336" : percentage >= 80 ? "#ff9800" : "#4caf50",
                                                    },
                                                }}
                                            />
                                        </Box>
                                    )}
                                </Paper>
                            );
                        })}
                    </Box>
                )}
            </div>

            <BudgetModal
                open={modalOpen}
                onClose={() => setModalOpen(false)}
                onSubmit={handleCreate}
            />
        </main>
    );
};

export { Budgets };
