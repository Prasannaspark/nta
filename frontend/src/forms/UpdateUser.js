import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import AdminService from '../services/AdminService';
 
function UpdateUser() {
  const navigate = useNavigate();

  const [userData, setUserData] = useState({
    id: '',
    role: ''
  });

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
    <div className="auth-container">
      <h2>Update User Role</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>User ID :</label>
          <input type="number" name="id" value={userData.id } onChange={handleInputChange} />
        </div>
        <div className="form-group">
        <label htmlFor="role" className="form-label">Role :</label>
            <select className="form-select" id="role" name="role" value={userData.role} onChange={handleInputChange} required>
              <option value="">Select Role</option>
              <option value="ROLE_ADMIN">ROLE_ADMIN</option>
              <option value="ROLE_SPECIALIST_ITSUPPORT">ROLE_SPECIALIST_ITSUPPORT</option>
              <option value="ROLE_CAPABLE_ITSUPPORT">ROLE_CAPABLE_ITSUPPORT</option>
              <option value="ROLE_AVAILABLE_ITSUPPORT">ROLE_AVAILABLE_ITSUPPORT</option>
              <option value="ROLE_USER">ROLE_USER</option>
            </select>
        </div>
        <button type="submit">Update</button>
      </form>
    </div>
  );
}
 
export default UpdateUser;