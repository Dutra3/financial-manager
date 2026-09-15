import apiClient from "./apiClient";

export const exportTransactions = async (userId: string) => {
    const response = await apiClient.get(`/export/transactions/${userId}`, {
        responseType: "blob",
    });
    return response.data;
};

export const downloadCSV = (data: Blob, filename: string) => {
    const url = window.URL.createObjectURL(data);
    const link = document.createElement("a");
    link.href = url;
    link.download = filename;
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    window.URL.revokeObjectURL(url);
};
