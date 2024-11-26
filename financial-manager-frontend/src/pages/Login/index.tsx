import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import { FaGoogle, FaApple } from 'react-icons/fa';
import { useGoogleLogin } from '@react-oauth/google';
import './Login.css';

const Login = () => {
    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');

    const navigate = useNavigate();

    const handleLogin = (e: React.FormEvent<HTMLFormElement>): void => {
        e.preventDefault();

        console.log('Username:', username);
        console.log('Password:', password);
    };

    const handleSocialLogin = (platform: string): void => {
        console.log(`Login with ${platform}`);
    };

    const handleGoogleLoginSuccess = (tokenResponse: { access_token: string }): void => {
        localStorage.setItem('googleAuthToken', tokenResponse.access_token);
        console.log(tokenResponse);

        navigate('/');
    };

    const handleGoogleLoginError = (): void => {
        console.error('Login Failed: An error occurred during the login process.');
    };

    const googleLogin = useGoogleLogin({
        onSuccess: handleGoogleLoginSuccess,
        onError: handleGoogleLoginError,
    });

    const handleForgotPassword = (): void => {
        navigate("/forgot-password");
    }

    const handleCreateAccount = (): void => {
        navigate("/create-account");
    };

    return (
        <div className="login-container">
            <div className="left-section">
                <div>
                    <p>Empower Your Financial Future with Ease.</p>
                    <p>Track, Plan, and Achieve Your Financial Goals.</p>
                    <p>Take Control of Your Finances, Anytime, Anywhere.</p>
                </div>
            </div>
            <div className="right-section">
                <form className="login-form" onSubmit={handleLogin}>
                    <h2>Login</h2>
                    <div className="form-group">
                        <label htmlFor="username">Email</label>
                        <input
                            type="text"
                            id="email"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            placeholder="Enter your email"
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="password">Password</label>
                        <input
                            type="password"
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Enter your password"
                            required
                        />
                    </div>
                    <button type="submit" className="login-button">
                        Login
                    </button>
                    <div className="social-login-container">
                        <button
                            type="button"
                            className="social-login-button google"
                            onClick={() => googleLogin()}
                        >
                            <FaGoogle className="social-icon" /> Login with Google
                        </button>
                        <button
                            type="button"
                            className="social-login-button apple"
                            onClick={() => handleSocialLogin('Apple')}
                        >
                            <FaApple className="social-icon" /> Login with Apple
                        </button>
                    </div>
                    <button
                        type="button"
                        className="forgot-password-button"
                        onClick={handleForgotPassword}
                    >
                        Forgot Password?
                    </button>
                    <button
                        type="button"
                        className="create-account-button"
                        onClick={handleCreateAccount}
                    >
                        Create Account
                    </button>
                </form>
            </div>
        </div>
    );
};

export { Login };