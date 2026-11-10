import { describe, it, expect, vi, beforeEach } from "vitest";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { AccountsSection } from "./index";
import * as accountApi from "../../api/accountApi";

vi.mock("../../api/accountApi", () => ({
    getAccounts: vi.fn(),
    createAccount: vi.fn(),
    createTransfer: vi.fn(),
    deleteAccount: vi.fn(),
}));

const mockedApi = vi.mocked(accountApi);

const renderSection = () => {
    const client = new QueryClient({ defaultOptions: { queries: { retry: false } } });
    return render(
        <QueryClientProvider client={client}>
            <AccountsSection />
        </QueryClientProvider>
    );
};

describe("AccountsSection", () => {
    beforeEach(() => vi.clearAllMocks());

    it("shows empty state when no accounts exist", async () => {
        mockedApi.getAccounts.mockResolvedValue([]);
        renderSection();

        await waitFor(() => {
            expect(screen.getByText(/No accounts yet/i)).toBeInTheDocument();
        });
    });

    it("renders account list with names and balances", async () => {
        mockedApi.getAccounts.mockResolvedValue([
            { id: "1", name: "Nubank", type: "CHECKING", initialBalance: 1000, currentBalance: 1500 },
            { id: "2", name: "Inter", type: "SAVINGS", initialBalance: 2000, currentBalance: 2000 },
        ]);
        renderSection();

        await waitFor(() => {
            expect(screen.getByText("Nubank")).toBeInTheDocument();
            expect(screen.getByText("Inter")).toBeInTheDocument();
            expect(screen.getByText("$1,500.00")).toBeInTheDocument();
        });
    });

    it("creates an account through the form", async () => {
        mockedApi.getAccounts.mockResolvedValue([]);
        mockedApi.createAccount.mockResolvedValue({ id: "9", name: "XP", type: "INVESTMENT", initialBalance: 500 });
        renderSection();

        await waitFor(() => screen.getByText("Add Account"));
        fireEvent.click(screen.getByText("Add Account"));

        const [nameInput, balanceInput] = screen.getAllByRole("textbox");
        fireEvent.change(nameInput, { target: { value: "XP" } });
        fireEvent.change(balanceInput, { target: { value: "50000" } });

        const createBtn = screen.getByRole("button", { name: "Create Account" });
        fireEvent.click(createBtn);

        await waitFor(() => {
            expect(mockedApi.createAccount).toHaveBeenCalledWith(
                expect.objectContaining({ name: "XP", type: "CHECKING" })
            );
        });
    });
});
