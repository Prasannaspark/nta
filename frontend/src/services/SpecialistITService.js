import axios from "axios";
import authHeader from "../services/auth-header";

const BASE_URL = "http://localhost:9090/api/specialist";

const viewCriticalTickets = async () => {
    try {
      const response = await axios.get(`${BASE_URL}/critical`, {
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

  const updateCriticalTicket = async (ticketData) => {
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

const SpecialistITService = {
    
    viewCriticalTickets,
    getTicket,
    updateCriticalTicket
};

export default SpecialistITService;