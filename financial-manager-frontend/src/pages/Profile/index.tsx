import { Header } from "../../components/Header";
import { SideBar } from "../../components/SideBar";
import "./Profile.css";

const Profile = () => {
    return (
        <main className="profile-container">
            <SideBar page="profile"/>
            <div className="profile-content">
                <Header title="Olá, Gabriel. Você está na aba de Perfil. Aqui você encontra seus dados pessoais, como nome, salário e metas."/>
                <div>
                    <h1>Profile Page</h1>
                </div>
            </div>
        </main>
    )
};

export { Profile };