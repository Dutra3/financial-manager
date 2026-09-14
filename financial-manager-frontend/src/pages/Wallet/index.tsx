import { useState } from "react";
import { Header } from "../../components/Header";
import { SideBar } from "../../components/SideBar";
import { InvestmentModal } from "../../components/InvestmentModal";
import { getStocks, createStockTransaction, StockResponse } from "../../api/stockApi";
import { getReits, createReitTransaction, ReitResponse } from "../../api/reitApi";
import { getBonds, createBondTransaction, BondResponse } from "../../api/bondApi";
import { getEtfs, createEtfTransaction, EtfResponse } from "../../api/etfApi";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import { Box, Typography, Button, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, CircularProgress, Tabs, Tab } from "@mui/material";
import { FiPlusCircle } from "react-icons/fi";
import "./Wallet.css";

type InvestmentType = "stocks" | "reits" | "bonds" | "etfs";

interface SelectedItem {
    id: string;
    name: string;
    price: number;
    type: InvestmentType;
}

const formatPrice = (price: number) => `$${price.toLocaleString("en-US", { minimumFractionDigits: 2, maximumFractionDigits: 4 })}`;
const formatMetric = (value: number | null | undefined) => value != null ? value.toFixed(2) : "—";

const Wallet = () => {
    const queryClient = useQueryClient();
    const [activeTab, setActiveTab] = useState<InvestmentType>("stocks");
    const [modalOpen, setModalOpen] = useState(false);
    const [selectedItem, setSelectedItem] = useState<SelectedItem | null>(null);

    const { data: stocks = [], isLoading: stocksLoading } = useQuery({
        queryKey: ["stocks"],
        queryFn: getStocks,
    });
    const { data: reits = [], isLoading: reitsLoading } = useQuery({
        queryKey: ["reits"],
        queryFn: getReits,
    });
    const { data: bonds = [], isLoading: bondsLoading } = useQuery({
        queryKey: ["bonds"],
        queryFn: getBonds,
    });
    const { data: etfs = [], isLoading: etfsLoading } = useQuery({
        queryKey: ["etfs"],
        queryFn: getEtfs,
    });

    const openModal = (item: SelectedItem) => {
        setSelectedItem(item);
        setModalOpen(true);
    };

    const handleAddInvestment = async (quantity: number, price: number, transactionDate: string) => {
        if (!selectedItem) return;
        const payload = { id: "", quantity, price, transactionDate };
        switch (selectedItem.type) {
            case "stocks":
                await createStockTransaction({ ...payload, stockId: selectedItem.id });
                queryClient.invalidateQueries({ queryKey: ["stocks"] });
                break;
            case "reits":
                await createReitTransaction({ ...payload, reitId: selectedItem.id });
                queryClient.invalidateQueries({ queryKey: ["reits"] });
                break;
            case "bonds":
                await createBondTransaction({ ...payload, bondId: selectedItem.id });
                queryClient.invalidateQueries({ queryKey: ["bonds"] });
                break;
            case "etfs":
                await createEtfTransaction({ ...payload, etfId: selectedItem.id });
                queryClient.invalidateQueries({ queryKey: ["etfs"] });
                break;
        }
    };

    const renderTable = () => {
        const isLoading = stocksLoading || reitsLoading || bondsLoading || etfsLoading;

        if (isLoading) {
            return <Box sx={{ display: "flex", justifyContent: "center", padding: 4 }}><CircularProgress /></Box>;
        }

        switch (activeTab) {
            case "stocks":
                return (
                    <TableContainer component={Paper} sx={{ backgroundColor: "var(--primary-color)" }}>
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Name</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Ticker</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Price</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>P/E</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Div Yield</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>P/B</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Last Dividend</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }} align="right">Actions</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {(stocks as StockResponse[]).map((s) => (
                                    <TableRow key={s.id}>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{s.name}</TableCell>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{s.ticker}</TableCell>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{formatPrice(s.price)}</TableCell>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{formatMetric(s.peRatio)}</TableCell>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{formatMetric(s.dividendYield)}{s.dividendYield != null ? "%" : ""}</TableCell>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{formatMetric(s.pbRatio)}</TableCell>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{s.lastDividend != null ? formatPrice(s.lastDividend) : "—"}</TableCell>
                                        <TableCell align="right">
                                            <Button size="small" variant="outlined" startIcon={<FiPlusCircle />}
                                                onClick={() => openModal({ id: s.id, name: `${s.name} (${s.ticker})`, price: s.price, type: "stocks" })}
                                                sx={{ color: "var(--text-color)", borderColor: "var(--text-color)" }}>
                                                Add
                                            </Button>
                                        </TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                );
            case "reits":
                return (
                    <TableContainer component={Paper} sx={{ backgroundColor: "var(--primary-color)" }}>
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Name</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Ticker</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Type</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Segment</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Price</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }} align="right">Actions</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {(reits as ReitResponse[]).map((r) => (
                                    <TableRow key={r.id}>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{r.name}</TableCell>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{r.ticker}</TableCell>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{r.type}</TableCell>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{r.industrySegment}</TableCell>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{formatPrice(r.price)}</TableCell>
                                        <TableCell align="right">
                                            <Button size="small" variant="outlined" startIcon={<FiPlusCircle />}
                                                onClick={() => openModal({ id: r.id, name: `${r.name} (${r.ticker})`, price: r.price, type: "reits" })}
                                                sx={{ color: "var(--text-color)", borderColor: "var(--text-color)" }}>
                                                Add
                                            </Button>
                                        </TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                );
            case "bonds":
                return (
                    <TableContainer component={Paper} sx={{ backgroundColor: "var(--primary-color)" }}>
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Name</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Type</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Segment</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Price</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }} align="right">Actions</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {(bonds as BondResponse[]).map((b) => (
                                    <TableRow key={b.id}>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{b.name}</TableCell>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{b.type}</TableCell>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{b.industrySegment}</TableCell>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{formatPrice(b.price)}</TableCell>
                                        <TableCell align="right">
                                            <Button size="small" variant="outlined" startIcon={<FiPlusCircle />}
                                                onClick={() => openModal({ id: b.id, name: b.name, price: b.price, type: "bonds" })}
                                                sx={{ color: "var(--text-color)", borderColor: "var(--text-color)" }}>
                                                Add
                                            </Button>
                                        </TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                );
            case "etfs":
                return (
                    <TableContainer component={Paper} sx={{ backgroundColor: "var(--primary-color)" }}>
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Name</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Ticker</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Type</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Segment</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }}>Price</TableCell>
                                    <TableCell sx={{ color: "var(--text-color)", fontWeight: "bold" }} align="right">Actions</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {(etfs as EtfResponse[]).map((e) => (
                                    <TableRow key={e.id}>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{e.name}</TableCell>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{e.ticker}</TableCell>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{e.type}</TableCell>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{e.industrySegment}</TableCell>
                                        <TableCell sx={{ color: "var(--text-color)" }}>{formatPrice(e.price)}</TableCell>
                                        <TableCell align="right">
                                            <Button size="small" variant="outlined" startIcon={<FiPlusCircle />}
                                                onClick={() => openModal({ id: e.id, name: `${e.name} (${e.ticker})`, price: e.price, type: "etfs" })}
                                                sx={{ color: "var(--text-color)", borderColor: "var(--text-color)" }}>
                                                Add
                                            </Button>
                                        </TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                );
        }
    };

    const currentData = {
        stocks: stocks as StockResponse[],
        reits: reits as ReitResponse[],
        bonds: bonds as BondResponse[],
        etfs: etfs as EtfResponse[],
    };

    return (
        <main className="home-container">
            <SideBar page="wallet"/>
            <div className="wallet-content">
                <Header title="Olá, Gabriel. Você está na aba de Carteira. Aqui você encontra suas ações e fundos imobiliários cadastrados, podendo também alterar a quantidade de cada um deles."/>

                <Tabs value={activeTab} onChange={(_, v) => setActiveTab(v)} sx={{ marginBottom: 2, "& .MuiTab-root": { color: "var(--text-color)" }, "& .MuiTabs-indicator": { backgroundColor: "var(--text-color)" } }}>
                    <Tab label="Stocks" value="stocks" />
                    <Tab label="REITs" value="reits" />
                    <Tab label="Bonds" value="bonds" />
                    <Tab label="ETFs" value="etfs" />
                </Tabs>

                {currentData[activeTab].length === 0 && !stocksLoading && !reitsLoading && !bondsLoading && !etfsLoading ? (
                    <Paper sx={{ padding: 4, textAlign: "center", backgroundColor: "var(--primary-color)" }}>
                        <Typography sx={{ color: "var(--text-color)" }}>No {activeTab} found.</Typography>
                    </Paper>
                ) : (
                    renderTable()
                )}
            </div>

            <InvestmentModal
                open={modalOpen}
                onClose={() => setModalOpen(false)}
                title={`Add ${activeTab === "stocks" ? "Stock" : activeTab === "reits" ? "REIT" : activeTab === "bonds" ? "Bond" : "ETF"} Transaction`}
                itemName={selectedItem?.name || ""}
                itemPrice={selectedItem?.price || 0}
                onSubmit={handleAddInvestment}
            />
        </main>
    );
};

export { Wallet };
