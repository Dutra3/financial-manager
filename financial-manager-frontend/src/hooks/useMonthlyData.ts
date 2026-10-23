import { useQuery } from "@tanstack/react-query";
import { getTransactions, TransactionResponse } from "../api/transactionApi";
import { getUserId } from "../utils/auth";

export interface MonthlyData {
    month: string;
    income: number;
    expenses: number;
}

const groupByMonth = (transactions: TransactionResponse[]): MonthlyData[] => {
    const map = new Map<string, MonthlyData>();

    transactions.forEach((t) => {
        const date = new Date(t.paymentDate);
        const monthKey = date.toLocaleDateString("en-US", { month: "short", year: "numeric" });
        const existing = map.get(monthKey) || { month: monthKey, income: 0, expenses: 0 };

        if (t.type === "CREDIT") {
            existing.income += t.amount || 0;
        } else {
            existing.expenses += t.amount || 0;
        }

        map.set(monthKey, existing);
    });

    return Array.from(map.values()).sort((a, b) => {
        const [aMonth] = a.month.split(" ");
        const [bMonth] = b.month.split(" ");
        return new Date(aMonth + " 1").getTime() - new Date(bMonth + " 1").getTime();
    });
};

export const useMonthlyData = () => {
    const query = useQuery({
        queryKey: ["transactions", getUserId()],
        queryFn: ({ signal }) => getTransactions(getUserId(), signal),
    });

    const monthlyData = query.data ? groupByMonth(query.data) : [];

    return {
        monthlyData,
        transactions: query.data || [],
        loading: query.isLoading,
        error: query.isError ? "Failed to load monthly data." : null,
    };
};
