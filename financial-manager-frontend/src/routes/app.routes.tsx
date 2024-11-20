import { Route, Routes } from "react-router-dom";
import { Home } from "../pages/Home";
import { Profile } from "../pages/Profile";
import { Transactions } from "../pages/Transactions";
import { Wallet } from "../pages/Wallet";
import { Login } from "../pages/Login";
import { PrivateRoute } from "./PrivateRoute";

const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/login" element={<Login />}/>
            <Route path="/" element={
                <PrivateRoute>
                    <Home />
                </PrivateRoute>}
            />
            <Route path="/profile" element={
                <PrivateRoute>
                    <Profile />
                </PrivateRoute>}
            />
            <Route path="/transactions" element={
                <PrivateRoute>
                    <Transactions />
                </PrivateRoute>}
            />
            <Route path="/wallet" element={
                <PrivateRoute>
                    <Wallet />
                </PrivateRoute>}
            />
        </Routes>
    )
};

export { AppRoutes };