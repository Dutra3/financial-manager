import { useState, useEffect } from "react";
import { Modal, Box, Typography, TextField, Button, Select, MenuItem, FormControl, InputLabel, Alert } from "@mui/material";
import { createCategory, Category, CategoryType } from "../../api/categoryApi";
import { useMutation, useQueryClient } from "@tanstack/react-query";

interface CategoryModalProps {
    open: boolean;
    onClose: () => void;
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

const CategoryModal = ({ open, onClose }: CategoryModalProps) => {
    const queryClient = useQueryClient();
    const [name, setName] = useState("");
    const [type, setType] = useState<CategoryType>("debit");
    const [message, setMessage] = useState("");

    useEffect(() => {
        if (open) {
            setName("");
            setType("debit");
            setMessage("");
        }
    }, [open]);

    const mutation = useMutation({
        mutationFn: (newCategory: Category) => createCategory(newCategory),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["categories"] });
            setMessage("Category created successfully!");
            setTimeout(() => onClose(), 1000);
        },
        onError: (err: any) => {
            setMessage(`Error: ${err?.response?.data?.message || "Failed to create category"}`);
        },
    });

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        mutation.mutate({ id: "", name, type });
    };

    return (
        <Modal open={open} onClose={onClose}>
            <Box sx={modalStyle}>
                <Typography variant="h6" sx={{ marginBottom: 2, color: "var(--text-color)" }}>
                    Create Category
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
                        <FormControl size="small" fullWidth>
                            <InputLabel sx={{ color: "var(--text-color)" }}>Type</InputLabel>
                            <Select
                                value={type}
                                onChange={(e) => setType(e.target.value as CategoryType)}
                                label="Type"
                                sx={{
                                    color: "var(--text-color)",
                                    "& .MuiOutlinedInput-notchedOutline": { borderColor: "var(--text-color)" },
                                    "& .MuiSvgIcon-root": { color: "var(--text-color)" },
                                }}
                            >
                                <MenuItem value="debit">Debit</MenuItem>
                                <MenuItem value="credit">Credit</MenuItem>
                            </Select>
                        </FormControl>
                        <Box sx={{ display: "flex", gap: 2, marginTop: 1 }}>
                            <Button type="submit" variant="contained" disabled={mutation.isPending} sx={{ flex: 1 }}>
                                {mutation.isPending ? "Creating..." : "Create"}
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

export { CategoryModal };
