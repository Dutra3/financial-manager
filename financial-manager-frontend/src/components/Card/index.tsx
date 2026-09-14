import "./Card.css";

interface CardProps {
    title: string;
    amount: number;
    currency?: string;
}

const LOCALE: Record<string, string> = {
    USD: "en-US",
    BRL: "pt-BR",
    EUR: "de-DE",
    CAD: "en-CA",
    GBP: "en-GB",
};

const formatCurrency = (amount: number, currency: string) => {
    const locale = LOCALE[currency] || "en-US";
    return new Intl.NumberFormat(locale, {
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
