import { useQuery } from "@tanstack/react-query";
import { getCredits, getDebits } from "../api/dashboardApi";
import { getCurrentBalance } from "../api/goalApi";
import { getUserId } from "../utils/auth";

const sumAmounts = (items: { amount: number }[] | undefined) =>
    (items || []).reduce((sum, item) => sum + (item.amount || 0), 0);

export const useDashboardData = () => {
    const userId = getUserId();

    const creditsQuery = useQuery({
        queryKey: ["credits", userId],
        queryFn: ({ signal }) => getCredits(userId, signal),
    });

    const debitsQuery = useQuery({
        queryKey: ["debits", userId],
        queryFn: ({ signal }) => getDebits(userId, signal),
    });

    const balanceQuery = useQuery({
        queryKey: ["balance", userId],
        queryFn: async () => {
            try {
                return await getCurrentBalance(userId);
            } catch (err: any) {
                if (err?.response?.status === 404) return 0;
                throw err;
            }
        },
        retry: false,
    });

    const income = sumAmounts(creditsQuery.data);
    const expenses = sumAmounts(debitsQuery.data);
    const balance = balanceQuery.data ?? 0;

    const loading = creditsQuery.isLoading || debitsQuery.isLoading || balanceQuery.isLoading;

    const allFailed = creditsQuery.isError && debitsQuery.isError && balanceQuery.isError;
    const error = allFailed ? "Unable to connect to the server. Showing default values." : null;

    return { balance, income, expenses, loading, error };
};
