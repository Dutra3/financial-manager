import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import './ForgotPassword.css';

const ForgotPassword: React.FC = () => {
    const [email, setEmail] = useState<string>('');
    const [showMessage, setShowMessage] = useState<boolean>(false);
    const [countdown, setCountdown] = useState<number>(5);

    const navigate = useNavigate();

    const handleReturn = () => {
        navigate('/login');
    };
    
    const handleForgotPassword = (e: React.FormEvent<HTMLFormElement>): void => {
        e.preventDefault();
        console.log(`Send recovery email to: ${email}`);
        setShowMessage(true);

        setTimeout(() => {
            navigate("/login");
        }, 5000);
    }

    useEffect(() => {
        if (showMessage && countdown > 0) {
            const timer = setTimeout(() => {
                setCountdown((prev) => prev - 1);
            }, 1000);
            return () => clearTimeout(timer);
        }
    }, [showMessage, countdown]);

    return (
        <div className="forgot-password-container">
            <div className="forgot-password-data">
                <div className="forgot-password-header">
                    <button className="return-button" onClick={handleReturn}>
                        &#8592;
                    </button>
                    <h2>Forgot my password</h2>
                </div>
                {showMessage ? (
                    <div className="confirmation-message">
                        <CheckCircleIcon style={{ color: 'green', fontSize: '48px' }} />
                        <p>If the email is registered, you will receive a link to reset your password shortly.</p>
                        <p>Redirecting to the login page in <strong>{countdown}</strong> seconds...</p>
                    </div>
                ) : (
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
                )}
            </div>
        </div>
    );
};

export { ForgotPassword };