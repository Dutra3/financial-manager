import { useQuery } from "@tanstack/react-query";

export type Currency = "USD" | "BRL" | "EUR" | "CAD" | "GBP";

export const CURRENCIES: { code: Currency; label: string; flag: string }[] = [
    { code: "USD", label: "US Dollar", flag: "🇺🇸" },
    { code: "BRL", label: "Brazilian Real", flag: "🇧🇷" },
    { code: "EUR", label: "Euro", flag: "🇪🇺" },
    { code: "CAD", label: "Canadian Dollar", flag: "🇨🇦" },
    { code: "GBP", label: "British Pound", flag: "🇬🇧" },
];

interface FrankfurterResponse {
    amount: number;
    base: string;
    date: string;
    rates: Record<string, number>;
}

const fetchRates = async (base: Currency): Promise<Record<string, number>> => {
    const symbols = CURRENCIES
        .map((c) => c.code)
        .filter((c) => c !== base)
        .join(",");

    const response = await fetch(
        `https://api.frankfurter.dev/v1/latest?from=${base}&to=${symbols}`
    );

    if (!response.ok) throw new Error("Failed to fetch exchange rates");

    const data: FrankfurterResponse = await response.json();
    return { ...data.rates, [base]: 1 };
};

export const useExchangeRates = (base: Currency = "USD") => {
    return useQuery({
        queryKey: ["exchange-rates", base],
        queryFn: () => fetchRates(base),
        staleTime: 1000 * 60 * 60, // 1 hour
        refetchOnWindowFocus: false,
        retry: 1,
    });
};

export const convertCurrency = (
    amount: number,
    rates: Record<string, number> | undefined,
    to: Currency
): number => {
    if (!rates) return amount;
    const rate = rates[to] ?? 1;
    return amount * rate;
};
