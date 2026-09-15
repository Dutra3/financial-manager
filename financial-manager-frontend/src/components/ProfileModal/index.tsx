import { useState, useEffect } from "react";
import { Modal, Box, Typography, TextField, Button, Alert } from "@mui/material";
import { Profile } from "../../api/profileApi";
import { formatAmountInput, parseAmountInput } from "../../utils/formatAmount";

interface ProfileModalProps {
    open: boolean;
    onClose: () => void;
    profile: Profile | null;
    onSubmit: (profile: Profile) => Promise<void>;
}

const modalStyle = {
    position: "absolute" as const,
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    width: { xs: "90%", sm: 450 },
    bgcolor: "var(--primary-color)",
    border: "1px solid var(--text-color)",
    boxShadow: 24,
    p: 4,
};

const ProfileModal = ({ open, onClose, profile, onSubmit }: ProfileModalProps) => {
    const [name, setName] = useState("");
    const [profession, setProfession] = useState("");
    const [salary, setSalary] = useState("");
    const [payDay, setPayDay] = useState("");
    const [initialBalance, setInitialBalance] = useState("");
    const [message, setMessage] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);

    useEffect(() => {
        if (open && profile) {
            setName(profile.name || "");
            setProfession(profile.profession || "");
            setSalary(profile.salary ? profile.salary.toString() : "");
            setPayDay(profile.payDay ? String(profile.payDay) : "");
            setInitialBalance(profile.initialBalance ? profile.initialBalance.toString() : "");
            setMessage("");
        }
    }, [open, profile]);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!profile) return;
        setIsSubmitting(true);
        setMessage("");
        try {
            await onSubmit({
                ...profile,
                name,
                profession,
                salary: parseAmountInput(salary),
                payDay: parseInt(payDay) || 1,
                initialBalance: parseAmountInput(initialBalance),
            });
            setMessage("Profile updated successfully!");
            setTimeout(() => onClose(), 1000);
        } catch (err: any) {
            setMessage(`Error: ${err?.response?.data?.message || "Failed to update profile"}`);
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <Modal open={open} onClose={onClose}>
            <Box sx={modalStyle}>
                <Typography variant="h6" sx={{ marginBottom: 2, color: "var(--text-color)" }}>
                    Edit Profile
                </Typography>
                <form onSubmit={handleSubmit}>
                    <Box sx={{ display: "flex", flexDirection: "column", gap: 2 }}>
                        <TextField
                            label="Name"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            required
                            size="small"
                            sx={{ input: { color: "var(--text-color)" }, label: { color: "var(--text-color)" } }}
                        />
                        <TextField
                            label="Profession"
                            value={profession}
                            onChange={(e) => setProfession(e.target.value)}
                            size="small"
                            sx={{ input: { color: "var(--text-color)" }, label: { color: "var(--text-color)" } }}
                        />
                        <TextField
                            label="Net Salary"
                            value={salary}
                            onChange={(e) => setSalary(formatAmountInput(e.target.value, salary))}
                            required
                            size="small"
                            inputMode="decimal"
                            sx={{ input: { color: "var(--text-color)" }, label: { color: "var(--text-color)" } }}
                        />
                        <TextField
                            label="Payday (1-31)"
                            value={payDay}
                            onChange={(e) => setPayDay(e.target.value.replace(/[^0-9]/g, "").slice(0, 2))}
                            required
                            size="small"
                            inputMode="numeric"
                            sx={{ input: { color: "var(--text-color)" }, label: { color: "var(--text-color)" } }}
                        />
                        <TextField
                            label="Initial Balance"
                            value={initialBalance}
                            onChange={(e) => setInitialBalance(formatAmountInput(e.target.value, initialBalance))}
                            required
                            size="small"
                            inputMode="decimal"
                            sx={{ input: { color: "var(--text-color)" }, label: { color: "var(--text-color)" } }}
                        />
                        <Box sx={{ display: "flex", gap: 2, marginTop: 1 }}>
                            <Button type="submit" variant="contained" disabled={isSubmitting} sx={{ flex: 1 }}>
                                {isSubmitting ? "Saving..." : "Save"}
                            </Button>
                            <Button variant="outlined" onClick={onClose} sx={{ flex: 1, color: "var(--text-color)", borderColor: "var(--text-color)" }}>
                                Cancel
                            </Button>
                        </Box>
                    </Box>
                </form>
                {message && (
                    <Alert severity={message.startsWith("Error") ? "error" : "success"} sx={{ marginTop: 2 }}>
                        {message}
                    </Alert>
                )}
            </Box>
        </Modal>
    );
};

export { ProfileModal };
