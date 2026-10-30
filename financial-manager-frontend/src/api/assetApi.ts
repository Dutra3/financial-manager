import apiClient from "./apiClient";

export type AssetType = "STOCK" | "REIT" | "ETF" | "BOND" | "CRYPTO";
export type AssetTransactionType = "BUY" | "SELL" | "DIVIDEND";

export interface Asset {
    id: string;
    ticker: string;
    name: string;
    assetType: AssetType;
    description: string;
    type: string;
    industrySegment: string;
    price: number;
    tagAlong: number | null;
    peRatio: number | null;
    dividendYield: number | null;
    pbRatio: number | null;
    lastDividend: number | null;
    isBesst: boolean | null;
    isNewMarket: boolean | null;
};

export interface AssetTransaction {
    id: string;
    assetId: string;
    userId: string;
    type: AssetTransactionType;
    quantity: number;
    price: number;
    transactionDate: string;
};

export const getAssets = async (type?: AssetType): Promise<Asset[]> => {
    const response = await apiClient.get('/assets', { params: type ? { type } : {} });
    return response.data;
};

export const getAsset = async (id: string) => {
    const response = await apiClient.get(`/assets/${id}`);
    return response.data;
};

export const createAssetTransaction = async (transaction: Omit<AssetTransaction, "id">) => {
    const response = await apiClient.post('/asset-transactions', transaction);
    return response.data;
};

export interface Position {
    assetId: string,
    ticker: string,
    name: string,
    assetType: string,
    quantity: number,
    averagePrice: number,
    totalInvested: number,
    dividends: number,
    currentValue: number,
    profitLoss: number,
    profitLossPercent: number
};

export const getPortfolio = async (userId: string): Promise<Position[]> => {
    const response = await apiClient.get(`/portfolio/${userId}`);
    return response.data;
};

export interface HistoryPoint {
    month: string,
    invested: number
};

export interface Allocation {
    assetType: string,
    currentValue: number,
    percent: number
};

export const getAllocation = async (userId: string): Promise<Allocation[]> => {
    const response = await apiClient.get(`/portfolio/${userId}/allocation`);
    return response.data;
};

export const getPortfolioHistory = async (userId: string): Promise<HistoryPoint[]> => {
    const response = await apiClient.get(`/portfolio/${userId}/history`);
    return response.data;
};

export const refreshPrices = async (): Promise<{ updated: number }> => {
    const response = await apiClient.post('/assets/refresh-prices');
    return response.data;
};

export const getAssetTransactions = async (userId: string): Promise<AssetTransaction[]> => {
    const response = await apiClient.get(`/asset-transactions/${userId}`);
    return response.data;
};
