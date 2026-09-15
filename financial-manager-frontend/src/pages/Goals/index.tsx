import { useState } from "react";
import { Header } from "../../components/Header";
import { SideBar } from "../../components/SideBar";
import { GoalModal } from "../../components/GoalModal";
import { getGoals, createGoal, deleteGoal, getCurrentBalance, Goal as GoalType } from "../../api/goalApi";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { Box, Typography, Button, Paper, CircularProgress, LinearProgress, IconButton, Tooltip } from "@mui/material";
import { FiPlusCircle, FiTrash2 } from "react-icons/fi";
import "./Goals.css";

const PLACEHOLDER_USER_ID = "00000000-0000-0000-0000-000000000000";

const formatCurrency = (value: number) => `$${value.toLocaleString("en-US", { minimumFractionDigits: 2, maximumFractionDigits: 2 })}`;
const formatDate = (dateStr: string) => dateStr ? new Date(dateStr).toLocaleDateString("en-US", { year: "numeric", month: "short", day: "numeric" }) : "—";

const Goals = () => {
    const queryClient = useQueryClient();
    const [modalOpen, setModalOpen] = useState(false);

    const { data: goals = [], isLoading: goalsLoading } = useQuery<GoalType[]>({
        queryKey: ["goals", PLACEHOLDER_USER_ID],
        queryFn: () => getGoals(PLACEHOLDER_USER_ID),
        retry: false,
    });

    const { data: currentBalance = 0, isLoading: balanceLoading } = useQuery<number>({
        queryKey: ["currentBalance", PLACEHOLDER_USER_ID],
        queryFn: () => getCurrentBalance(PLACEHOLDER_USER_ID),
        retry: false,
    });

    const createMutation = useMutation({
        mutationFn: (goal: GoalType) => createGoal(goal),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["goals"] });
            queryClient.invalidateQueries({ queryKey: ["currentBalance"] });
        },
    });

    const deleteMutation = useMutation({
        mutationFn: (id: string) => deleteGoal(id),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["goals"] });
        },
    });

    const handleCreate = async (name: string, description: string, targetDate: string | null, targetAmount: number, isAchieved: boolean) => {
        await createMutation.mutateAsync({
            id: "", name, description, targetDate: targetDate || "", targetAmount, isAchieved
        } as GoalType);
    };

    return (
        <main className="goals-container">
            <SideBar page="goals"/>
            <div className="goals-content">
                <Header title="Olá, Gabriel. Você está na aba de Metas. Aqui você acompanha seu saldo atual e suas metas financeiras."/>

                <Paper sx={{ padding: 3, marginBottom: 3, backgroundColor: "var(--primary-color)" }}>
                    <Typography variant="h5" sx={{ color: "var(--text-color)", marginBottom: 1 }}>
                        Current Balance
                    </Typography>
                    {balanceLoading ? (
                        <CircularProgress size={28} />
                    ) : (
                        <Typography variant="h3" sx={{ color: "var(--text-color)", fontWeight: "bold" }}>
                            {formatCurrency(currentBalance)}
                        </Typography>
                    )}
                </Paper>

                <Box sx={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: 2 }}>
                    <Typography variant="h5" sx={{ color: "var(--text-color)" }}>
                        Goals
                    </Typography>
                    <Button
                        variant="contained"
                        startIcon={<FiPlusCircle />}
                        onClick={() => setModalOpen(true)}
                    >
                        Add Goal
                    </Button>
                </Box>

                {goalsLoading ? (
                    <Box sx={{ display: "flex", justifyContent: "center", padding: 4 }}>
                        <CircularProgress />
                    </Box>
                ) : goals.length === 0 ? (
                    <Paper sx={{ padding: 4, textAlign: "center", backgroundColor: "var(--primary-color)" }}>
                        <Typography sx={{ color: "var(--text-color)" }}>No goals found. Click "Add Goal" to create one.</Typography>
                    </Paper>
                ) : (
                    <Box sx={{ display: "flex", flexDirection: "column", gap: 2 }}>
                        {goals.map((goal) => {
                            const progress = currentBalance > 0 && goal.targetAmount > 0
                                ? Math.min((currentBalance / goal.targetAmount) * 100, 100)
                                : 0;
                            return (
                                <Paper key={goal.id} sx={{ padding: 3, backgroundColor: "var(--primary-color)" }}>
                                    <Box sx={{ display: "flex", justifyContent: "space-between", alignItems: "flex-start" }}>
                                        <Box>
                                            <Typography variant="h6" sx={{ color: "var(--text-color)" }}>
                                                {goal.name} {goal.isAchieved && "✓"}
                                            </Typography>
                                            {goal.description && (
                                                <Typography sx={{ color: "var(--text-color)", opacity: 0.7, fontSize: "0.9rem" }}>
                                                    {goal.description}
                                                </Typography>
                                            )}
                                            <Typography sx={{ color: "var(--text-color)", marginTop: 1 }}>
                                                Target: {formatCurrency(goal.targetAmount)} · Due: {formatDate(goal.targetDate)}
                                            </Typography>
                                        </Box>
                                        <Tooltip title="Delete goal">
                                            <IconButton onClick={() => deleteMutation.mutate(goal.id)} size="small">
                                                <FiTrash2 color="var(--text-color)" />
                                            </IconButton>
                                        </Tooltip>
                                    </Box>
                                    <Box sx={{ marginTop: 2 }}>
                                        <Box sx={{ display: "flex", justifyContent: "space-between", marginBottom: 0.5 }}>
                                            <Typography sx={{ color: "var(--text-color)", fontSize: "0.85rem" }}>
                                                Progress: {formatCurrency(currentBalance)} / {formatCurrency(goal.targetAmount)}
                                            </Typography>
                                            <Typography sx={{ color: "var(--text-color)", fontSize: "0.85rem", fontWeight: "bold" }}>
                                                {progress.toFixed(1)}%
                                            </Typography>
                                        </Box>
                                        <LinearProgress
                                            variant="determinate"
                                            value={progress}
                                            sx={{
                                                height: 10,
                                                borderRadius: 5,
                                                backgroundColor: "rgba(255,255,255,0.1)",
                                                "& .MuiLinearProgress-bar": {
                                                    backgroundColor: progress >= 100 ? "#4caf50" : "var(--accent-color, #1976d2)",
                                                },
                                            }}
                                        />
                                    </Box>
                                </Paper>
                            );
                        })}
                    </Box>
                )}
            </div>

            <GoalModal
                open={modalOpen}
                onClose={() => setModalOpen(false)}
                onSubmit={handleCreate}
            />
        </main>
    );
};

export { Goals };
