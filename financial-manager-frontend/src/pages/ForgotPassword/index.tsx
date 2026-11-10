import { useState } from "react";
import { useNavigate } from "react-router-dom";
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import { forgotPassword, resetPassword } from "../../api/authApi";
import './ForgotPassword.css';

const ForgotPassword: React.FC = () => {
    const [email, setEmail] = useState<string>('');
    const [token, setToken] = useState<string>('');
    const [newPassword, setNewPassword] = useState<string>('');
    const [confirmPassword, setConfirmPassword] = useState<string>('');
    const [step, setStep] = useState<"request" | "reset" | "done">("request");
    const [message, setMessage] = useState<string>('');
    const [isSubmitting, setIsSubmitting] = useState<boolean>(false);

    const navigate = useNavigate();

    const handleReturn = () => {
        navigate('/login');
    };

    const handleForgotPassword = async (e: React.FormEvent<HTMLFormElement>): Promise<void> => {
        e.preventDefault();
        setIsSubmitting(true);
        setMessage('');
        try {
            await forgotPassword(email);
            setStep("reset");
        } catch (err: any) {
            setMessage(err?.response?.data?.message || "Failed to request password reset");
        } finally {
            setIsSubmitting(false);
        }
    };

    const handleResetPassword = async (e: React.FormEvent<HTMLFormElement>): Promise<void> => {
        e.preventDefault();
        if (newPassword !== confirmPassword) {
            setMessage("Passwords do not match");
            return;
        }
        setIsSubmitting(true);
        setMessage('');
        try {
            await resetPassword(token.trim(), newPassword);
            setStep("done");
            setTimeout(() => navigate("/login"), 3000);
        } catch (err: any) {
            setMessage(err?.response?.data?.error || "Invalid or expired token");
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <div className="forgot-password-container">
            <div className="forgot-password-data">
                <div className="forgot-password-header">
                    <button className="return-button" onClick={handleReturn}>
                        &#8592;
                    </button>
                    <h2>Forgot my password</h2>
                </div>
                {step === "done" ? (
                    <div className="confirmation-message">
                        <CheckCircleIcon style={{ color: 'green', fontSize: '48px' }} />
                        <p>Password reset successfully! Redirecting to login...</p>
                    </div>
                ) : step === "request" ? (
                    <form className="forgot-password-form" onSubmit={handleForgotPassword}>
                        <div className="forgot-form-group">
                            <label>Email:</label>
                            <input
                                type="email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                placeholder="Write your email"
                                required />
                        </div>
                        <button className="recovery-button" type="submit" disabled={isSubmitting}>
                            {isSubmitting ? "Sending..." : "Recovery password"}
                        </button>
                        {message && <p className="error-message">{message}</p>}
                    </form>
                ) : (
                    <>
                        <p style={{ marginBottom: "1rem" }}>
                            If the email is registered, a reset token was issued. Enter it below with your new password.
                        </p>
                        <form className="forgot-password-form" onSubmit={handleResetPassword}>
                            <div className="forgot-form-group">
                                <label>Reset Token:</label>
                                <input
                                    type="text"
                                    value={token}
                                    onChange={(e) => setToken(e.target.value)}
                                    placeholder="Paste the reset token"
                                    required />
                            </div>
                            <div className="forgot-form-group">
                                <label>New Password:</label>
                                <input
                                    type="password"
                                    value={newPassword}
                                    onChange={(e) => setNewPassword(e.target.value)}
                                    placeholder="Min 8 chars, uppercase, digit, special"
                                    required />
                            </div>
                            <div className="forgot-form-group">
                                <label>Confirm Password:</label>
                                <input
                                    type="password"
                                    value={confirmPassword}
                                    onChange={(e) => setConfirmPassword(e.target.value)}
                                    placeholder="Repeat the new password"
                                    required />
                            </div>
                            <button className="recovery-button" type="submit" disabled={isSubmitting}>
                                {isSubmitting ? "Resetting..." : "Reset password"}
                            </button>
                            {message && <p className="error-message">{message}</p>}
                        </form>
                    </>
                )}
            </div>
        </div>
    );
};

export { ForgotPassword };
