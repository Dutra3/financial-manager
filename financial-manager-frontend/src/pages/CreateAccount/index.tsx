import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { createUser } from "../../api/authApi";
import "./CreateAccount.css";

const CreateAccount = () => {
    const [email, setEmail] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [confirmPassword, setConfirmPassword] = useState<string>('');
    const [error, setError] = useState<string>('');
    const [success, setSuccess] = useState<string>('');

    const navigate = useNavigate();

    const handleReturn = () => {
        navigate('/login');
    };

    const validateForm = (): boolean => {
        if (password !== confirmPassword) {
            setError('Passwords do not match');
            return false;
        }
        if (password.length < 8) {
            setError('Password must be at least 8 characters');
            return false;
        }
        setError('');
        return true;
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!validateForm()) return;
        try {
            await createUser({ id: crypto.randomUUID(), email, password });
            setSuccess('Account created successfully! Redirecting to login...');
            setTimeout(() => navigate('/login'), 2000);
        } catch (err: any) {
            setError(err?.response?.status === 409 ? 'Email already registered' : 'Failed to create account');
        }
    };

    return (
        <div className="create-account-container">
            <div className="create-account-data">
                <div className="create-account-header">
                    <button className="return-button" onClick={handleReturn}>
                        &#8592;
                    </button>
                    <h2>Create Account</h2>
                </div>
                <form onSubmit={handleSubmit} className="create-account-form">
                    <div className="create-account-form-group">
                        <label htmlFor="email">Email</label>
                        <input
                            type="email"
                            id="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            placeholder="Enter your email"
                            required
                        />
                    </div>
                    <div className="create-account-form-group">
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
                    <div className="create-account-form-group">
                        <label htmlFor="confirm-password">Confirm Password</label>
                        <input
                            type="password"
                            id="confirm-password"
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                            placeholder="Confirm your password"
                            required
                        />
                    </div>

                    {error && <p className="error-message">{error}</p>}
                    {success && <p className="success-message">{success}</p>}

                    <button
                        type="submit"
                        className="create-account-final-button"
                        disabled={password !== confirmPassword || password.length < 8}
                    >
                        Create Account
                    </button>
                </form>
            </div>
        </div>
    );
};

export { CreateAccount };