import React from 'react';
import ReactDOM from 'react-dom/client';
import { Routes } from './routes/Routes';
import { GoogleOAuthProvider } from "@react-oauth/google"
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
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

ReactDOM.createRoot(document.getElementById('root')!).render(
	<GoogleOAuthProvider clientId='528599693427-r4u7o6atffjr6k7k5r5itdvtq6vr48an.apps.googleusercontent.com'>
		<QueryClientProvider client={queryClient}>
			<React.StrictMode>
				<Routes />
			</React.StrictMode>
		</QueryClientProvider>
	</GoogleOAuthProvider>
)
