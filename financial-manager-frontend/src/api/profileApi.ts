import apiClient from "./apiClient";

export interface Profile {
    id: string,
    name: string,
    profession: string,
    salary: number,
    payDay: string,
    financialGoal: number
};

export const createProfile = async (profile: Profile) => {
    const response = await apiClient.post('/profiles', profile);
    return response.data;
};