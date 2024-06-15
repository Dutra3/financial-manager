import { Header } from "../../components/Header";
import { SideBar } from "../../components/SideBar";
import "./Wallet.css";

const Wallet = () => {
    return (
        <main className="home-container">
            <SideBar page="wallet"/>
            <div className="main-content">
                <Header title="Olá, Gabriel. Você está na aba de Carteira. Aqui você encontra suas ações e fundos imobiliários cadastrados, podendo também alterar a quantidade de cada um deles."/>
                <div>
                    <h1>Wallet Page</h1>
                </div>
            </div>
        </main>
    )
};

export { Wallet };