import apiClient from "./apiClient";

export interface Goal {
    id: string,
    name: string,
    description: string,
    targetDate: string,
    targetAmount: number,
    isAchieved: boolean
};

export const getGoals = async (userId: string) => {
    const response = await apiClient.get(`/goals/${userId}`);
    return response.data;
};

export const createGoal = async (goal: Goal) => {
    const response = await apiClient.post('/goals', goal);
    return response.data;
};

export const deleteGoal = async (id: string) => {
    const response = await apiClient.delete(`/goals/${id}`);
    return response.data;
};

export const getCurrentBalance = async (userId: string) => {
    const response = await apiClient.get(`/goals/balance/${userId}`);
    return response.data;
};
