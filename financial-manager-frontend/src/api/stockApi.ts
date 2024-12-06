import apiClient from "./apiClient";

export interface StockTransaction {
    id: string,
    stockId: string,
    quantity: number,
    price: number,
    transactionDate: string
};

export const getStocks = async () => {
    const response = await apiClient.get('/stocks');
    return response.data;
};

export const getStock = async (id: number) => {
    const response = await apiClient.get(`/stocks/${id}`);
    return response.data;
};

export const createStockTransaction = async (stockTransaction: StockTransaction) => {
    const response = await apiClient.post('/stocks', stockTransaction);
    return response.data;
};