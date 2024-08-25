// import React, { useState, useRef } from "react";
// import { useNavigate,Link } from 'react-router-dom';
// import Form from "react-validation/build/form";
// import Input from "react-validation/build/input";
// import CheckButton from "react-validation/build/button";

// import AuthService from "../services/auth.service";

// const required = (value) => {
//   if (!value) {
//     return (
//       <div className="alert alert-danger" role="alert">
//         This field is required!
//       </div>
//     );
//   }
// };

// const Login = () => {
//   let navigate = useNavigate();

//   const form = useRef();
//   const checkBtn = useRef();

//   const [username, setUsername] = useState("");
//   const [password, setPassword] = useState("");
//   const [loading, setLoading] = useState(false);
//   const [message, setMessage] = useState("");

//   const onChangeUsername = (e) => {
//     const username = e.target.value;
//     setUsername(username);
//   };

//   const onChangePassword = (e) => {
//     const password = e.target.value;
//     setPassword(password);
//   };

//   const handleLogin = (e) => {
//     e.preventDefault();

//     setMessage("");
//     setLoading(true);

//     form.current.validateAll();

//     if (checkBtn.current.context._errors.length === 0) {
//       AuthService.login(username, password).then(
//         () => {
//           navigate("/home");
//           window.location.reload();
//         },
//         (error) => {
//           const resMessage =
//             (error.response &&
//               error.response.data &&
//               error.response.data.message) ||
//             error.message ||
//             error.toString();

//           setLoading(false);
//           setMessage(resMessage);
//         }
//       );
//     } else {
//       setLoading(false);
//     }
//   };

//   return (
//       <div className="container-fluid vh-100 d-flex align-items-center justify-content-center" style={{ color: 'white' }}>
//         <div className="card p-4 shadow-lg border-0 rounded-3" style={{ maxWidth: '400px', width: '100%', backgroundColor: 'white', color: '#4078a5', fontFamily: 'Georgia' }}>
//           <div className="card-body">
//             <h3 className="card-title text-center mb-4" style={{color: '#4078a5'}}>Sign In</h3>
//             <img
//               src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
//               alt="profile-img"
//               className="profile-img-card rounded-circle mx-auto d-block mb-3"
//               style={{ width: "100px", height: "100px" }}
//             />
   
//             <Form onSubmit={handleLogin} ref={form}>
//               <div className="form-group mb-3">
//                 <label htmlFor="username" className="form-label" style={{ fontFamily: 'Georgia', color:'#4078a5'}}>Username</label>
//                 <Input
//                   type="text"
//                   className="form-control rounded-pill"
//                   name="username"
//                   value={username}
//                   onChange={onChangeUsername}
//                   validations={[required]}
//                   style={{ backgroundColor: '#f0f0f0', color: '#001f3f' }}
//                 />
//               </div>
   
//               <div className="form-group mb-3">
//                 <label htmlFor="password" className="form-label" style={{ fontFamily: 'Georgia', color:'#4078a5'}}>Password</label>
//                 <Input
//                   type="password"
//                   className="form-control rounded-pill"
//                   name="password"
//                   value={password}
//                   onChange={onChangePassword}
//                   validations={[required]}
//                   style={{ backgroundColor: '#f0f0f0', color: '#001f3f' }}
//                 />
//               </div>
   
//               <div className="d-grid mb-3">
//                 <button className="btn btn-primary btn-block rounded-pill" disabled={loading} style={{ backgroundColor: '#4078a5', borderColor: '#4078a5', fontFamily:'Georgia', color: '#f4eeff' }}>
//                   {loading && (
//                     <span className="spinner-border spinner-border-sm"></span>
//                   )}
//                   <span>Login</span>
//                 </button>
//               </div>
   
//               {message && (
//                 <div className="form-group mt-3">
//                   <div className="alert alert-danger" role="alert">
//                     {message}
//                   </div>
//                 </div>
//               )}
//               <CheckButton style={{ display: "none" }} ref={checkBtn} />
//             </Form>
//           </div>
//           <div className="card-footer text-center">
//             <div className="small" style={{ fontFamily: 'Georgia'}}>
//               Don't have an account? <Link to="/register" className="#" style={{color: '#4078a5', fontFamily: 'Georgia'}}>Sign up</Link>
//             </div>
//           </div>
//         </div>
//       </div>
//     );
//   };
   
//   export default Login;

// import React, { useState, useRef } from "react";
// import { useNavigate, Link } from 'react-router-dom';
// import Form from "react-validation/build/form";
// import Input from "react-validation/build/input";
// import CheckButton from "react-validation/build/button";
// import AuthService from "../services/auth.service";

