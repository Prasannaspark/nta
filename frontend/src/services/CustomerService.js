import axios from "axios";
import authHeader from "../services/auth-header";
 
const BASE_URL = "http://localhost:9090/api/customer";
 
const addTicket = async (ticketData) => {
  try {
    const response = await axios.post(`${BASE_URL}/addticket`, ticketData, {
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

const updateCustomerTicket = async (ticketData) => {
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



const viewTickets = async (id) => {
  try {
    const response = await axios.post(`${BASE_URL}/getmytickets`, id, {
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

const RequestTier = async (userData) => {
  try {
    const response = await axios.post(`${BASE_URL}/updaterequest`,userData, {
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


const CustomerService = {
  addTicket,
  getTicket,
  updateCustomerTicket,
  viewTickets,
  RequestTier
};

export default CustomerService;