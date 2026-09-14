import { useQuery } from "@tanstack/react-query";
import { getCredits, getDebits } from "../api/dashboardApi";
import { getWallet } from "../api/walletApi";

const PLACEHOLDER_USER_ID = "00000000-0000-0000-0000-000000000000";
const PLACEHOLDER_WALLET_ID = "00000000-0000-0000-0000-000000000000";

const sumAmounts = (items: { amount: number }[] | undefined) =>
    (items || []).reduce((sum, item) => sum + (item.amount || 0), 0);

export const useDashboardData = () => {
    const creditsQuery = useQuery({
        queryKey: ["credits", PLACEHOLDER_USER_ID],
        queryFn: ({ signal }) => getCredits(PLACEHOLDER_USER_ID, signal),
    });

    const debitsQuery = useQuery({
        queryKey: ["debits", PLACEHOLDER_USER_ID],
        queryFn: ({ signal }) => getDebits(PLACEHOLDER_USER_ID, signal),
    });

    const walletQuery = useQuery({
        queryKey: ["wallet", PLACEHOLDER_WALLET_ID],
        queryFn: async ({ signal }) => {
            try {
                return await getWallet(PLACEHOLDER_WALLET_ID, signal);
            } catch (err: any) {
                if (err?.response?.status === 404) return null;
                throw err;
            }
        },
        retry: false,
    });

    const income = sumAmounts(creditsQuery.data);
    const expenses = sumAmounts(debitsQuery.data);
    const balance = walletQuery.data?.totalAmount || 0;

    const loading = creditsQuery.isLoading || debitsQuery.isLoading || walletQuery.isLoading;

    const allFailed = creditsQuery.isError && debitsQuery.isError && walletQuery.isError;
    const error = allFailed ? "Unable to connect to the server. Showing default values." : null;

    return { balance, income, expenses, loading, error };
};
