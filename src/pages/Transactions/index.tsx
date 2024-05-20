import { Header } from "../../components/Header";
import { SideBar } from "../../components/SideBar";
import "./Transactions.css";

const Transactions = () => {
    return (
        <>
            <Header title="Olá, Gabriel. Você está na aba de Transações. Aqui você encontra suas despesas e receitas passadas, além de poder cadastrar novos dados de despesas e receitas."/>
            <SideBar />
            <div>
                <h1>Transactions Page</h1>
            </div>
        </>
    )
};

export { Transactions };