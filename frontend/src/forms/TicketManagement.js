import AdminService from '../services/AdminService';
import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './TicketManagement.css';

const TicketManagement = () => {
  const [tickets, setTickets] = useState([]);

  useEffect(() => {
    fetchTickets();
  }, []);

  const fetchTickets = () => {
    AdminService.viewTickets()
      .then(response => {
        setTickets(response);
        console.log(response);
      })
      .catch(error => {
        console.error('Error fetching tickets: ', error);
      });
  };

  
  return (
    <div className="container mt-5 pt-5">
      <h2>Ticket Dashboard</h2>
      <table className="table table-striped table-bordered">
        <thead>
          <tr>
            <th>Ticket ID</th>
            <th>Title</th>
            <th>Description</th>
            <th>Priority</th>
            <th>Severity</th>
            <th>Status</th>
            <th>Creation Date</th>
            <th>Last Updated</th>
            <th>Customer Tier</th>
            <th>Issue Type</th>
            <th>User Id</th>
            <th>Set Priority and Severity</th>
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
              <td>{ticket.status}</td>
              <td>{ticket.creationDate}</td>
              <td>{ticket.lastUpdated}</td>
              <td>{ticket.customerTier}</td>
              <td>{ticket.issueType}</td>
              <td>{ticket.user.id}</td>
              <td>
                                <button class="btn btn-success"><Link to={`/admin-priorityseverity-ticket/${ticket.ticketId}`} style={{ textDecoration: 'none', color: 'white' }}>
                                        Set 
                                    </Link></button>
                                </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default TicketManagement;
