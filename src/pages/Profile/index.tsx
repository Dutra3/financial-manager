import { Header } from "../../components/Header";
import { SideBar } from "../../components/SideBar";
import "./Profile.css";

const Profile = () => {
    return (
        <>
            <Header title="Olá, Gabriel. Você está na aba de Perfil. Aqui você encontra seus dados pessoais, como nome, salário e metas."/>
            <SideBar page="profile"/>
            <div>
                <h1>Profile Page</h1>
            </div>
        </>
    )
};

export { Profile };