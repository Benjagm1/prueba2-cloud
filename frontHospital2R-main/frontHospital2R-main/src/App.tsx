import { useAuth } from 'react-oidc-context';
import { PatientApp } from './components/PatientApp';

function App() {

  const auth = useAuth();

  //Funcion para cerrar sesion redirigiendo a Cognito
  const handleSignOut = () => {
    auth.removeUser();
  }

  //1. Mientras cognito verifica si hay una sesion activa
  if(auth.isLoading){
    return (
      <div className="container text-center mt-5">
        <p className="fs-4 text-secondary">Cargando sesion</p>
      </div>
    )
  }

  //2. Si ocurre un error durante el inicio de sesion
  if(auth.error){
    return (
      <div className="container text-center mt-5">
        <p className="text-danger fs-5">Error de autenticación: {auth.error.message}</p>
        <button className="btn btn-primary mt-2" onClick={() => auth.signinRedirect()}>
          Reintentar inicio de sesión
        </button>
      </div>
    );
  }

  // 3. Si el usuario YA inició sesión correctamente
  if (auth.isAuthenticated) {
    return (
      <div>
        {/* Barra superior con información del usuario y botón de salir */}
        <header className="navbar navbar-dark bg-dark px-4 mb-4 shadow-sm">
          <span className="navbar-brand mb-0 h1">Sistema de Gestión de Pacientes</span>
          <div className="d-flex align-items-center gap-3">
            <span className="text-light">
              Bienvenido, <strong>{auth.user?.profile.email}</strong>
            </span>
            <button className="btn btn-outline-light btn-sm" onClick={handleSignOut}>
              Cerrar sesión
            </button>
          </div>
        </header>

        {/* Aplicación principal */}
        <PatientApp />
      </div>
    );
  }

  // 4. Si el usuario NO está autenticado
  return (
    <div className="container text-center mt-5">
      <div className="card shadow p-5 mx-auto" style={{ maxWidth: '480px' }}>
        <h2 className="mb-3 text-primary">Gestión de Pacientes</h2>
        <p className="text-muted mb-4">Inicia sesión con tu cuenta de AWS Cognito para acceder al sistema.</p>
        <button className="btn btn-primary btn-lg w-100" onClick={() => auth.signinRedirect()}>
          Iniciar Sesión
        </button>
      </div>
    </div>
  );
}

export default App;