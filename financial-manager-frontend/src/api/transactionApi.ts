import apiClient from "./apiClient";

export interface Transaction {
    id: string,
    name: string,
    description: string,
    amount: number,
    paymentDate: string,
    categoryId: string
};

export interface TransactionResponse {
    id: string,
    name: string,
    description: string,
    amount: number,
    paymentDate: string,
    type: string,
    category: string
};

export const getTransactions = async (userId: string) => {
    const response = await apiClient.get(`/transactions/${userId}`);
    return response.data;
};

export const createTransaction = async (transaction: Transaction) => {
    const response = await apiClient.post('/transactions', transaction);
    return response.data;
};

export const deleteTransaction = async (id: string) => {
    const response = await apiClient.delete(`/transactions/${id}`);
    return response.data;
};
