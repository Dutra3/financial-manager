import apiClient from "./apiClient";

export interface ReitTransaction {
    id: string,
    reitId: string,
    quantity: number,
    price: number,
    transactionDate: string
};

export interface ReitResponse {
    id: string,
    name: string,
    ticker: string,
    description: string,
    type: string,
    industrySegment: string,
    price: number
};

export const getReits = async () => {
    const response = await apiClient.get('/reits');
    return response.data;
};

export const getReit = async (id: string) => {
    const response = await apiClient.get(`/reits/${id}`);
    return response.data;
};

export const createReitTransaction = async (reitTransaction: ReitTransaction) => {
    const response = await apiClient.post('/reits', reitTransaction);
    return response.data;
};
