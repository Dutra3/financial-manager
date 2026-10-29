import { useState } from "react";
import { Header } from "../../components/Header";
import { SideBar } from "../../components/SideBar";
import { InvestmentModal } from "../../components/InvestmentModal";
import { getAssets, createAssetTransaction, Asset, AssetType } from "../../api/assetApi";
import { getUserId } from "../../utils/auth";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import { Box, Typography, Button, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, CircularProgress, Tabs, Tab } from "@mui/material";
import { FiPlusCircle } from "react-icons/fi";
import "./Wallet.css";
import { useUserName } from "../../hooks/useUserName";

const TABS: { value: AssetType; label: string }[] = [
    { value: "STOCK", label: "Stocks" },
    { value: "REIT", label: "REITs" },
    { value: "ETF", label: "ETFs" },
    { value: "BOND", label: "Bonds" },
];

const formatPrice = (price: number) => `$${price.toLocaleString("en-US", { minimumFractionDigits: 2, maximumFractionDigits: 4 })}`;
const formatMetric = (value: number | null | undefined) => value != null ? value.toFixed(2) : "—";

const Wallet = () => {
    const name = useUserName();
    const queryClient = useQueryClient();
    const [activeTab, setActiveTab] = useState<AssetType>("STOCK");
    const [modalOpen, setModalOpen] = useState(false);
    const [selectedAsset, setSelectedAsset] = useState<Asset | null>(null);

    const { data: assets = [], isLoading } = useQuery({
        queryKey: ["assets"],
        queryFn: () => getAssets(),
    });

    const filteredAssets = assets.filter((a) => a.assetType === activeTab);
    const isStockTab = activeTab === "STOCK";

    const openModal = (asset: Asset) => {
        setSelectedAsset(asset);
        setModalOpen(true);
    };

    const handleAddInvestment = async (quantity: number, price: number, transactionDate: string) => {
        if (!selectedAsset) return;
        await createAssetTransaction({
            assetId: selectedAsset.id,
            userId: getUserId(),
            type: "BUY",
            quantity,
            price,
            transactionDate,
        });
        queryClient.invalidateQueries({ queryKey: ["asset-transactions"] });
    };

    const assetLabel = TABS.find((t) => t.value === activeTab)?.label ?? activeTab;

    return (
        <main className="wallet-container">
            <SideBar page="wallet"/>
            <div className="wallet-content">
                <Header title={`Olá, ${name}. Você está na aba de Carteira. Aqui você encontra os ativos disponíveis e pode registrar suas compras.`}/>

                <Tabs value={activeTab} onChange={(_, v) => setActiveTab(v)} sx={{ marginBottom: 2, "& .MuiTab-root": { color: "var(--text-color)" }, "& .MuiTabs-indicator": { backgroundColor: "var(--text-color)" } }}>
                    {TABS.map((tab) => (
                        <Tab key={tab.value} label={tab.label} value={tab.value} />
                    ))}
                </Tabs>

                {isLoading ? (
                    <Box sx={{ display: "flex", justifyContent: "center", padding: 4 }}><CircularProgress /></Box>
                ) : filteredAssets.length === 0 ? (
                    <Paper sx={{ padding: 4, textAlign: "center", backgroundColor: "var(--primary-color)" }}>
                        <Typography sx={{ color: "var(--text-color)" }}>No {assetLabel.toLowerCase()} found.</Typography>
                    </Paper>
                ) : (
                    <TableContainer component={Paper} sx={{ backgroundColor: "var(--primary-color)" }}>
                        <Table>
                            <TableHead>
                                <TableRow sx={{ "& th": { backgroundColor: "var(--hover-color)" } }}>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Name</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Ticker</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Type</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Segment</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Price</TableCell>
                                    {isStockTab && (
                                        <>
                                            <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>P/E</TableCell>
                                            <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Div Yield</TableCell>
                                            <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>P/B</TableCell>
                                            <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Last Dividend</TableCell>
                                        </>
                                    )}
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }} align="right">Actions</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {filteredAssets.map((asset) => (
                                    <TableRow key={asset.id} sx={{ "&:hover": { backgroundColor: "var(--hover-color)" } }}>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{asset.name}</TableCell>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{asset.ticker || "—"}</TableCell>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{asset.type || "—"}</TableCell>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{asset.industrySegment || "—"}</TableCell>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{formatPrice(asset.price)}</TableCell>
                                        {isStockTab && (
                                            <>
                                                <TableCell sx={{ color: "var(--text-color)" }}>{formatMetric(asset.peRatio)}</TableCell>
                                                <TableCell sx={{ color: "var(--text-color)" }}>{formatMetric(asset.dividendYield)}{asset.dividendYield != null ? "%" : ""}</TableCell>
                                                <TableCell sx={{ color: "var(--text-color)" }}>{formatMetric(asset.pbRatio)}</TableCell>
                                                <TableCell sx={{ color: "var(--text-color)" }}>{asset.lastDividend != null ? formatPrice(asset.lastDividend) : "—"}</TableCell>
                                            </>
                                        )}
                                        <TableCell align="right">
                                            <Button size="small" variant="outlined" startIcon={<FiPlusCircle />}
                                                onClick={() => openModal(asset)}
                                                sx={{ color: "var(--text-color)", borderColor: "var(--text-color)" }}>
                                                Add
                                            </Button>
                                        </TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                )}
            </div>

            <InvestmentModal
                open={modalOpen}
                onClose={() => setModalOpen(false)}
                title={`Add ${assetLabel.replace(/s$/, "")} Transaction`}
                itemName={selectedAsset ? `${selectedAsset.name}${selectedAsset.ticker ? ` (${selectedAsset.ticker})` : ""}` : ""}
                itemPrice={selectedAsset?.price || 0}
                onSubmit={handleAddInvestment}
            />
        </main>
    );
};

export { Wallet };
