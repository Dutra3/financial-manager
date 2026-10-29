import { logoutSession } from '../api/authApi';

export const getUserId = (): string => {
    return localStorage.getItem('userId') || '00000000-0000-0000-0000-000000000000';
};

export const saveSession = (token: string, refreshToken: string, userId: string, email?: string): void => {
    localStorage.setItem('token', token);
    localStorage.setItem('refreshToken', refreshToken);
    localStorage.setItem('userId', userId);
    if (email) {
        localStorage.setItem('userEmail', email);
    }
};

export const getUserEmail = (): string => {
    return localStorage.getItem('userEmail') || '';
};

export const logout = (): void => {
    const refreshToken = localStorage.getItem('refreshToken');
    if (refreshToken) {
        logoutSession(refreshToken).catch(() => {});
    }
    localStorage.removeItem('token');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('userId');
    localStorage.removeItem('userEmail');
    localStorage.removeItem('googleAuthToken');
    window.location.href = '/login';
};
