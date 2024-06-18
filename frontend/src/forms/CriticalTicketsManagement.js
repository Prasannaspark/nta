import SpecialistITService from '../services/SpecialistITService';
import { Link } from 'react-router-dom';
import { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

const CriticalTicketManagement = () => {
  const [tickets, setTickets] = useState([]);
  const [selectedTicket, setSelectedTicket] = useState(null);

  useEffect(() => {
    fetchTickets();
  }, []);

  const fetchTickets = () => {
    SpecialistITService.viewCriticalTickets()
      .then(response => {
        setTickets(response);
        console.log(response);
      })
      .catch(error => {
        console.error('Error fetching customers:', error);
      });
  };

  const fetchTicket = async (ticketId) => {
    try {
      const ticket = await SpecialistITService.getTicket(ticketId);
      setSelectedTicket(ticket); // Set the selected device
    } catch (error) {
      console.error('Error fetching ticket by ID:', error);
    }
  };

  return (
    <div className="container mt-5 pt-3">
      <table className="table table-striped table-bordered">
        <thead>
          <tr>
            <th>Ticket ID</th>
            <th>Title</th>
            <th>Description</th>
            <th>Priority</th>
            <th>Severity</th>
            <th>Customer Tier</th>
            <th>Issue Type</th>
            <th>Status</th>
            <th>Creation Date</th>
            <th>Last Updated</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {tickets.map(ticket => (
            <tr key={ticket.ticketId}>
              <td>{ticket.ticketId}</td>
              <td>{ticket.title}</td>
              <td>{ticket.description}</td>
              <td>{ticket.priority}</td>
              <td>{ticket.severity}</td>
              <td>{ticket.customerTier}</td>
              <td>{ticket.issueType}</td>
              <td>{ticket.status}</td>
              <td>{ticket.creationDate}</td>
              <td>{ticket.lastUpdated}</td>
              <td>
                <button className="btn btn-primary me-2" onClick={() => fetchTicket(ticket.ticketId)}>View</button>
                <Link to={`/update-critical-ticket/${ticket.ticketId}`} className="btn btn-secondary">Update</Link>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      
      {selectedTicket && (
  <div>
    <h3>Ticket Details :</h3>
    <table className="table table-striped table-bordered">
      <tbody>
        <tr>
          <th>Title</th>
          <td>{selectedTicket.title}</td>
        </tr>
        <tr>
          <th>Description</th>
          <td>{selectedTicket.description}</td>
        </tr>
        <tr>
          <th>Priority</th>
          <td>{selectedTicket.priority}</td>
        </tr>
        <tr>
          <th>Severity</th>
          <td>{selectedTicket.severity}</td>
        </tr>
        <tr>
          <th>Customer Tier</th>
          <td>{selectedTicket.customerTier}</td>
        </tr>
        <tr>
          <th>Issue Type</th>
          <td>{selectedTicket.issueType}</td>
        </tr>
        <tr>
          <th>Status</th>
          <td>{selectedTicket.status}</td>
        </tr>
        <tr>
          <th>Creation Date</th>
          <td>{selectedTicket.creationDate}</td>
        </tr>
        <tr>
          <th>Last Updated</th>
          <td>{selectedTicket.lastUpdated}</td>
        </tr>
      </tbody>
    </table>
  </div>
)};
    </div>
  );
};

export default CriticalTicketManagement;
