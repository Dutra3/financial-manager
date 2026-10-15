import { Link } from 'react-router-dom';
import { Tooltip } from '@mui/material';
import HomeOutlinedIcon from '@mui/icons-material/HomeOutlined';
import PaidOutlinedIcon from '@mui/icons-material/PaidOutlined';
import AccountBalanceWalletOutlinedIcon from '@mui/icons-material/AccountBalanceWalletOutlined';
import AccountCircleOutlinedIcon from '@mui/icons-material/AccountCircleOutlined';
import FlagOutlinedIcon from '@mui/icons-material/FlagOutlined';
import AccountBalanceOutlinedIcon from '@mui/icons-material/AccountBalanceOutlined';
import "./SideBar.css";

interface SideBarProps {
    page: string;
}

const NAV_ITEMS = [
    { to: "/", key: "dashboard", label: "Dashboard", icon: <HomeOutlinedIcon /> },
    { to: "/transactions", key: "transactions", label: "Transactions", icon: <PaidOutlinedIcon /> },
    { to: "/wallet", key: "wallet", label: "Wallet", icon: <AccountBalanceWalletOutlinedIcon /> },
    { to: "/goals", key: "goals", label: "Goals", icon: <FlagOutlinedIcon /> },
    { to: "/budgets", key: "budgets", label: "Budgets", icon: <AccountBalanceOutlinedIcon /> },
    { to: "/profile", key: "profile", label: "Profile", icon: <AccountCircleOutlinedIcon /> },
];

const SideBar: React.FC<SideBarProps> = ({ page }) => {
    return (
        <div className="sidebar">
            <div className="logo">
                <span className="logo-mark">FM</span>
            </div>
            <nav>
                <ul>
                    {NAV_ITEMS.map((item) => (
                        <li key={item.key}>
                            <Tooltip title={item.label} placement="right" arrow>
                                <Link to={item.to} className={page === item.key ? "active" : ""}>
                                    {item.icon}
                                </Link>
                            </Tooltip>
                        </li>
                    ))}
                </ul>
            </nav>
        </div>
    )
};

export { SideBar };
