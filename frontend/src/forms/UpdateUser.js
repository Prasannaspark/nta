import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import AdminService from '../services/AdminService';
import './UpdateUser.css'

function UpdateUser() {
  const navigate = useNavigate();
  const {id} = useParams;
  const [userData, setUserData] = useState({
    username: '',
    role: ''
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
      await AdminService.updateUserRole(userData);
      navigate("/usermanagement");
    } catch (error) {
      console.error('Error updating user : ', error);
      alert(error.message || 'An error occurred while updating user.');
    }
  };

  return (
    <div className="auth-container mt-5 pt-5">
      <h2>UPDATE ROLE</h2>
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
          <label>Role :</label>
          <select
            name="role"
            value={userData.role}
            onChange={handleInputChange}
            required
          >
              <option value="">Select Role</option>
              <option value="ROLE_SYSTEM_ADMIN">ADMIN</option>
              <option value="ROLE_HARDWARE_SUPPORT">HARDWARE SUPPORT</option>
              <option value="ROLE_ADMINISTRATIVE_SUPPORT">ADMINISTRATIVE SUPPORT</option>
              <option value="ROLE_CONNECTIVITY_SUPPORT">CONNECTIVITY SUPPORT</option>
              <option value="ROLE_PERFORMANCE_SUPPORT">PERFORMANCE SUPPORT</option>
              <option value="ROLE_USER">USER</option>
              </select>
            </div>
            <button type="submit" className="btn btn-default w-100">UPDATE</button>
        <div className="text-center">
          <button className="btn btn-default w-100" onClick={() => navigate("/usermanagement")}>Back to User Management</button>
        </div>
      </form>
    </div>
  );
}
 
export default UpdateUser;
