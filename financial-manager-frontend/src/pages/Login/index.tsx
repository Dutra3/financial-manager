import { useState } from 'react';
import { FaGoogle, FaApple } from 'react-icons/fa';
import './Login.css';

const MainLogin = () => {
    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');

    const handleLogin = (e: React.FormEvent<HTMLFormElement>): void => {
        e.preventDefault();

        console.log('Username:', username);
        console.log('Password:', password);
    };

    const handleSocialLogin = (platform: string): void => {
        console.log(`Login with ${platform}`);
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
                        <label htmlFor="username">Username</label>
                        <input
                            type="text"
                            id="username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
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
                            onClick={() => handleSocialLogin('Google')}
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
                        onClick={() => console.log('Forgot password clicked')}
                    >
                        Forgot Password?
                    </button>
                </form>
            </div>
        </div>
    );
};

export { MainLogin };