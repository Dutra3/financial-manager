import apiClient from "./apiClient";

export interface UserData {
    id: string,
    email: string,
    password: string
};

export const createUser = async (user: UserData) => {
    const response = await apiClient.post('/users', user);
    return response.data;
};
