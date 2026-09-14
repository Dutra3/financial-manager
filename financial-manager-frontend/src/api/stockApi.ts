import apiClient from "./apiClient";

export interface StockTransaction {
    id: string,
    stockId: string,
    quantity: number,
    price: number,
    transactionDate: string
};

export interface StockResponse {
    id: string,
    name: string,
    ticker: string,
    description: string,
    type: string,
    industrySegment: string,
    tagAlong: number,
    price: number,
    peRatio: number | null,
    dividendYield: number | null,
    pbRatio: number | null,
    lastDividend: number | null,
    isBesst: boolean,
    isNewMarket: boolean
};

export const getStocks = async () => {
    const response = await apiClient.get('/stocks');
    return response.data;
};

export const getStock = async (id: string) => {
    const response = await apiClient.get(`/stocks/${id}`);
    return response.data;
};

export const createStockTransaction = async (stockTransaction: StockTransaction) => {
    const response = await apiClient.post('/stocks', stockTransaction);
    return response.data;
};
