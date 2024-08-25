// import React, { useState, useEffect } from 'react';
// import { useNavigate, useParams } from 'react-router-dom';
// import CustomerService from '../services/CustomerService';
// import './UpdateUser.css'

// function CustomerRequesttoupdatetier() {
//   const navigate = useNavigate();
//   const {id} = useParams;
//   const [userData, setUserData] = useState({
//     username: '',
//     request: ''
//   });

//   useEffect(() => {
//     fetchUserDataById(id);
//   }, [id]);
 
//   const fetchUserDataById = async (id) => {
//     try {
//       const response = await CustomerService.getUser(id);
//       if (response) {
//         setUserData(response);
//       } else {
//         console.error('Error: User data is undefined.');
//       }
//     } catch (error) {
//       console.error('Error fetching user data : ', error);
//     }
//   };

//   const handleInputChange = (e) => {
//     const { name, value } = e.target;
//     setUserData((prevUserData) => ({
//       ...prevUserData,
//       [name]: value
//     }));
//   };

//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     try {
//       await CustomerService.RequestTier(userData);
//     } catch (error) {
//       console.error('Error updating user : ', error);
//       alert(error.message || 'An error occurred while updating user.');
//     }
//   };

//   return (
//     <div className="auth-container mt-5 pt-5">
//       <h2>Request for Tier </h2>
//       <form onSubmit={handleSubmit}>
//         <div className="form-group">
//           <label>User Name :</label>
//           <input
//             type="text"
//             name="username"
//             value={userData.username || ''}
//             onChange={handleInputChange}
//             required
//           />
//         </div>
//         <div className="form-group">
//           <label>Request Tier</label>
//           <select
//             name="request"
//             value={userData.request}
//             onChange={handleInputChange}
//             required
//           >
//               <option value="">Select The Tier </option>
//               <option value="BRONZE">Pay 20$ for Bronze</option>
//               <option value="SILVER">pay 50$ for Silver</option>
//               <option value="GOLD">pay 100$ for Gold</option>
//               </select>
//             </div>
          
//             <button type="submit" className="btn btn-default w-100">Place Request</button>
            
//       </form>
//     </div>
//   );
// }
 
// export default CustomerRequesttoupdatetier;

import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import AuthService from "../services/auth.service";
import CustomerService from '../services/CustomerService';
import './UpdateUser.css';

function CustomerRequestToUpdateTier() {
  const navigate = useNavigate();
  const { id } = useParams();
  const [userData, setUserData] = useState({
    
    username: AuthService.getCurrentUser().username,
    request: ''
  });
  const [submissionStatus, setSubmissionStatus] = useState(null);

  useEffect(() => {
    fetchUserDataById(id);
  }, [id]);

  const fetchUserDataById = async (id) => {
    try {
      const response = await CustomerService.getUser(id);
      if (response) {
        setUserData(response);
      } else {
        console.error('Error: User data is undefined.');
      }
    } catch (error) {
      console.error('Error fetching user data: ', error);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUserData((prevUserData) => ({
      ...prevUserData,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await CustomerService.RequestTier(userData);
      setSubmissionStatus('success');
    } catch (error) {
      console.error('Error updating user: ', error);
      setSubmissionStatus('error');
      alert(error.message || 'An error occurred while updating user.');
    }
  };

  return (
    <div className="auth-container mt-5 pt-5">
      <h2>Request for Tier Modification</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>User Name:</label>
          <input
            type="text"
            name="username"
            value={userData.username || ''}
              readOnly
          />
        </div>
        <div className="form-group">
          <label>Request Tier Type</label>
          <select
            name="request"
            value={userData.request}
            onChange={handleInputChange}
            required
          >
            <option value="">Select The Tier</option>
            <option value="BRONZE">Pay Rs 20 for Bronze</option>
            <option value="SILVER">Pay Rs 50 for Silver</option>
            <option value="GOLD">Pay Rs 100 for Gold</option>
          </select>
        </div>
        <button type="submit" className="btn btn-default w-100">Place Request</button>
      </form>
            {submissionStatus === 'success' && <div className="alert alert-success">Request placed successfully</div>}
    </div>
  );
}

export default CustomerRequestToUpdateTier;
