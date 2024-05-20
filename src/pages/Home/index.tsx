import { Header } from "../../components/Header";
import { SideBar } from "../../components/SideBar";
import "./Home.css";

const Home = () => {
    return (
        <>
            <Header title="Olá, Gabriel. Aqui você encontra o resumo de suas informações financeiras."/>
            <SideBar />
            <div>
                <h1>Dashboard Page</h1>
            </div>
        </>
    )
};

export { Home };