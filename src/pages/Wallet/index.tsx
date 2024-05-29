import { Header } from "../../components/Header";
import { SideBar } from "../../components/SideBar";
import "./Wallet.css";

const Wallet = () => {
    return (
        <>
            <Header title="Olá, Gabriel. Você está na aba de Carteira. Aqui você encontra suas ações e fundos imobiliários cadastrados, podendo também alterar a quantidade de cada um deles."/>
            <SideBar page="wallet"/>
            <div>
                <h1>Wallet Page</h1>
            </div>
        </>
    )
};

export { Wallet };