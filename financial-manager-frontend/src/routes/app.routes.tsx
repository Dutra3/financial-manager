import { Route, Routes } from "react-router-dom";
import { Home } from "../pages/Home";
import { Profile } from "../pages/Profile";
import { Transactions } from "../pages/Transactions";
import { Wallet } from "../pages/Wallet";

const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/" element={<Home />}/>
            <Route path="/profile" element={<Profile />}/>
            <Route path="/transactions" element={<Transactions />}/>
            <Route path="/wallet" element={<Wallet />}/>
        </Routes>
    )
};

export { AppRoutes };