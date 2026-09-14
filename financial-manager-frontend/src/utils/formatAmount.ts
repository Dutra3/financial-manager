export const formatAmountInput = (raw: string, prevValue: string): string => {
    const rawDigits = raw.replace(/\D/g, "");
    const prevDigits = prevValue.replace(/\D/g, "");
    const MAX_CENTS = 99999999999;

    let cents: string;

    if (rawDigits.length < prevDigits.length) {
        cents = prevDigits.slice(0, -1);
    } else if (rawDigits.length > prevDigits.length) {
        cents = prevDigits + rawDigits.slice(prevDigits.length);
    } else {
        cents = rawDigits;
    }

    if (cents.length > 12) cents = cents.slice(0, 12);

    if (cents.length === 0) return "";

    const centsNum = parseInt(cents, 10);
    if (isNaN(centsNum) || centsNum === 0) return "0.00";

    const cappedCents = Math.min(centsNum, MAX_CENTS);

    const value = cappedCents / 100;
    const formatted = value.toLocaleString("en-US", {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2,
    });

    return formatted;
};

export const parseAmountInput = (formatted: string): number => {
    return parseFloat(formatted.replace(/,/g, "")) || 0;
};
