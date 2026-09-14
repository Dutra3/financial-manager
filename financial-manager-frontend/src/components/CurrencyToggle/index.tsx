import { FormControl, Select, MenuItem, Box } from "@mui/material";
import { CURRENCIES, Currency } from "../../utils/currency";

interface CurrencyToggleProps {
    currency: Currency;
    onChange: (currency: Currency) => void;
}

const CurrencyToggle: React.FC<CurrencyToggleProps> = ({ currency, onChange }) => {
    const selected = CURRENCIES.find((c) => c.code === currency);

    return (
        <FormControl size="small" sx={{ minWidth: 200, marginBottom: 2 }}>
            <Select
                value={currency}
                onChange={(e) => onChange(e.target.value as Currency)}
                sx={{
                    color: "var(--text-color)",
                    "& .MuiSelect-select": { color: "var(--text-color)" },
                    "& .MuiOutlinedInput-notchedOutline": {
                        borderColor: "var(--text-color)",
                    },
                    "&:hover .MuiOutlinedInput-notchedOutline": {
                        borderColor: "var(--text-color)",
                    },
                    "&.Mui-focused .MuiOutlinedInput-notchedOutline": {
                        borderColor: "var(--text-color)",
                    },
                    "& .MuiSvgIcon-root": {
                        color: "var(--text-color)",
                    },
                }}
                renderValue={() => (
                    <Box sx={{ display: "flex", alignItems: "center", gap: 1 }}>
                        <span style={{ fontSize: "1.2rem" }}>{selected?.flag}</span>
                        <span>{selected?.code} — {selected?.label}</span>
                    </Box>
                )}
            >
                {CURRENCIES.map(({ code, label, flag }) => (
                    <MenuItem key={code} value={code}>
                        <Box sx={{ display: "flex", alignItems: "center", gap: 1 }}>
                            <span style={{ fontSize: "1.2rem" }}>{flag}</span>
                            <span>{code} — {label}</span>
                        </Box>
                    </MenuItem>
                ))}
            </Select>
        </FormControl>
    );
};

export { CurrencyToggle };
