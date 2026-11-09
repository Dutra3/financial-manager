import { describe, it, expect } from "vitest";
import { formatAmountInput, parseAmountInput } from "./formatAmount";

describe("formatAmountInput", () => {
    it("formats digits with two decimals", () => {
        expect(formatAmountInput("123", "")).toBe("1.23");
    });

    it("strips non-numeric characters", () => {
        const out = formatAmountInput("abc1de2f", "");
        expect(parseAmountInput(out)).toBeCloseTo(0.12);
    });
});

describe("parseAmountInput", () => {
    it("parses a formatted amount", () => {
        expect(parseAmountInput("1,234.56")).toBeCloseTo(1234.56);
    });

    it("returns 0 for empty input", () => {
        expect(parseAmountInput("")).toBe(0);
    });
});
