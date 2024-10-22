import { useState } from 'react';
import "./Login.css";

const Login = () => {
    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');

    const handleLogin = (e: React.FormEvent<HTMLFormElement>): void => {
        e.preventDefault();
        
        console.log('Username:', username);
        console.log('Password:', password);
    };

    const handleForgotPassword = (): void => {
        console.log('Forgot password clicked');
    };

    return (
        <div className="login-container">
            <form className="login-form" onSubmit={handleLogin}>
                <h2>Login Page</h2>
            
                <div className="form-group">
                    <label htmlFor="username">Username</label>
                    <input
                    type="text"
                    id="username"
                    value={username}
                    onChange={
                        (e: React.ChangeEvent<HTMLInputElement>) => setUsername(e.target.value)
                    }
                    placeholder="Enter your username"
                    required
                    />
                </div>
            
                <div className="form-group">
                    <label htmlFor="password">Password</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={
                            (e: React.ChangeEvent<HTMLInputElement>) => setPassword(e.target.value)
                        }
                        placeholder="Enter your password"
                        required
                    />
                </div>
            
                <button type="submit" className="login-button">
                    Login
                </button>
                <button
                    type="button"
                    className="forgot-password-button"
                    onClick={handleForgotPassword}
                >
                    Forgot Password?
                </button>
            </form>
        </div>
    );
};

export { Login };