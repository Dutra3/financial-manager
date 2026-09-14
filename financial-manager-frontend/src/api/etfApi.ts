import apiClient from "./apiClient";

export interface EtfTransaction {
    id: string,
    etfId: string,
    quantity: number,
    price: number,
    transactionDate: string
};

export interface EtfResponse {
    id: string,
    name: string,
    ticker: string,
    description: string,
    type: string,
    industrySegment: string,
    price: number
};

export const getEtfs = async () => {
    const response = await apiClient.get('/etfs');
    return response.data;
};

export const getEtf = async (id: string) => {
    const response = await apiClient.get(`/etfs/${id}`);
    return response.data;
};

export const createEtfTransaction = async (etfTransaction: EtfTransaction) => {
    const response = await apiClient.post('/etfs', etfTransaction);
    return response.data;
};
