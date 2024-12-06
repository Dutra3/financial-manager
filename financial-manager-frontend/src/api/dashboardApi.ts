import apiClient from "./apiClient"

export const getDebits = async () => {
    const response = await apiClient.get('/dashboards');
    return response.data;
};