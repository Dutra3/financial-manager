import "./Card.css";

interface CardProps {
    title: string;
    amount: number
}

const Card: React.FC<CardProps> = ({ title, amount }) => {
    return (
        <div className="card-body">
            <h1 className="card-title">{title}</h1>
            <p className="card-amount">{amount}</p>
        </div>
    )
};

export { Card };