

const ForgotPassword: React.FC = () => {
    return (
        <div>
            <h2>Forgot my password</h2>
            <form>
                <div className="form-group">
                    <label>Email:</label>
                    <input type="email" placeholder="Digite seu email" />
                </div>
                <button type="submit">Recovery password</button>
            </form>
        </div>
    );
};

export { ForgotPassword };