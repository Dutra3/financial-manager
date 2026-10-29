import React, { useState, useEffect } from 'react';
import LightModeOutlinedIcon from '@mui/icons-material/LightModeOutlined';
import DarkModeOutlinedIcon from '@mui/icons-material/DarkModeOutlined';
import LogoutIcon from '@mui/icons-material/Logout';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { getMyProfile, updateProfile } from '../../api/profileApi';
import { logout } from '../../utils/auth';
import "./Header.css";

interface HeaderProps {
    title: string;
}

const applyTheme = (theme: string) => {
    document.body.classList.toggle('dark-theme', theme === 'dark');
    document.body.classList.toggle('light-theme', theme !== 'dark');
    localStorage.setItem('theme', theme);
};

const Header: React.FC<HeaderProps> = ({ title }) => {
    const queryClient = useQueryClient();
    const [isDarkMode, setIsDarkMode] = useState(() => {
        return localStorage.getItem('theme') === 'dark';
    });

    const { data: profile } = useQuery({
        queryKey: ["profile", "me"],
        queryFn: getMyProfile,
        retry: false,
        staleTime: 5 * 60 * 1000,
    });

    const themeMutation = useMutation({
        mutationFn: (theme: string) => {
            if (!profile) return Promise.resolve();
            return updateProfile(profile.id, { ...profile, theme });
        },
        onSuccess: () => queryClient.invalidateQueries({ queryKey: ["profile", "me"] }),
    });

    useEffect(() => {
        applyTheme(localStorage.getItem('theme') || 'light');
    }, []);

    useEffect(() => {
        if (profile?.theme) {
            applyTheme(profile.theme);
            setIsDarkMode(profile.theme === 'dark');
        }
    }, [profile]);

    const toggleTheme = () => {
        const next = isDarkMode ? 'light' : 'dark';
        applyTheme(next);
        setIsDarkMode(!isDarkMode);
        themeMutation.mutate(next);
    };

    const handleLogout = () => {
        logout();
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
                    <LogoutIcon />
                </button>
            </div>
        </div>
    );
};

export { Header };
