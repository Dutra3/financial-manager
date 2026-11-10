import { useState, useEffect } from "react";
import { Modal, Box, Typography, Button, Alert } from "@mui/material";
import { deleteTransaction, deleteRecurringGroup, TransactionResponse } from "../../api/transactionApi";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { getUserId } from "../../utils/auth";

interface DeleteConfirmationModalProps {
    open: boolean;
    onClose: () => void;
    transaction: TransactionResponse | null;
}

const modalStyle = {
    position: "absolute" as const,
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    width: { xs: "90%", sm: 400 },
    bgcolor: "var(--primary-color)",
    border: "1px solid var(--text-color)",
    boxShadow: 24,
    p: 4,
};

const DeleteConfirmationModal = ({ open, onClose, transaction }: DeleteConfirmationModalProps) => {
    const queryClient = useQueryClient();
    const [message, setMessage] = useState("");

    useEffect(() => {
        if (open) setMessage("");
    }, [open]);

    const groupMutation = useMutation({
        mutationFn: () => deleteRecurringGroup(transaction!.recurringGroupId!),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["transactions", getUserId()] });
            setMessage("Recurring series deleted successfully!");
            setTimeout(() => onClose(), 1000);
        },
        onError: (err: any) => {
            setMessage(`Error: ${err?.response?.data?.message || "Failed to delete series"}`);
        },
    });

    const mutation = useMutation({
        mutationFn: () => deleteTransaction(transaction!.id),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["transactions", getUserId()] });
            setMessage("Transaction deleted successfully!");
            setTimeout(() => onClose(), 1000);
        },
        onError: (err: any) => {
            setMessage(`Error: ${err?.response?.data?.message || "Failed to delete transaction"}`);
        },
    });

    return (
        <Modal open={open} onClose={onClose}>
            <Box sx={modalStyle}>
                <Typography variant="h6" sx={{ marginBottom: 2, color: "var(--text-color)" }}>
                    Delete Transaction
                </Typography>
                <Typography sx={{ marginBottom: 3, color: "var(--text-color)" }}>
                    Are you sure you want to delete <strong>{transaction?.name}</strong>?
                    This action cannot be undone.
                </Typography>
                <Box sx={{ display: "flex", gap: 2 }}>
                    <Button
                        variant="contained"
                        color="error"
                        disabled={mutation.isPending}
                        onClick={() => mutation.mutate()}
                        sx={{ flex: 1 }}
                    >
                        {mutation.isPending ? "Deleting..." : "Delete"}
                    </Button>
                    <Button
                        variant="outlined"
                        onClick={onClose}
                        sx={{ flex: 1, color: "var(--text-color)", borderColor: "var(--text-color)" }}
                    >
                        Cancel
                    </Button>
                </Box>
                {transaction?.recurringGroupId && (
                    <Button
                        variant="outlined"
                        color="warning"
                        disabled={groupMutation.isPending}
                        onClick={() => groupMutation.mutate()}
                        fullWidth
                        sx={{ marginTop: 1.5 }}
                    >
                        {groupMutation.isPending ? "Deleting..." : "Delete entire recurring series"}
                    </Button>
                )}
                {message && (
                    <Alert severity={message.startsWith("Error") ? "error" : "success"} sx={{ marginTop: 2 }}>
                        {message}
                    </Alert>
                )}
            </Box>
        </Modal>
    );
};

export { DeleteConfirmationModal };
