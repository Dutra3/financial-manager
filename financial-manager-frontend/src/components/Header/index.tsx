import "./Header.css";

interface HeaderProps {
    title: string;
}

const Header: React.FC<HeaderProps> = ({ title }) => {
    return (
        <div>
            <h1>{title}</h1>
        </div>
    )
};

export { Header };