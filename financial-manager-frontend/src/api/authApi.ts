import apiClient from "./apiClient";

export interface LoginRequest {
    email: string;
    password: string;
};

export interface AuthResponse {
    token: string;
    refreshToken: string;
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

export const logoutSession = async (refreshToken: string) => {
    await apiClient.post('/auth/logout', { refreshToken });
};

export const forgotPassword = async (email: string) => {
    const response = await apiClient.post('/auth/forgot-password', { email });
    return response.data;
};

export const resetPassword = async (token: string, newPassword: string) => {
    const response = await apiClient.post('/auth/reset-password', { token, newPassword });
    return response.data;
};
