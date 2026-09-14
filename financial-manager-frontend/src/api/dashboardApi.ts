import apiClient from "./apiClient";

export const getDebits = async (userId: string) => {
    const response = await apiClient.get(`/dashboards/debits/${userId}`);
    return response.data;
};

export const getCredits = async (userId: string) => {
    const response = await apiClient.get(`/dashboards/credits/${userId}`);
    return response.data;
};
