import "./Card.css";

interface CardProps {
    title: string;
    amount: number;
    currency?: string;
}

const formatCurrency = (amount: number, currency: string) => {
    return new Intl.NumberFormat("en-US", {
        style: "currency",
        currency,
        minimumFractionDigits: 2,
    }).format(amount);
};

const Card: React.FC<CardProps> = ({ title, amount, currency = "USD" }) => {
    return (
        <div className="card-container">
            <h1 className="card-title">{title}</h1>
            <p className="card-amount">{formatCurrency(amount, currency)}</p>
        </div>
    )
};

export { Card };
