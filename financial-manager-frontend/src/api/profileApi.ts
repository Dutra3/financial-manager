import apiClient from "./apiClient";

export interface Profile {
    id: string,
    name: string,
    profession: string,
    salary: number,
    payDay: number,
    initialBalance: number,
    financialGoal: number
};

export const createProfile = async (profile: Profile) => {
    const response = await apiClient.post('/profiles', profile);
    return response.data;
};

export const getProfile = async (id: string) => {
    const response = await apiClient.get(`/profiles/${id}`);
    return response.data;
};

export const updateProfile = async (id: string, profile: Profile) => {
    const response = await apiClient.put(`/profiles/${id}`, profile);
    return response.data;
};