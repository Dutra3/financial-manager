import { Card } from "../../components/Card";
import { Header } from "../../components/Header";
import { SideBar } from "../../components/SideBar";
import { useDashboardData } from "../../hooks/useDashboardData";
import "./Home.css";

const Home = () => {
    const { balance, income, expenses, loading, error } = useDashboardData();

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
                        <Card title="My balance" amount={balance} />
                        <Card title="Income" amount={income} />
                        <Card title="Expenses" amount={expenses} />
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
