import { Header } from "../../components/Header";
import { SideBar } from "../../components/SideBar";
import { ProfileModal } from "../../components/ProfileModal";
import { getProfile, updateProfile, Profile as ProfileType } from "../../api/profileApi";
import { getGoals, getCurrentBalance, Goal as GoalType } from "../../api/goalApi";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { useState } from "react";
import { Box, Typography, CircularProgress, Paper, LinearProgress } from "@mui/material";
import "./Profile.css";

const PLACEHOLDER_PROFILE_ID = "00000000-0000-0000-0000-000000000000";

const formatCurrency = (value: number) => `$${value.toLocaleString("en-US", { minimumFractionDigits: 2, maximumFractionDigits: 2 })}`;

const Profile = () => {
    const queryClient = useQueryClient();
    const [modalOpen, setModalOpen] = useState(false);

    const { data: profile, isLoading, error } = useQuery<ProfileType>({
        queryKey: ["profile", PLACEHOLDER_PROFILE_ID],
        queryFn: () => getProfile(PLACEHOLDER_PROFILE_ID),
        retry: false,
    });

    const { data: goals = [] } = useQuery<GoalType[]>({
        queryKey: ["goals", PLACEHOLDER_PROFILE_ID],
        queryFn: () => getGoals(PLACEHOLDER_PROFILE_ID),
        retry: false,
    });

    const { data: currentBalance = 0 } = useQuery<number>({
        queryKey: ["currentBalance", PLACEHOLDER_PROFILE_ID],
        queryFn: () => getCurrentBalance(PLACEHOLDER_PROFILE_ID),
        retry: false,
    });

    const updateMutation = useMutation({
        mutationFn: (p: ProfileType) => updateProfile(PLACEHOLDER_PROFILE_ID, p),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["profile"] });
            queryClient.invalidateQueries({ queryKey: ["currentBalance"] });
        },
    });

    const handleUpdate = async (p: ProfileType) => {
        await updateMutation.mutateAsync(p);
    };

    if (isLoading) {
        return (
            <main className="profile-container">
                <SideBar page="profile"/>
                <div className="profile-content">
                    <Header title="Profile"/>
                    <Box sx={{ display: "flex", justifyContent: "center", padding: 4 }}>
                        <CircularProgress />
                    </Box>
                </div>
            </main>
        );
    }

    if (error || !profile) {
        return (
            <main className="profile-container">
                <SideBar page="profile"/>
                <div className="profile-content">
                    <Header title="Profile"/>
                    <Paper sx={{ padding: 4, textAlign: "center", backgroundColor: "var(--primary-color)" }}>
                        <Typography sx={{ color: "var(--text-color)" }}>
                            No profile found. Please create a profile first.
                        </Typography>
                    </Paper>
                </div>
            </main>
        );
    }

    return (
        <main className="profile-container">
            <SideBar page="profile"/>
            <div className="profile-content">
                <Header title="Profile"/>

                <div className="profile-info">
                    <div className="profile-header">
                        <div className="profile-name">
                            <h1>{profile.name}</h1>
                            <p className="profile-profession">{profile.profession}</p>
                        </div>
                    </div>
                    <div className="profile-details">
                        <div className="profile-section">
                            <h2>Personal Infos</h2>
                            <div className="info-grid">
                                <div className="info-item">
                                    <label>Full Name</label>
                                    <span>{profile.name}</span>
                                </div>
                                <div className="info-item">
                                    <label>Profession</label>
                                    <span>{profile.profession}</span>
                                </div>
                            </div>
                        </div>
                        <div className="profile-section">
                            <h2>Financial Infos</h2>
                            <div className="info-grid">
                                <div className="info-item">
                                    <label>Net Salary</label>
                                    <span className="salary-amount">{formatCurrency(profile.salary)}</span>
                                </div>
                                <div className="info-item">
                                    <label>Payday</label>
                                    <span>{String(profile.payDay).padStart(2, "0")}</span>
                                </div>
                                <div className="info-item">
                                    <label>Initial Balance</label>
                                    <span className="balance-amount">{formatCurrency(profile.initialBalance)}</span>
                                </div>
                                <div className="info-item">
                                    <label>Current Balance</label>
                                    <span className="balance-amount">{formatCurrency(currentBalance)}</span>
                                </div>
                            </div>
                        </div>
                        {goals.length > 0 && (
                            <div className="profile-section">
                                <h2>Goals Overview</h2>
                                <Box sx={{ display: "flex", flexDirection: "column", gap: 2 }}>
                                    {goals.map((goal) => {
                                        const progress = currentBalance > 0 && goal.targetAmount > 0
                                            ? Math.min((currentBalance / goal.targetAmount) * 100, 100)
                                            : 0;
                                        return (
                                            <Box key={goal.id}>
                                                <Box sx={{ display: "flex", justifyContent: "space-between", marginBottom: 0.5 }}>
                                                    <Typography sx={{ color: "var(--text-color)", fontSize: "0.9rem" }}>
                                                        {goal.name}
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
                                                            backgroundColor: progress >= 100 ? "#4caf50" : "#1976d2",
                                                        },
                                                    }}
                                                />
                                            </Box>
                                        );
                                    })}
                                </Box>
                            </div>
                        )}
                        <div className="profile-actions">
                            <button className="btn-primary" onClick={() => setModalOpen(true)}>Edit Perfil</button>
                        </div>
                    </div>
                </div>
            </div>

            <ProfileModal
                open={modalOpen}
                onClose={() => setModalOpen(false)}
                profile={profile}
                onSubmit={handleUpdate}
            />
        </main>
    );
};

export { Profile };
