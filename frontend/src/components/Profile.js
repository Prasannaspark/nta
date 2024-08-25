// import React from "react";
// import AuthService from "../services/auth.service";

// const Profile = () => {
//   const currentUser = AuthService.getCurrentUser();

//   return (
//     <div className="container mt-5">
//       <div className="row justify-content-center">
//         <div className="col-md-6">
//           <div className="card shadow">
//             <div className="card-body">
//             <h3 className="card-title mb-4 text-center" style={{ backgroundColor: '#4078a5', color: 'white', padding: '15px', borderRadius: '10px', height: '60px', fontFamily: 'Georgia' }}>USER PROFILE</h3>
 
//               <div className="mb-3"style={{fontFamily: 'Georgia' }}>
//                 <strong>Username : </strong> {currentUser.username}
//               </div>
//               <div className="mb-3"style={{fontFamily: 'Georgia' }}>
//                 <strong>Authorities : </strong> {currentUser.role}
//               </div>
//             </div>
//           </div>
//         </div>
//       </div>
//     </div>
//   );
// };
// export default Profile;

// import React from "react";
// import AuthService from "../services/auth.service";

// const Profile = () => {
//   const currentUser = AuthService.getCurrentUser();

//   return (
//     <div className="container mt-5">
//       <div className="row justify-content-center">
//         <div className="col-md-8">
//           <div className="card shadow-lg">
//             <div className="card-body p-5">
//               <h3 className="card-title mb-4 text-center" style={{
//                 backgroundColor: '#4078a5',
//                 color: 'white',
//                 padding: '20px',
//                 borderRadius: '15px',
//                 fontFamily: 'Georgia, serif',
//                 fontSize: '28px',
//                 letterSpacing: '1px',
//                 boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)'
//               }}>
//                 USER PROFILE
//               </h3>
              
//               <div className="profile-info mt-5">
//                 <div className="mb-4" style={{
//                   fontFamily: 'Georgia, serif',
//                   fontSize: '18px',
//                   borderBottom: '2px solid #e0e0e0',
//                   paddingBottom: '10px'
//                 }}>
//                   <strong style={{color: '#4078a5'}}>Username:</strong> 
//                   <span className="ml-2">{currentUser.username}</span>
//                 </div>

//                 <div className="mb-4" style={{
//                   fontFamily: 'Georgia, serif',
//                   fontSize: '18px',
//                   borderBottom: '2px solid #e0e0e0',
//                   paddingBottom: '10px'
//                 }}>
//                   <strong style={{color: '#4078a5'}}>Authorities:</strong> 
//                   <span className="ml-2">{currentUser.role}</span>
//                 </div>
//               </div>
//             </div>
//           </div>
//         </div>
//       </div>
//     </div>
//   );
// };

// export default Profile;

import React from "react";
import AuthService from "../services/auth.service";

const Profile = () => {
  const currentUser = AuthService.getCurrentUser();
  console.log(currentUser);

  return (
    
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-8">
          <div className="card shadow-lg">
            <div className="card-body p-5">
              <h3 className="card-title mb-4 text-center" style={{
                backgroundColor: '#4078a5',
                color: 'white',
                padding: '20px',
                borderRadius: '15px',
                fontFamily: 'Georgia, serif',
                fontSize: '28px',
                letterSpacing: '1px',
                boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)'
              }}>
                👤 USER PROFILE
              </h3>
              
              <div className="profile-info mt-5">
                <div className="mb-4" style={{
                  fontFamily: 'Georgia, serif',
                  fontSize: '18px',
                  borderBottom: '2px solid #e0e0e0',
                  paddingBottom: '10px',
                  display: 'flex',
                  alignItems: 'center'
                }}>
                  <span style={{fontSize: '24px', marginRight: '10px'}}>🆔</span>
                  <strong style={{color: '#4078a5'}}>Username:</strong> 
                  <span className="ml-2">{currentUser.username}</span>
                </div>
                <div className="mb-4" style={{
                  fontFamily: 'Georgia, serif',
                  fontSize: '18px',
                  borderBottom: '2px solid #e0e0e0',
                  paddingBottom: '10px',
                  display: 'flex',
                  alignItems: 'center'
                }}>
                  <span style={{fontSize: '24px', marginRight: '10px'}}>🛡️</span>
                  <strong style={{color: '#4078a5'}}>Authorities:</strong> 
                  <span className="ml-2">{currentUser.role}</span>
                </div>
                <div className="mb-4" style={{
                  fontFamily: 'Georgia, serif',
                  fontSize: '18px',
                  borderBottom: '2px solid #e0e0e0',
                  paddingBottom: '10px',
                  display: 'flex',
                  alignItems: 'center'
                }}>
                  <span style={{fontSize: '24px', marginRight: '10px'}}>🛡️</span>
                  <strong style={{color: '#4078a5'}}>TIER :</strong> 
                  
                  <span className="ml-2">{currentUser.tier}</span>
                </div>

              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Profile;


