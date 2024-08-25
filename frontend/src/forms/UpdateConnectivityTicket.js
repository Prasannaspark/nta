import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
 import ConnectivityService from '../services/ConnectivityService';
function UpdateConnectivityTicket() {
  const navigate = useNavigate();
  const { ticketId } = useParams();
 
  const [ticketData, setTicketData] = useState({
   status:''
  });
 
  useEffect(() => {
    fetchTicketDataById(ticketId);
  }, [ticketId]);
 
  const fetchTicketDataById = async (ticketId) => {
    try {
       
      const response = await ConnectivityService.getTicket(ticketId);
      if (response) {
        setTicketData(response);
      } else {
        console.error('Error: Ticket data is undefined.');
      }
    } catch (error) {
      console.error('Error fetching ticket data:', error);
    }
  };
 
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setTicketData((prevTicketData) => ({
      ...prevTicketData,
      [name]: value
    }));
  };
 
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await ConnectivityService.updateTicket(ticketData);
      navigate("/connectivityhighpriority");
    } catch (error) {
      console.error('Error updating ticket:', error);
      alert(error.message || 'An error occurred while updating ticket.');
    }
  };
 
  return (
    <div className="auth-container mt-5 pt-5">
      <h2>RESOLVE TICKET</h2>
      <form onSubmit={handleSubmit}>

        <div className="mb-3">
            <label htmlFor="status" className="form-label">Status : </label>
            <select className="form-select" id="status" name="status" value={ticketData.status || ''} onChange={handleInputChange} required>
            <option value="">Update Status</option>
            <option value="CLOSE">RESOLVED</option>
          
          </select>
          </div>
          
        <button type="submit">UPDATE</button>


      </form>
    </div>
  );
}
 
export default UpdateConnectivityTicket;