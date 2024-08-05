import { Link } from 'react-router-dom';
import { Logo } from '../Logo';
import HomeOutlinedIcon from '@mui/icons-material/HomeOutlined';
import PaidOutlinedIcon from '@mui/icons-material/PaidOutlined';
import AccountBalanceWalletOutlinedIcon from '@mui/icons-material/AccountBalanceWalletOutlined';
import AccountCircleOutlinedIcon from '@mui/icons-material/AccountCircleOutlined';
import "./SideBar.css";

interface SideBarProps {
    page: string;
}

const SideBar: React.FC<SideBarProps> = ({ page }) => {
    return (
        <div className="sidebar">
            <div className="logo">
                <Logo />
            </div>
            <nav>
                <ul>
                    <li>
                        <Link to="/" className={page == "dashboard" ? "active" : ""}>
                            <HomeOutlinedIcon />
                            Dashboard
                        </Link>
                    </li>
                    <li>
                        <Link to="/transactions" className={page == "transactions" ? "active" : ""}>
                            <PaidOutlinedIcon />
                            Transactions
                        </Link>
                    </li>
                    <li>
                        <Link to="/wallet" className={page == "wallet" ? "active" : ""}>
                            <AccountBalanceWalletOutlinedIcon />
                            Wallet
                        </Link>
                    </li>
                    <li>
                        <Link to="/profile" className={page == "profile" ? "active" : ""}>
                            <AccountCircleOutlinedIcon />
                            Profile
                        </Link>
                    </li>
                </ul>
            </nav>
        </div>
    )
};

export { SideBar };