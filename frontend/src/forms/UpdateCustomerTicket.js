import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import CustomerService from '../services/CustomerService';
 
function UpdateCustomerTicket() {
  const navigate = useNavigate();
  const { ticketId } = useParams();
 
  const [ticketData, setTicketData] = useState({
    title: '',
    description: '',
    priority: '',
    severity: '',
    customerTier: '',
    issueType: ''
  });
 
  useEffect(() => {
    fetchTicketDataById(ticketId);
  }, [ticketId]);
 
  const fetchTicketDataById = async (ticketId) => {
    try {
       
      const response = await CustomerService.getTicket(ticketId);
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
      await CustomerService.updateCustomerTicket(ticketData);
      navigate("/customerticketmanagement");
    } catch (error) {
      console.error('Error updating ticket:', error);
      alert(error.message || 'An error occurred while updating ticket.');
    }
  };
 
  return (
    <div className="auth-container">
      <h2>Update Ticket</h2>
      <form onSubmit={handleSubmit}>

        <div className="mb-3">
            <label htmlFor="title" className="form-label">Title : </label>
            <input type="text" className="form-control" id="title" name="title" value={ticketData.title || ''} onChange={handleInputChange} required />
          </div>
          <div className="mb-3">
            <label htmlFor="description" className="form-label">Description : </label>
            <input type="text" className="form-control" id="description" name="description" value={ticketData.description || ''} onChange={handleInputChange} required />
          </div>
          <div className="mb-3">
            <label htmlFor="priority" className="form-label">Priority :</label>
            <input type="text" className="form-control" id="priority" name="priority" value={ticketData.priority || ''} onChange={handleInputChange} required />
          </div>
          <div className="mb-3">
            <label htmlFor="severity" className="form-label">Severity :</label>
            <input type="text" className="form-control" id="severity" name="severity" value={ticketData.severity || ''} onChange={handleInputChange} required />
          </div>
          <div className="mb-3">
            <label htmlFor="customerTier" className="form-label">Customer Tier :</label>
            <input type="text" className="form-control" id="customerTier" name="customerTier" value={ticketData.customerTier || ''} onChange={handleInputChange} required />
          </div>
          <div className="mb-3">
            <label htmlFor="issueType" className="form-label">Issue Type :</label>
            <input type="text" className="form-control" id="issueType" name="issueType" value={ticketData.issueType || ''} onChange={handleInputChange} required />
          </div>
        <button type="submit">Update</button>


      </form>
    </div>
  );
}
 
export default UpdateCustomerTicket;