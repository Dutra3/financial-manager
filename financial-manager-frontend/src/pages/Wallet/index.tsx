import { Header } from "../../components/Header";
import { SideBar } from "../../components/SideBar";
import "./Wallet.css";

const Wallet = () => {
    return (
        <main className="home-container">
            <SideBar page="wallet"/>
            <div className="wallet-content">
                <Header title="Olá, Gabriel. Você está na aba de Carteira. Aqui você encontra suas ações e fundos imobiliários cadastrados, podendo também alterar a quantidade de cada um deles."/>
                <div>
                    <h1>Wallet Page</h1>
                </div>
                <div>
                     <label htmlFor="category-type">Type:</label>
                        <select
                           id="category-type"
                           name="type"
                        >
                            <option value="debit">Debit</option>
                            <option value="credit">Credit</option>
                        </select>
                </div>
            </div>
        </main>
    )
};

export { Wallet };