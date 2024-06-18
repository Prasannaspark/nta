import axios from "axios";
import authHeader from "../services/auth-header";
 
const BASE_URL = "http://localhost:9090/api/admin";
 
const updateUserRole = async (userData) => {
    try {
      const response = await axios.post(`${BASE_URL}/updateuserrole`, userData, {
        headers: {
          'Content-Type': 'application/json',
          ...authHeader(),
          'Access-Control-Allow-Origin': '*'
        }
      });
      return response.data;
    } catch (err) {
      throw err;
    }
  };
  
  const viewUsers = async () => {
    try {
      const response = await axios.get(`${BASE_URL}/getallusers`, {
        headers: {
          'Content-Type': 'application/json',
          ...authHeader(),
          'Access-Control-Allow-Origin': '*'
        }
      });
      
      return response.data;
     
    } catch (err) {
      throw err;
    }
    
  };

const viewTickets= async () => {
  try {
    const response = await axios.get(`${BASE_URL}/getalltickets`, {
      headers: {
        'Content-Type': 'application/json',
        ...authHeader(),
        'Access-Control-Allow-Origin': '*'
      }
    });
    return response.data;
   
  } catch (err) {
    throw err;
  }
  
};



const Admin = {
    updateUserRole,
    viewUsers,
    viewTickets
};

export default Admin;