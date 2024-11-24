import React from 'react';
import ReactDOM from 'react-dom/client';
import { Routes } from './routes/Routes';
import { GoogleOAuthProvider } from "@react-oauth/google"
import "./global.css";

ReactDOM.createRoot(document.getElementById('root')!).render(
	<GoogleOAuthProvider clientId='528599693427-r4u7o6atffjr6k7k5r5itdvtq6vr48an.apps.googleusercontent.com'>
		<React.StrictMode>
    		<Routes />
  		</React.StrictMode>
	</GoogleOAuthProvider>
)
