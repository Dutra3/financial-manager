import { Header } from "../../components/Header";
import { SideBar } from "../../components/SideBar";
import "./Profile.css";

const Profile = () => {

    const userData = {
        name: "Gabriel Dutra",
        email: "gabriel.dutra@email.com",
        profession: "Desenvolvedor Fullstack",
        netSalary: "R$ 5.500,00",
        payday: "07",
        initialBalance: "R$ 1.200,00",
        financialGoal: "R$ 10.000,00",
        financialGoalProgress: "35%"
    };

    return (
        <main className="profile-container">
            <SideBar page="profile"/>
            <div className="profile-content">
                <Header title="Profile"/>
                
                <div className="profile-info">
                    <div className="profile-header">
                        <div className="profile-name">
                            <h1>{userData.name}</h1>
                            <p className="profile-profession">{userData.profession}</p>
                        </div>
                    </div>
                    <div className="profile-details">
                        <div className="profile-section">
                            <h2>Personal Infos</h2>
                            <div className="info-grid">
                                <div className="info-item">
                                    <label>Full Name</label>
                                    <span>{userData.name}</span>
                                </div>
                                <div className="info-item">
                                    <label>Email</label>
                                    <span>{userData.email}</span>
                                </div>
                                <div className="info-item">
                                    <label>Profession</label>
                                    <span>{userData.profession}</span>
                                </div>
                            </div>
                        </div>
                        <div className="profile-section">
                            <h2>Financial Infos</h2>
                            <div className="info-grid">
                                <div className="info-item">
                                    <label>Net Salary</label>
                                    <span className="salary-amount">{userData.netSalary}</span>
                                </div>
                                <div className="info-item">
                                    <label>Payday</label>
                                    <span>{userData.payday}</span>
                                </div>
                                <div className="info-item">
                                    <label>Initial Balance</label>
                                    <span className="balance-amount">{userData.initialBalance}</span>
                                </div>
                            </div>
                        </div>
                        <div className="profile-section">
                            <h2>Financial Goal</h2>
                            <div className="goal-container">
                                <div className="goal-amount">
                                    <span className="goal-value">{userData.financialGoal}</span>
                                </div>
                                <div className="goal-progress">
                                    <div className="progress-bar">
                                        <div className="progress-fill" style={{width: '35%'}}></div>
                                    </div>
                                    <span className="progress-text">{userData.financialGoalProgress} alcançado</span>
                                </div>
                            </div>
                        </div>
                        <div className="profile-actions">
                            <button className="btn-primary">Edit Perfil</button>
                            <button className="btn-secondary">Change Financial Goal</button>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    );
};

export { Profile };