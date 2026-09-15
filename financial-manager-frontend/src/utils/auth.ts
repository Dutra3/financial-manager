export const getUserId = (): string => {
    return localStorage.getItem('userId') || '00000000-0000-0000-0000-000000000000';
};

export const logout = (): void => {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    localStorage.removeItem('googleAuthToken');
    window.location.href = '/login';
};
