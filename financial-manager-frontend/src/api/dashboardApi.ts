import apiClient from "./apiClient";

export interface DebitResponse {
    amount: number;
}

export interface CreditResponse {
    amount: number;
}

export const getDebits = async (userId: string, signal?: AbortSignal): Promise<DebitResponse[]> => {
    const response = await apiClient.get(`/dashboards/debits/${userId}`, { signal });
    return response.data;
};

export const getCredits = async (userId: string, signal?: AbortSignal): Promise<CreditResponse[]> => {
    const response = await apiClient.get(`/dashboards/credits/${userId}`, { signal });
    return response.data;
};

export interface NetWorth {
    accountsBalance: number,
    investmentsValue: number,
    total: number
};

export const getNetWorth = async (userId: string): Promise<NetWorth> => {
    const response = await apiClient.get(`/dashboards/net-worth/${userId}`);
    return response.data;
};
