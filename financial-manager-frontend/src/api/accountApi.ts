import apiClient from "./apiClient";

export interface Account {
    id: string,
    name: string,
    type: string,
    initialBalance: number,
    currentBalance?: number
};

export const getAccounts = async (userId: string, signal?: AbortSignal): Promise<Account[]> => {
    const response = await apiClient.get(`/accounts/${userId}`, { signal });
    return response.data;
};

export const createAccount = async (account: Omit<Account, "id"> & { id?: string }) => {
    const response = await apiClient.post('/accounts', account);
    return response.data;
};

export const createTransfer = async (transfer: {
    fromAccountId: string,
    toAccountId: string,
    amount: number,
    date: string,
    description?: string
}) => {
    const response = await apiClient.post('/transfers', transfer);
    return response.data;
};

export const deleteAccount = async (id: string) => {
    const response = await apiClient.delete(`/accounts/${id}`);
    return response.data;
};
