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

const priorityseverityset = async (userData) => {
  try {
    const response = await axios.post(`${BASE_URL}/allotpriorityandseverity`, userData, {
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



const getTicketAgingReport = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/ticketAgingReport`, {
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

const getPriorityAnalysisReport = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/priorityAnalysisReport`, {
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

const getResolutionTimeReport = async () => {
  try {
    const response = await axios.get(`${BASE_URL}/resolutionTimeReport`, {
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



const updateusertier = async (userData) => {
  try {
    const response = await axios.post(`${BASE_URL}/updatetierofuser`, userData, {
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
    viewTickets,
    priorityseverityset,
    getTicket,
    getTicketAgingReport,
    getPriorityAnalysisReport,
    getResolutionTimeReport,
    updateusertier
  };

export default Admin;


