import apiClient from "./apiClient";

export interface BondTransaction {
    id: string,
    bondId: string,
    quantity: number,
    price: number,
    transactionDate: string
};

export const getBonds = async () => {
    const response = await apiClient.get('/bonds');
    return response.data;
};

export const getBond = async (id: number) => {
    const response = await apiClient.get(`/bonds/${id}`);
    return response.data;
};

export const createBondTransaction = async (bondTransaction: BondTransaction) => {
    const response = await apiClient.post('/bonds', bondTransaction);
    return response.data;
};