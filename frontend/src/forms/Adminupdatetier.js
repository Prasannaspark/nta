import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import AdminService from '../services/AdminService';
import AuthService from "../services/auth.service";
import './UpdateUser.css'

function Adminupdatetier() {
  const navigate = useNavigate();
  const {id} = useParams;
  const [userData, setUserData] = useState({
    username:'',
    customertier: '',
    reqchange: 'UPDATED'
  });
  

  useEffect(() => {
    fetchUserDataById(id);
  }, [id]);
 
  const fetchUserDataById = async (id) => {
    try {
      const response = await AdminService.getUser(id);
      if (response) {
        setUserData(response);
      } else {
        console.error('Error: User data is undefined.');
      }
    } catch (error) {
      console.error('Error fetching user data : ', error);
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
      await AdminService.updateusertier(userData);
      navigate("/usermanagement");
    } catch (error) {
      console.error('Error updating user : ', error);
      alert(error.message || 'An error occurred while updating user.');
    }
  };

  return (
    <div className="auth-container mt-5 pt-5">
      <h2>UPDATE Tier</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>User Name :</label>
          <input
            type="text"
            name="username"
            value={userData.username || ''}
            onChange={handleInputChange}
            required
          />
        </div>
        <div className="form-group">
          <label>Tier:</label>
          <select
            name="customertier"
            value={userData.customertier}
            onChange={handleInputChange}
            required
          >
              <option value="">Select Tier</option>
              <option value="BRONZE">BRONZE</option>
              <option value="SILVER">SILVER</option>
              <option value="GOLD">GOLD</option>
              </select>
            </div>
            {/* <div className="form-group">
          <label>REQUEST UPDATE NOTIFY:</label>
          <select
            name="reqchange"
            value={userData.reqchange}
            onChange={handleInputChange}
            required
          >
              <option value="">Select Change</option>
              <option value="UPDATED">Modified as Request</option>
              </select>
            </div> */}
            <button type="submit" className="btn btn-default w-100">UPDATE</button>
        <div className="text-center">
          <button className="btn btn-default w-100" onClick={() => navigate("/usermanagement")}>Back to User Management</button>
        </div>
      </form>
    </div>
  );
}
 
export default Adminupdatetier;
