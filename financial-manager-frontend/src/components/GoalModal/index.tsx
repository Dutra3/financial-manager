import { useState, useEffect } from "react";
import { Modal, Box, Typography, TextField, Button, Alert, Switch, FormControlLabel } from "@mui/material";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { format } from "date-fns";
import { formatAmountInput, parseAmountInput } from "../../utils/formatAmount";

interface GoalModalProps {
    open: boolean;
    onClose: () => void;
    onSubmit: (name: string, description: string, targetDate: string | null, targetAmount: number, isAchieved: boolean) => Promise<void>;
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

const GoalModal = ({ open, onClose, onSubmit }: GoalModalProps) => {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [targetDate, setTargetDate] = useState<Date | null>(null);
    const [targetAmount, setTargetAmount] = useState("");
    const [isAchieved, setIsAchieved] = useState(false);
    const [message, setMessage] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);

    useEffect(() => {
        if (open) {
            setName("");
            setDescription("");
            setTargetDate(null);
            setTargetAmount("");
            setIsAchieved(false);
            setMessage("");
        }
    }, [open]);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsSubmitting(true);
        setMessage("");
        try {
            await onSubmit(
                name,
                description,
                targetDate ? format(targetDate, "yyyy-MM-dd") : null,
                parseAmountInput(targetAmount),
                isAchieved
            );
            setMessage("Goal created successfully!");
            setTimeout(() => onClose(), 1000);
        } catch (err: any) {
            setMessage(`Error: ${err?.response?.data?.message || "Failed to create goal"}`);
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <Modal open={open} onClose={onClose}>
            <Box sx={modalStyle}>
                <Typography variant="h6" sx={{ marginBottom: 2, color: "var(--text-color)" }}>
                    Create Goal
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
                            label="Description"
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            size="small"
                            multiline
                            rows={2}
                            sx={{
                                "& .MuiInputBase-input": { color: "var(--text-color)" },
                                "& .MuiOutlinedInput-notchedOutline": { borderColor: "var(--text-color)" },
                                label: { color: "var(--text-color)" },
                            }}
                        />
                        <DatePicker
                            label="Target Date"
                            value={targetDate}
                            onChange={(date) => setTargetDate(date)}
                            minDate={new Date(1900, 0, 1)}
                            maxDate={new Date(2100, 11, 31)}
                            shouldDisableDate={(date) => {
                                const min = new Date(1900, 0, 1);
                                const max = new Date(2100, 11, 31);
                                return date < min || date > max;
                            }}
                            slotProps={{
                                textField: {
                                    size: "small",
                                    onBlur: (e) => {
                                        const raw = e.target.value;
                                        if (!raw) return;
                                        const d = new Date(raw);
                                        if (isNaN(d.getTime())) return;
                                        const min = new Date(1900, 0, 1);
                                        const max = new Date(2100, 11, 31);
                                        if (d < min) setTargetDate(min);
                                        else if (d > max) setTargetDate(max);
                                    },
                                    sx: {
                                        "& .MuiInputBase-root": { color: "var(--text-color)" },
                                        "& .MuiOutlinedInput-notchedOutline": { borderColor: "var(--text-color)" },
                                        "& .MuiSvgIcon-root": { color: "var(--text-color)" },
                                        label: { color: "var(--text-color)" },
                                    },
                                },
                            }}
                        />
                        <TextField
                            label="Target Amount"
                            value={targetAmount}
                            onChange={(e) => setTargetAmount(formatAmountInput(e.target.value, targetAmount))}
                            required
                            size="small"
                            inputMode="decimal"
                            sx={{ input: { color: "var(--text-color)" }, label: { color: "var(--text-color)" } }}
                        />
                        <FormControlLabel
                            control={<Switch checked={isAchieved} onChange={(e) => setIsAchieved(e.target.checked)} />}
                            label="Achieved"
                            sx={{ color: "var(--text-color)" }}
                        />
                        <Box sx={{ display: "flex", gap: 2, marginTop: 1 }}>
                            <Button type="submit" variant="contained" disabled={isSubmitting} sx={{ flex: 1 }}>
                                {isSubmitting ? "Saving..." : "Create Goal"}
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

export { GoalModal };
