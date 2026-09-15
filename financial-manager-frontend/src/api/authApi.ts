import apiClient from "./apiClient";

export interface LoginRequest {
    email: string;
    password: string;
};

export interface AuthResponse {
    token: string;
    userId: string;
    email: string;
};

export interface UserData {
    id: string;
    email: string;
    password: string;
};

export const login = async (request: LoginRequest) => {
    const response = await apiClient.post('/auth/login', request);
    return response.data as AuthResponse;
};

export const googleLogin = async (accessToken: string) => {
    const response = await apiClient.post('/auth/google', { accessToken });
    return response.data as AuthResponse;
};

export const createUser = async (user: UserData) => {
    const response = await apiClient.post('/users', user);
    return response.data;
};
