import { Card } from "../../components/Card";
import { Header } from "../../components/Header";
import { SideBar } from "../../components/SideBar";
import { getDebits } from "../../api/dashboardApi";
import "./Home.css";
import { useState } from "react";

const Home = () => {
    const [debits, setDebits] = useState<string>('');

    const getAllDebits = async () => {
        const debits = await getDebits();
        console.log(debits);
        setDebits(debits);
    }

    return (
        <main className="home-container">
            <SideBar page="dashboard"/> 
            <div className="home-content">
                <Header title="Olá, Gabriel. Aqui você encontra o resumo de suas informações financeiras."/>
                <Card title="My balance" amount={40000.00} />
                <Card title="Income" amount={4700.00} />
                <Card title="Expenses" amount={2500.00} />
                <div className="dashboard-content">
                    <h1>Dashboard Page</h1>
                </div>
                <div>
                    <button onClick={getAllDebits}>
                        Get all debits.
                    </button>
                </div>
                {debits && (
                    <div>
                        <h1>{`${debits}`}</h1>
                    </div>
                )}
            </div>
        </main>
    );
};

export { Home };