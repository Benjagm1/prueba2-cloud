import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import 'bootstrap/dist/css/bootstrap-grid.min.css'
import { AuthProvider } from 'react-oidc-context';

const cognitoAuthConfig = {
  authority: import.meta.env.VITE_COGNITO_AUTHORITY || "https://cognito-idp.sa-east-1.amazonaws.com/sa-east-1_B3GggKnss",
  client_id: import.meta.env.VITE_COGNITO_CLIENT_ID || "32o0rot47qlmghao6reid7ftoj",
  redirect_uri: window.location.origin, // Usa la URL actual (funciona en localhost y en Amplify)
  response_type: "code",
  scope: "phone openid email",
};

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <AuthProvider {...cognitoAuthConfig}>
    <App />
    </AuthProvider>
  </StrictMode>,
)
