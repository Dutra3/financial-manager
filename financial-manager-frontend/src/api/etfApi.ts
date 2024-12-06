import apiClient from "./apiClient";

export interface EtfTransaction {
    id: string,
    etfId: string,
    quantity: number,
    price: number,
    transactionDate: string
};

export const getEtfs = async () => {
    const response = await apiClient.get('/etfs');
    return response.data;
};

export const getEtf = async (id: number) => {
    const response = await apiClient.get(`/etfs/${id}`);
    return response.data;
};

export const createEtfTransaction = async (etfTransaction: EtfTransaction) => {
    const response = await apiClient.post('/etfs', etfTransaction);
    return response.data;
};