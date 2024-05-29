

interface CardProps {
    title: string;
    amount: number
}

const Card: React.FC<CardProps> = ({ title, amount }) => {
    return (
        <div>
            <h1>{title}</h1>
            <p>{amount}</p>
        </div>
    )
};

export { Card };