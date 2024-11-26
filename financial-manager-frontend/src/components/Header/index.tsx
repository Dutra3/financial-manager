import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import LightModeOutlinedIcon from '@mui/icons-material/LightModeOutlined';
import DarkModeOutlinedIcon from '@mui/icons-material/DarkModeOutlined';
import "./Header.css";

interface HeaderProps {
    title: string;
}

const Header: React.FC<HeaderProps> = ({ title }) => {
    const [isDarkMode, setIsDarkMode] = useState(() => {
        const savedTheme = localStorage.getItem('theme');
        return savedTheme === 'dark';
    });

    const navigate = useNavigate();

    useEffect(() => {
        const savedTheme = localStorage.getItem('theme');
        if (savedTheme === 'dark') {
            document.body.classList.add('dark-theme');
            document.body.classList.remove('light-theme');
        } else {
            document.body.classList.add('light-theme');
            document.body.classList.remove('dark-theme');
        }
    }, []);

    const toggleTheme = () => {
        const isDarkMode = document.body.classList.toggle('dark-theme');
        document.body.classList.toggle('light-theme', !isDarkMode);
        localStorage.setItem('theme', isDarkMode ? 'dark' : 'light');
        setIsDarkMode(isDarkMode);
    };

    const handleLogout = () => {
        localStorage.removeItem('authToken');
        localStorage.removeItem('googleAuthToken');
        
        navigate("/login");
    };

    return (
        <div className="header-container">
            <div className="header-title">
                <h2>{title}</h2>
            </div>
            <div className="header-toggle">
                <button onClick={toggleTheme} className="theme-toggle-button">
                    {isDarkMode ? <LightModeOutlinedIcon /> : <DarkModeOutlinedIcon />}
                </button>
                <button onClick={handleLogout} className="logout-button">
                    Logout
                </button>
            </div>
        </div>
    );
};

export { Header };
