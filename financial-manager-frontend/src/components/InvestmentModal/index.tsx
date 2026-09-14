import { useState, useEffect } from "react";
import { Modal, Box, Typography, TextField, Button, Alert } from "@mui/material";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { format } from "date-fns";

interface InvestmentModalProps {
    open: boolean;
    onClose: () => void;
    title: string;
    itemName: string;
    itemPrice: number;
    onSubmit: (quantity: number, price: number, transactionDate: string) => Promise<void>;
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

const InvestmentModal = ({ open, onClose, title, itemName, itemPrice, onSubmit }: InvestmentModalProps) => {
    const [quantity, setQuantity] = useState("");
    const [price, setPrice] = useState("");
    const [transactionDate, setTransactionDate] = useState<Date | null>(null);
    const [message, setMessage] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);

    useEffect(() => {
        if (open) {
            setQuantity("");
            setPrice(itemPrice ? itemPrice.toString() : "");
            setTransactionDate(new Date());
            setMessage("");
        }
    }, [open, itemPrice]);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!transactionDate) return;
        setIsSubmitting(true);
        setMessage("");
        try {
            await onSubmit(
                parseFloat(quantity),
                parseFloat(price),
                format(transactionDate, "yyyy-MM-dd")
            );
            setMessage("Transaction created successfully!");
            setTimeout(() => onClose(), 1000);
        } catch (err: any) {
            setMessage(`Error: ${err?.response?.data?.message || "Failed to create transaction"}`);
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <Modal open={open} onClose={onClose}>
            <Box sx={modalStyle}>
                <Typography variant="h6" sx={{ marginBottom: 2, color: "var(--text-color)" }}>
                    {title}
                </Typography>
                <Typography sx={{ marginBottom: 2, color: "var(--text-color)", fontWeight: "bold" }}>
                    {itemName}
                </Typography>
                <form onSubmit={handleSubmit}>
                    <Box sx={{ display: "flex", flexDirection: "column", gap: 2 }}>
                        <TextField
                            label="Quantity"
                            value={quantity}
                            onChange={(e) => setQuantity(e.target.value.replace(/[^0-9.]/g, ""))}
                            required
                            size="small"
                            inputMode="decimal"
                            sx={{ input: { color: "var(--text-color)" }, label: { color: "var(--text-color)" } }}
                        />
                        <TextField
                            label="Price"
                            value={price}
                            onChange={(e) => setPrice(e.target.value.replace(/[^0-9.]/g, ""))}
                            required
                            size="small"
                            inputMode="decimal"
                            sx={{ input: { color: "var(--text-color)" }, label: { color: "var(--text-color)" } }}
                        />
                        <DatePicker
                            label="Transaction Date"
                            value={transactionDate}
                            onChange={(date) => setTransactionDate(date)}
                            minDate={new Date(1900, 0, 1)}
                            maxDate={new Date(2100, 11, 31)}
                            slotProps={{
                                textField: {
                                    size: "small",
                                    required: true,
                                    sx: {
                                        "& .MuiInputBase-root": { color: "var(--text-color)" },
                                        "& .MuiOutlinedInput-notchedOutline": { borderColor: "var(--text-color)" },
                                        "& .MuiSvgIcon-root": { color: "var(--text-color)" },
                                        label: { color: "var(--text-color)" },
                                    },
                                },
                            }}
                        />
                        <Box sx={{ display: "flex", gap: 2, marginTop: 1 }}>
                            <Button type="submit" variant="contained" disabled={isSubmitting || !transactionDate} sx={{ flex: 1 }}>
                                {isSubmitting ? "Saving..." : "Add to Wallet"}
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

export { InvestmentModal };
