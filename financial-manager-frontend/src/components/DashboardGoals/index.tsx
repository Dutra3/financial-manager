import { useQuery } from "@tanstack/react-query";
import { getGoals, getCurrentBalance, Goal } from "../../api/goalApi";
import { Box, Typography, Paper, LinearProgress, CircularProgress, Alert } from "@mui/material";
import { FiFlag } from "react-icons/fi";

const PLACEHOLDER_USER_ID = "00000000-0000-0000-0000-000000000000";

const formatCurrency = (value: number) => `$${value.toLocaleString("en-US", { minimumFractionDigits: 2, maximumFractionDigits: 2 })}`;

const DashboardGoals = () => {
    const { data: goals = [] } = useQuery<Goal[]>({
        queryKey: ["goals", PLACEHOLDER_USER_ID],
        queryFn: () => getGoals(PLACEHOLDER_USER_ID),
        retry: false,
    });

    const { data: currentBalance = 0, isLoading } = useQuery<number>({
        queryKey: ["currentBalance", PLACEHOLDER_USER_ID],
        queryFn: () => getCurrentBalance(PLACEHOLDER_USER_ID),
        retry: false,
    });

    if (isLoading && goals.length === 0) {
        return (
            <Paper sx={{ padding: 3, backgroundColor: "var(--primary-color)" }}>
                <Box sx={{ display: "flex", alignItems: "center", gap: 1, marginBottom: 2 }}>
                    <FiFlag color="var(--text-color)" />
                    <Typography variant="h6" sx={{ color: "var(--text-color)" }}>Goals Progress</Typography>
                </Box>
                <CircularProgress size={24} />
            </Paper>
        );
    }

    if (goals.length === 0) {
        return null;
    }

    const achievedGoals = goals.filter(g => {
        const progress = currentBalance > 0 && g.targetAmount > 0
            ? (currentBalance / g.targetAmount) * 100 : 0;
        return progress >= 100;
    });

    return (
        <Paper sx={{ padding: 3, backgroundColor: "var(--primary-color)" }}>
            <Box sx={{ display: "flex", alignItems: "center", gap: 1, marginBottom: 2 }}>
                <FiFlag color="var(--text-color)" />
                <Typography variant="h6" sx={{ color: "var(--text-color)" }}>Goals Progress</Typography>
            </Box>

            {achievedGoals.length > 0 && (
                <Alert severity="success" sx={{ marginBottom: 2 }} icon={false}>
                    Congratulations! {achievedGoals.length} goal{achievedGoals.length > 1 ? "s" : ""} achieved!
                </Alert>
            )}

            <Box sx={{ display: "flex", flexDirection: "column", gap: 2 }}>
                {goals.slice(0, 3).map((goal) => {
                    const progress = currentBalance > 0 && goal.targetAmount > 0
                        ? Math.min((currentBalance / goal.targetAmount) * 100, 100) : 0;
                    const isAchieved = progress >= 100;
                    return (
                        <Box key={goal.id}>
                            <Box sx={{ display: "flex", justifyContent: "space-between", marginBottom: 0.5 }}>
                                <Typography sx={{ color: "var(--text-color)", fontSize: "0.9rem" }}>
                                    {goal.name} {isAchieved && "✓"}
                                </Typography>
                                <Typography sx={{ color: "var(--text-color)", fontSize: "0.9rem", fontWeight: "bold" }}>
                                    {progress.toFixed(1)}%
                                </Typography>
                            </Box>
                            <LinearProgress
                                variant="determinate"
                                value={progress}
                                sx={{
                                    height: 8,
                                    borderRadius: 4,
                                    backgroundColor: "rgba(255,255,255,0.1)",
                                    "& .MuiLinearProgress-bar": {
                                        backgroundColor: isAchieved ? "#4caf50" : "#1976d2",
                                    },
                                }}
                            />
                            <Typography sx={{ color: "var(--text-color)", fontSize: "0.8rem", opacity: 0.7, marginTop: 0.5 }}>
                                {formatCurrency(currentBalance)} / {formatCurrency(goal.targetAmount)}
                            </Typography>
                        </Box>
                    );
                })}
            </Box>
        </Paper>
    );
};

export { DashboardGoals };
