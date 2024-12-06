import apiClient from "./apiClient";

export interface ReitTransaction {
    id: string,
    etfId: string,
    quantity: number,
    price: number,
    transactionDate: string
};

export const getReits = async () => {
    const response = await apiClient.get('/reits');
    return response.data;
};

export const getReit = async (id: number) => {
    const response = await apiClient.get(`/reits/${id}`);
    return response.data;
};

export const createReitTransaction = async (reitTransaction: ReitTransaction) => {
    const response = await apiClient.post('/reits', reitTransaction);
    return response.data;
};