import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import CustomerService from '../services/CustomerService';
import './CustomerTicketManagement.css';
function UpdateCustomerTicket() {
  const navigate = useNavigate();
  const { ticketId } = useParams();

  const [ticketData, setTicketData] = useState({
    title: '',
    description: ''
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
    <div className="auth-container mt-5">
      <h2>UPDATE TICKET</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label htmlFor="title" className="form-label">Title :</label>
          <input type="text" className="form-control" id="title" name="title" value={ticketData.title || ''} onChange={handleInputChange} required />
        </div>
        <div className="mb-3">
          <label htmlFor="description" className="form-label">Description :</label>
          <input type="text" className="form-control" id="description" name="description" value={ticketData.description || ''} onChange={handleInputChange} required />
        </div>
        <button type="submit" className="btn btn-default">UPDATE</button>
        <div className="card-footer text-center">
          <button className="btn btn-default" onClick={() => navigate("/customerticketmanagement")}>Back to customer ticket management</button>
        </div>
      </form>
    </div>
  );
}

export default UpdateCustomerTicket;
