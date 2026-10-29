import apiClient from "./apiClient";

export interface Transaction {
    id: string,
    name: string,
    description: string,
    amount: number,
    paymentDate: string,
    categoryId: string,
    userId: string,
    accountId?: string | null
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

export interface Page<T> {
    content: T[],
    totalElements: number,
    totalPages: number,
    number: number,
    size: number
};

export const getTransactions = async (userId: string, signal?: AbortSignal): Promise<TransactionResponse[]> => {
    const response = await apiClient.get(`/transactions/${userId}`, {
        signal,
        params: { page: 0, size: 100, sort: "paymentDate,desc" },
    });
    return response.data.content;
};

export const createTransaction = async (transaction: Transaction) => {
    const response = await apiClient.post('/transactions', transaction);
    return response.data;
};

export const updateTransaction = async (id: string, transaction: Transaction) => {
    const response = await apiClient.put(`/transactions/${id}`, transaction);
    return response.data;
};

export const deleteTransaction = async (id: string) => {
    const response = await apiClient.delete(`/transactions/${id}`);
    return response.data;
};
