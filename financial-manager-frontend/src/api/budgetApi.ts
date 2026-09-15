import apiClient from "./apiClient";

export interface Budget {
    id: string;
    categoryId: string;
    amount: number;
    month: number;
    year: number;
    categoryName?: string;
};

export const getBudgets = async (userId: string) => {
    const response = await apiClient.get(`/budgets/${userId}`);
    return response.data;
};

export const getBudgetsByMonth = async (userId: string, month: number, year: number) => {
    const response = await apiClient.get(`/budgets/${userId}/${month}/${year}`);
    return response.data;
};

export const createBudget = async (budget: Budget) => {
    const response = await apiClient.post('/budgets', budget);
    return response.data;
};

export const deleteBudget = async (id: string) => {
    const response = await apiClient.delete(`/budgets/${id}`);
    return response.data;
};

export interface BudgetAlert {
    categoryId: string;
    categoryName: string;
    budgetAmount: number;
    spentAmount: number;
    percentage: number;
    status: 'OK' | 'WARNING' | 'EXCEEDED';
};

export const getBudgetAlerts = async (userId: string, month: number, year: number) => {
    const response = await apiClient.get(`/budgets/alerts/${userId}/${month}/${year}`);
    return response.data;
};
