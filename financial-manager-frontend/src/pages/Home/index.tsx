import { useState } from "react";
import { Card } from "../../components/Card";
import { Header } from "../../components/Header";
import { SideBar } from "../../components/SideBar";
import { CurrencyToggle } from "../../components/CurrencyToggle";
import { MonthlyOverview } from "../../components/MonthlyOverview";
import { DashboardKpis } from "../../components/DashboardKpis";
import { RecentTransactions } from "../../components/RecentTransactions";
import { VisualReports } from "../../components/VisualReports";
import { DashboardGoals } from "../../components/DashboardGoals";
import { DashboardBudgetAlerts } from "../../components/DashboardBudgetAlerts";
import { useDashboardData } from "../../hooks/useDashboardData";
import { useMonthlyData } from "../../hooks/useMonthlyData";
import { computeKpis } from "../../hooks/useDashboardKpis";
import { useExchangeRates, convertCurrency, Currency } from "../../utils/currency";
import "./Home.css";

const BASE_CURRENCY: Currency = "USD";

const Home = () => {
    const { balance, income, expenses, loading, error } = useDashboardData();
    const { monthlyData, transactions, loading: monthlyLoading, error: monthlyError } = useMonthlyData();
    const [currency, setCurrency] = useState<Currency>("USD");
    const { data: rates } = useExchangeRates(BASE_CURRENCY);

    const convert = (amount: number) => convertCurrency(amount, rates, currency);

    return (
        <main className="home-container">
            <SideBar page="dashboard"/>
            <div className="home-content">
                <Header title="Olá, Gabriel. Aqui você encontra o resumo de suas informações financeiras."/>
                {loading ? (
                    <p>Loading dashboard...</p>
                ) : (
                    <>
                        {error && <p className="dashboard-error">{error}</p>}
                        <CurrencyToggle currency={currency} onChange={setCurrency} />
                        <Card title="My balance" amount={convert(balance)} currency={currency} />
                        <Card title="Income" amount={convert(income)} currency={currency} />
                        <Card title="Expenses" amount={convert(expenses)} currency={currency} />
                        <DashboardKpis
                            kpis={computeKpis(transactions)}
                            rates={rates}
                            currency={currency}
                        />
                        <DashboardGoals />
                        <DashboardBudgetAlerts />
                        <VisualReports
                            transactions={transactions}
                            monthlyData={monthlyData}
                            rates={rates}
                            currency={currency}
                        />
                        <MonthlyOverview
                            data={monthlyData}
                            loading={monthlyLoading}
                            error={monthlyError}
                            rates={rates}
                            currency={currency}
                        />
                        <RecentTransactions
                            transactions={transactions}
                            loading={monthlyLoading}
                            error={monthlyError}
                            rates={rates}
                            currency={currency}
                        />
                    </>
                )}
            </div>
        </main>
    );
};

export { Home };
