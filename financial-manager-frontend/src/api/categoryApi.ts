import apiClient from "./apiClient";

export type CategoryType = 'debit' | 'credit';

export interface Category {
    id: string,
    name: string,
    type: CategoryType
};

export const getCategories = async () => {
    const response = await apiClient.get('/categories');
    return response.data;
};

export const getCategory = async (id: number) => {
    const response = await apiClient.get(`/categories/${id}`);
    return response.data;
};

export const createCategory = async (category: Category) => {
    const response = await apiClient.post('/categories', category);
    return response.data;
};