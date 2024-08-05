import { Link } from 'react-router-dom';
import "./SideBar.css"

interface SideBarProps {
    page: string;
}

const SideBar: React.FC<SideBarProps> = ({ page }) => {
    return (
        <div className="sidebar">
            <div className="logo">Financial Manager</div>
            <nav>
                <ul>
                    <li>
                        <Link to="/" className={page == "dashboard" ? "active" : ""}>Dashboard</Link>
                    </li>
                    <li>
                        <Link to="/transactions" className={page == "transactions" ? "active" : ""}>Transactions</Link>
                    </li>
                    <li>
                        <Link to="/wallet" className={page == "wallet" ? "active" : ""}>Wallet</Link>
                    </li>
                    <li>
                        <Link to="/profile" className={page == "profile" ? "active" : ""}>Profile</Link>
                    </li>
                </ul>
            </nav>
        </div>
    )
};

export { SideBar };