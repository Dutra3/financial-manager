import { useState } from "react";
import { Header } from "../../components/Header";
import { SideBar } from "../../components/SideBar";
import { InvestmentModal } from "../../components/InvestmentModal";
import { getAssets, createAssetTransaction, getPortfolio, getPortfolioHistory, Asset, AssetType, Position, HistoryPoint } from "../../api/assetApi";
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from "recharts";
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

    const { data: history = [] } = useQuery<HistoryPoint[]>({
        queryKey: ["portfolio-history", getUserId()],
        queryFn: () => getPortfolioHistory(getUserId()),
    });

    const { data: positions = [] } = useQuery<Position[]>({
        queryKey: ["portfolio", getUserId()],
        queryFn: () => getPortfolio(getUserId()),
    });

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

    const handleAddInvestment = async (quantity: number, price: number, transactionDate: string, type: string) => {
        if (!selectedAsset) return;
        await createAssetTransaction({
            assetId: selectedAsset.id,
            userId: getUserId(),
            type: type as "BUY" | "SELL" | "DIVIDEND",
            quantity,
            price,
            transactionDate,
        });
        queryClient.invalidateQueries({ queryKey: ["asset-transactions"] });
        queryClient.invalidateQueries({ queryKey: ["portfolio", getUserId()] });
        queryClient.invalidateQueries({ queryKey: ["portfolio-history", getUserId()] });
    };

    const assetLabel = TABS.find((t) => t.value === activeTab)?.label ?? activeTab;

    return (
        <main className="wallet-container">
            <SideBar page="wallet"/>
            <div className="wallet-content">
                <Header title={`Olá, ${name}. Você está na aba de Carteira. Aqui você encontra os ativos disponíveis e pode registrar suas compras.`}/>

                {positions.length > 0 && (
                    <>
                        <Typography variant="h6" sx={{ color: "var(--text-color)", marginBottom: 1 }}>My Positions</Typography>
                        <TableContainer component={Paper} sx={{ backgroundColor: "var(--primary-color)", marginBottom: 3 }}>
                            <Table size="small">
                                <TableHead>
                                    <TableRow>
                                        {["Ticker", "Qty", "Avg Price", "Invested", "Current", "Dividends", "P&L"].map((h) => (
                                            <TableCell key={h} sx={{ color: "var(--text-color)", fontWeight: "bold" }}>{h}</TableCell>
                                        ))}
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {positions.map((pos) => (
                                        <TableRow key={pos.assetId}>
                                            <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>{pos.ticker}</TableCell>
                                            <TableCell sx={{ color: "var(--text-color)" }}>{pos.quantity}</TableCell>
                                            <TableCell sx={{ color: "var(--text-color)" }}>{formatPrice(pos.averagePrice)}</TableCell>
                                            <TableCell sx={{ color: "var(--text-color)" }}>{formatPrice(pos.totalInvested)}</TableCell>
                                            <TableCell sx={{ color: "var(--text-color)" }}>{formatPrice(pos.currentValue)}</TableCell>
                                            <TableCell sx={{ color: "var(--text-color)" }}>{formatPrice(pos.dividends)}</TableCell>
                                            <TableCell sx={{ color: pos.profitLoss >= 0 ? "#4caf50" : "#f44336", fontWeight: "bold" }}>
                                                {pos.profitLoss >= 0 ? "+" : ""}{formatPrice(pos.profitLoss)} ({pos.profitLossPercent.toFixed(1)}%)
                                            </TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                    </>
                )}
                {history.length > 1 && (
                    <>
                        <Typography variant="h6" sx={{ color: "var(--text-color)", marginBottom: 1 }}>Invested Over Time</Typography>
                        <Paper sx={{ backgroundColor: "var(--primary-color)", padding: 2, marginBottom: 3 }}>
                            <ResponsiveContainer width="100%" height={280}>
                                <LineChart data={history} margin={{ top: 5, right: 20, left: 30, bottom: 5 }}>
                                    <CartesianGrid strokeDasharray="3 3" stroke="rgba(128,128,128,0.3)" />
                                    <XAxis dataKey="month" stroke="var(--text-color)" />
                                    <YAxis stroke="var(--text-color)" />
                                    <Tooltip
                                        formatter={(value: any) => formatPrice(Number(value))}
                                        contentStyle={{ backgroundColor: "var(--primary-color)", border: "1px solid var(--text-color)", color: "var(--text-color)" }}
                                    />
                                    <Line type="monotone" dataKey="invested" stroke="#4caf50" name="Invested" strokeWidth={2} />
                                </LineChart>
                            </ResponsiveContainer>
                        </Paper>
                    </>
                )}
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
