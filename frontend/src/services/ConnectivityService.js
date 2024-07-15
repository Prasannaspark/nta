import axios from "axios";
import authHeader from "../services/auth-header";
 
const BASE_URL = "http://localhost:9090/api/connectivitysupport";

const highPriority= async () => {
    try {
      const response = await axios.get(`${BASE_URL}/highprioritytickets`, {
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

  const mediumPriority= async () => {
    try {
      const response = await axios.get(`${BASE_URL}/mediumprioritytickets`, {
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

  const lowPriority= async () => {
    try {
      const response = await axios.get(`${BASE_URL}/lowprioritytickets`, {
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


  const getTicket = async (ticketId) => {
    try {
      const response = await axios.post(`${BASE_URL}/getticket`, ticketId, {
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
  

  const  updateTicket = async (ticketData) => {
    try {
      const response = await axios.post(`${BASE_URL}/updateticket`, ticketData, {
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

  const critical= async () => {
    try {
      const response = await axios.get(`${BASE_URL}/getcriticalticket`, {
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



  const ConnectivityService = {
    highPriority,
    mediumPriority,
    lowPriority ,
    getTicket,
    updateTicket,
    critical
};

export default  ConnectivityService ;
