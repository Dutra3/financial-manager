import { useState } from "react";
import './ForgotPassword.css';

const ForgotPassword: React.FC = () => {
    const [email, setEmail] = useState<string>('');

    const handleForgotPassword = (e: React.FormEvent<HTMLFormElement>): void => {
        e.preventDefault();
        console.log(`Send recovery email to: ${email}`);
    }

    return (
        <div className="forgot-password-container">
            <div className="forgot-password-data">
                <h2>Forgot my password</h2>
                <form className="forgot-password-form" onSubmit={handleForgotPassword}>
                    <div className="forgot-form-group">
                        <label>Email:</label>
                        <input 
                            type="email"
                            onChange={(e) => setEmail(e.target.value)} 
                            placeholder="Write your email"
                            required />
                    </div>
                    <button className="recovery-button" type="submit">Recovery password</button>
                </form>
            </div>
        </div>
    );
};

export { ForgotPassword };