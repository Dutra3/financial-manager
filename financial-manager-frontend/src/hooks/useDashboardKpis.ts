import { TransactionResponse } from "../api/transactionApi";

export interface KpisData {
    savingsRate: number;
    avgMonthlyExpense: number;
    topCategory: { name: string; amount: number } | null;
    monthComparison: { incomeDelta: number; expensesDelta: number } | null;
}

const getMonthKey = (dateStr: string) => {
    const date = new Date(dateStr);
    return `${date.getFullYear()}-${date.getMonth()}`;
};

export const computeKpis = (transactions: TransactionResponse[]): KpisData => {
    if (transactions.length === 0) {
        return { savingsRate: 0, avgMonthlyExpense: 0, topCategory: null, monthComparison: null };
    }

    // Group by month
    const monthlyMap = new Map<string, { income: number; expenses: number }>();
    const categoryMap = new Map<string, number>();

    transactions.forEach((t) => {
        const key = getMonthKey(t.paymentDate);
        const existing = monthlyMap.get(key) || { income: 0, expenses: 0 };

        if (t.type === "CREDIT") {
            existing.income += t.amount || 0;
        } else {
            existing.expenses += t.amount || 0;
            categoryMap.set(t.category, (categoryMap.get(t.category) || 0) + (t.amount || 0));
        }

        monthlyMap.set(key, existing);
    });

    const months = Array.from(monthlyMap.entries()).sort((a, b) => a[0].localeCompare(b[0]));

    // Total income/expenses across all months
    const totalIncome = months.reduce((sum, [, v]) => sum + v.income, 0);
    const totalExpenses = months.reduce((sum, [, v]) => sum + v.expenses, 0);
    const savingsRate = totalIncome > 0 ? ((totalIncome - totalExpenses) / totalIncome) * 100 : 0;

    // Average monthly expense
    const avgMonthlyExpense = months.length > 0 ? totalExpenses / months.length : 0;

    // Top category by total spend
    let topCategory: { name: string; amount: number } | null = null;
    for (const [name, amount] of categoryMap.entries()) {
        if (!topCategory || amount > topCategory.amount) {
            topCategory = { name, amount };
        }
    }

    // Month vs previous month comparison (last two months)
    let monthComparison: { incomeDelta: number; expensesDelta: number } | null = null;
    if (months.length >= 2) {
        const last = months[months.length - 1][1];
        const prev = months[months.length - 2][1];
        monthComparison = {
            incomeDelta: last.income - prev.income,
            expensesDelta: last.expenses - prev.expenses,
        };
    }

    return { savingsRate, avgMonthlyExpense, topCategory, monthComparison };
};
