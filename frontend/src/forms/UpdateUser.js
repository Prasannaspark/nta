import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import AdminService from '../services/AdminService';
import './UpdateUser.css'

function UpdateUser() {
  const navigate = useNavigate();
  const {id} = useParams;
  const [userData, setUserData] = useState({
    id: '',
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
          <label>User ID :</label>
          <input
            type="number"
            name="userId"
            value={userData.userId || ''}
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
              <option value="ROLE_ADMIN">ROLE ADMIN</option>
              <option value="ROLE_SPECIALIST_ITSUPPORT">ROLE SPECIALIST ITSUPPORT</option>
              <option value="ROLE_CAPABLE_ITSUPPORT">ROLE CAPABLE ITSUPPORT</option>
              <option value="ROLE_AVAILABLE_ITSUPPORT">ROLE AVAILABLE ITSUPPORT</option>
              <option value="ROLE_USER">ROLE USER</option>
              </select>
            </div>
            <button type="submit" className="btn btn-default w-100">UPDATE</button>
        <div className="card-footer text-center">
          <button className="btn btn-default" onClick={() => navigate("/usermanagement")}>Back to User Management</button>
        </div>
      </form>
    </div>
  );
}
 
export default UpdateUser;
