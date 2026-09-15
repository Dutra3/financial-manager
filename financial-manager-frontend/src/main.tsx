import React from 'react';
import ReactDOM from 'react-dom/client';
import { Routes } from './routes/Routes';
import { GoogleOAuthProvider } from "@react-oauth/google"
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { ThemeProvider, createTheme } from "@mui/material/styles";
import "./global.css";

const queryClient = new QueryClient({
	defaultOptions: {
		queries: {
			retry: 1,
			refetchOnWindowFocus: false,
			staleTime: 30_000,
		},
	},
});

const theme = createTheme({
	palette: {
		primary: { main: "#2a56e9" },
	},
	shape: {
		borderRadius: 10,
	},
	typography: {
		button: {
			textTransform: "none",
			fontWeight: 600,
		},
	},
	components: {
		MuiButton: {
			defaultProps: {
				size: "medium",
				disableElevation: true,
			},
			styleOverrides: {
				root: {
					borderRadius: 8,
					paddingLeft: 16,
					paddingRight: 16,
				},
			},
		},
		MuiPaper: {
			styleOverrides: {
				root: {
					backgroundImage: "none",
				},
			},
		},
	},
});

ReactDOM.createRoot(document.getElementById('root')!).render(
	<GoogleOAuthProvider clientId='528599693427-r4u7o6atffjr6k7k5r5itdvtq6vr48an.apps.googleusercontent.com'>
		<QueryClientProvider client={queryClient}>
			<ThemeProvider theme={theme}>
				<LocalizationProvider dateAdapter={AdapterDateFns}>
					<React.StrictMode>
						<Routes />
					</React.StrictMode>
				</LocalizationProvider>
			</ThemeProvider>
		</QueryClientProvider>
	</GoogleOAuthProvider>
)
