import apiClient from "./apiClient";
import { BondResponse } from "./bondApi";
import { StockResponse } from "./stockApi";
import { ReitResponse } from "./reitApi";

export interface WalletResponse {
    id: string,
    totalAmount: number,
    bonds: BondResponse[],
    stocks: StockResponse[],
    reits: ReitResponse[]
};

export const getWallet = async (id: string, signal?: AbortSignal) => {
    const response = await apiClient.get(`/wallets/${id}`, { signal });
    return response.data;
};
