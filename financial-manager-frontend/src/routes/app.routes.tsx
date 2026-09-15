import { Route, Routes } from "react-router-dom";
import { Home } from "../pages/Home";
import { Profile } from "../pages/Profile";
import { Transactions } from "../pages/Transactions";
import { Wallet } from "../pages/Wallet";
import { Goals } from "../pages/Goals";
import { Budgets } from "../pages/Budgets";
import { Login } from "../pages/Login";
import { ForgotPassword } from "../pages/ForgotPassword";
import { CreateAccount } from "../pages/CreateAccount";
import { PrivateRoute } from "./PrivateRoute";

const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/login" element={<Login />}/>
            <Route path="/forgot-password" element={<ForgotPassword />} />
            <Route path="/create-account" element={<CreateAccount />} />
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
            <Route path="/goals" element={
                <PrivateRoute>
                    <Goals />
                </PrivateRoute>}
            />
            <Route path="/budgets" element={
                <PrivateRoute>
                    <Budgets />
                </PrivateRoute>}
            />
        </Routes>
    )
};

export { AppRoutes };