// const required = (value) => {
//   if (!value) {
//     return (
//       <div className="alert alert-danger" role="alert">
//         This field is required!
//       </div>
//     );
//   }
// };

// const Login = () => {
//   let navigate = useNavigate();

//   const form = useRef();
//   const checkBtn = useRef();

//   const [username, setUsername] = useState("");
//   const [password, setPassword] = useState("");
//   const [loading, setLoading] = useState(false);
//   const [message, setMessage] = useState("");

//   const onChangeUsername = (e) => {
//     const username = e.target.value;
//     setUsername(username);
//   };

//   const onChangePassword = (e) => {
//     const password = e.target.value;
//     setPassword(password);
//   };

//   const handleLogin = (e) => {
//     e.preventDefault();

//     setMessage("");
//     setLoading(true);

//     form.current.validateAll();

//     if (checkBtn.current.context._errors.length === 0) {
//       AuthService.login(username, password).then(
//         () => {
//           navigate("/home");
//           window.location.reload();
//         },
//         (error) => {
//           const resMessage =
//             (error.response &&
//               error.response.data &&
//               error.response.data.message) ||
//             error.message ||
//             error.toString();

//           setLoading(false);
//           setMessage(resMessage);
//         }
//       );
//     } else {
//       setLoading(false);
//     }
//   };

//   return (
//     <div style={{
//       background: 'linear-gradient(135deg, #4078a5, #001f3f)',
//       color: 'white',
//       height: '100vh',
//       display: 'flex',
//       alignItems: 'center',
//       justifyContent: 'center'
//     }}>
//       <div style={{
//         maxWidth: '400px',
//         width: '100%',
//         backgroundColor: 'white',
//         color: '#4078a5',
//         fontFamily: 'Georgia',
//         padding: '2rem',
//         borderRadius: '1rem',
//         boxShadow: '0 8px 16px rgba(0, 0, 0, 0.2)'
//       }}>
//         <div style={{ textAlign: 'center', marginBottom: '1.5rem' }}>
//           <h3 style={{ color: '#4078a5', marginBottom: '1rem' }}>
//             <i className="fas fa-sign-in-alt"></i> Sign In
//           </h3>
//           <img
//             src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
//             alt="profile-img"
//             style={{ width: '100px', height: '100px', borderRadius: '50%', marginBottom: '1rem' }}
//           />
//         </div>
//         <Form onSubmit={handleLogin} ref={form}>
//           <div style={{ marginBottom: '1rem' }}>
//             <label htmlFor="username" style={{ fontFamily: 'Georgia', color: '#4078a5', display: 'flex', alignItems: 'center' }}>
//               <i className="fas fa-user" style={{ marginRight: '8px' }}></i> Username
//             </label>
//             <Input
//               type="text"
//               className="form-control"
//               name="username"
//               value={username}
//               onChange={onChangeUsername}
//               validations={[required]}
//               style={{
//                 backgroundColor: '#f0f0f0',
//                 color: '#001f3f',
//                 borderRadius: '50px',
//                 padding: '10px 20px'
//               }}
//             />
//           </div>
//           <div style={{ marginBottom: '1rem' }}>
//             <label htmlFor="password" style={{ fontFamily: 'Georgia', color: '#4078a5', display: 'flex', alignItems: 'center' }}>
//               <i className="fas fa-lock" style={{ marginRight: '8px' }}></i> Password
//             </label>
//             <Input
//               type="password"
//               className="form-control"
//               name="password"
//               value={password}
//               onChange={onChangePassword}
//               validations={[required]}
//               style={{
//                 backgroundColor: '#f0f0f0',
//                 color: '#001f3f',
//                 borderRadius: '50px',
//                 padding: '10px 20px'
//               }}
//             />
//           </div>
//           <div style={{ display: 'grid', marginBottom: '1rem' }}>
//             <button
//               className="btn btn-primary btn-block"
//               disabled={loading}
//               style={{
//                 backgroundColor: '#4078a5',
//                 borderColor: '#4078a5',
//                 fontFamily: 'Georgia',
//                 color: '#f4eeff',
//                 borderRadius: '50px',
//                 padding: '10px 20px'
//               }}
//             >
//               {loading && (
//                 <span className="spinner-border spinner-border-sm"></span>
//               )}
//               <span>Login</span>
//             </button>
//           </div>
//           {message && (
//             <div style={{ marginTop: '1rem' }}>
//               <div className="alert alert-danger" role="alert">
//                 {message}
//               </div>
//             </div>
//           )}
//           <CheckButton style={{ display: 'none' }} ref={checkBtn} />
//         </Form>
//         <div style={{ textAlign: 'center', marginTop: '1.5rem', fontFamily: 'Georgia' }}>
//           <div>
//             Don't have an account? <Link to="/register" style={{ color: '#4078a5' }}>Sign up</Link>
//           </div>
//         </div>
//       </div>
//     </div>
//   );
// };

