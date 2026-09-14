import { useState } from "react";
import { Card } from "../../components/Card";
import { Header } from "../../components/Header";
import { SideBar } from "../../components/SideBar";
import { CurrencyToggle } from "../../components/CurrencyToggle";
import { useDashboardData } from "../../hooks/useDashboardData";
import { useExchangeRates, convertCurrency, Currency } from "../../utils/currency";
import "./Home.css";

const BASE_CURRENCY: Currency = "USD";

const Home = () => {
    const { balance, income, expenses, loading, error } = useDashboardData();
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
                    </>
                )}
                <div className="dashboard-content">
                    <h1>Dashboard Page</h1>
                </div>
            </div>
        </main>
    );
};

export { Home };