// export default Login;

import React, { useState, useRef } from "react";
import { useNavigate, Link } from 'react-router-dom';
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import AuthService from "../services/auth.service";

const required = (value) => {
  if (!value) {
    return (
      <div className="alert alert-danger" role="alert">
        This field is required!
      </div>
    );
  }
};

const Login = () => {
  let navigate = useNavigate();

  const form = useRef();
  const checkBtn = useRef();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");

  const onChangeUsername = (e) => {
    const username = e.target.value;
    setUsername(username);
  };

  const onChangePassword = (e) => {
    const password = e.target.value;
    setPassword(password);
  };

  const handleLogin = (e) => {
    e.preventDefault();

    setMessage("");
    setLoading(true);

    form.current.validateAll();

    if (checkBtn.current.context._errors.length === 0) {
      AuthService.login(username, password).then(
        () => {
          navigate("/home");
          window.location.reload();
        },
        (error) => {
          const resMessage =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();

          setLoading(false);
          setMessage(resMessage);
        }
      );
    } else {
      setLoading(false);
    }
  };

  return (
    <div style={{
      background: 'linear-gradient(135deg, #4078a5, #001f3f)',
      color: 'white',
      minHeight: '100vh', // Changed from height to minHeight
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center'
    }}>
      <div style={{
        maxWidth: '400px',
        width: '100%',
        backgroundColor: 'white',
        color: '#4078a5',
        fontFamily: 'Georgia',
        padding: '2rem',
        borderRadius: '1rem',
        boxShadow: '0 8px 16px rgba(0, 0, 0, 0.2)'
      }}>
        <div style={{ textAlign: 'center', marginBottom: '1.5rem' }}>
          <h3 style={{ color: '#4078a5', marginBottom: '1rem' }}>
            <i className="fas fa-sign-in-alt"></i> Sign In
          </h3>
          <img
            src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
            alt="profile-img"
            style={{ width: '100px', height: '100px', borderRadius: '50%', marginBottom: '1rem' }}
          />
        </div>
        <Form onSubmit={handleLogin} ref={form}>
          <div style={{ marginBottom: '1rem' }}>
            <label htmlFor="username" style={{ fontFamily: 'Georgia', color: '#4078a5', display: 'flex', alignItems: 'center' }}>
              <i className="fas fa-user" style={{ marginRight: '8px' }}></i> Username
            </label>
            <Input
              type="text"
              className="form-control"
              name="username"
              value={username}
              onChange={onChangeUsername}
              validations={[required]}
              style={{
                backgroundColor: '#f0f0f0',
                color: '#001f3f',
                borderRadius: '50px',
                padding: '10px 20px'
              }}
            />
          </div>
          <div style={{ marginBottom: '1rem' }}>
            <label htmlFor="password" style={{ fontFamily: 'Georgia', color: '#4078a5', display: 'flex', alignItems: 'center' }}>
              <i className="fas fa-lock" style={{ marginRight: '8px' }}></i> Password
            </label>
            <Input
              type="password"
              className="form-control"
              name="password"
              value={password}
              onChange={onChangePassword}
              validations={[required]}
              style={{
                backgroundColor: '#f0f0f0',
                color: '#001f3f',
                borderRadius: '50px',
                padding: '10px 20px'
              }}
            />
          </div>
          <div style={{ display: 'grid', marginBottom: '1rem' }}>
            <button
              className="btn btn-primary btn-block"
              disabled={loading}
              style={{
                backgroundColor: '#4078a5',
                borderColor: '#4078a5',
                fontFamily: 'Georgia',
                color: '#f4eeff',
                borderRadius: '50px',
                padding: '10px 20px'
              }}
            >
              {loading && (
                <span className="spinner-border spinner-border-sm"></span>
              )}
              <span>Login</span>
            </button>
          </div>
          {message && (
            <div style={{ marginTop: '1rem' }}>
              <div className="alert alert-danger" role="alert">
                {message}
              </div>
            </div>
          )}
          <CheckButton style={{ display: 'none' }} ref={checkBtn} />
        </Form>
        <div style={{ textAlign: 'center', marginTop: '1.5rem', fontFamily: 'Georgia' }}>
          <div>
            Don't have an account? <Link to="/register" style={{ color: '#4078a5' }}>Sign up</Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;


