import CustomerService from '../services/CustomerService';
import { useNavigate, Link } from 'react-router-dom';
import { useState } from 'react';
import { useRef, useEffect } from 'react';
import AuthService from '../services/auth.service';
import 'bootstrap/dist/css/bootstrap.min.css';

const CustomerTicketManagement = () => {
  const [newTicket, setNewTicket] = useState({
    title: '',
    description: '',
    priority: '',
    severity: '',
    customerTier: '',
    issueType: '',
    userEntityId: AuthService.getCurrentUser().id
  });
  const [tickets, setTickets] = useState([]);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState('');
  const [selectedTicket, setSelectedTicket] = useState(null);

  const form = useRef();
  const navigate = useNavigate();

  useEffect(() => {
    fetchTickets();
  }, []);

  const fetchTickets = () => {
    const id = AuthService.getCurrentUser().id;
    CustomerService.viewTickets(id)
      .then(response => {
        setTickets(response);
        console.log(response);
      })
      .catch(error => {
        console.error('Error fetching customers:', error);
      });
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewTicket({ ...newTicket, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage('');
    CustomerService.addTicket(newTicket)
      .then(() => {
        alert('Ticket added successfully');
        setNewTicket({
          title: '',
          description: '',
          priority: '',
          severity: '',
          customerTier: '',
          issueType: '',
          userEntityId : ''
        });
        fetchTickets();
      })
      .catch(error => {
        console.error('Error adding ticket:', error);
        alert('An error occurred while adding ticket');
      })
      .finally(() => {
        setLoading(false);
      });
  };

  const fetchTicket = async (ticketId) => {
    try {
      const ticket = await CustomerService.getTicket(ticketId);
      setSelectedTicket(ticket); // Set the selected device
    } catch (error) {
      console.error('Error fetching ticket by ID:', error);
    }
  };

  return (
    <div className="container">
      <h2> Add Ticket</h2>
      <div className="card card-container">
        <form onSubmit={handleSubmit} ref={form}>
          <div className="mb-3">
            <label htmlFor="title" className="form-label">Title : </label>
            <input type="text" className="form-control" id="title" name="title" value={newTicket.title} onChange={handleInputChange} required />
          </div>
          <div className="mb-3">
            <label htmlFor="description" className="form-label">Description : </label>
            <input type="text" className="form-control" id="description" name="description" value={newTicket.description} onChange={handleInputChange} required />
          </div>
          <div className="mb-3">
            <label htmlFor="priority" className="form-label">Priority :</label>
            <input type="text" className="form-control" id="priority" name="priority" value={newTicket.priority} onChange={handleInputChange} required />
          </div>
          <div className="mb-3">
            <label htmlFor="severity" className="form-label">Severity :</label>
            <input type="text" className="form-control" id="severity" name="severity" value={newTicket.severity} onChange={handleInputChange} required />
          </div>
          <div className="mb-3">
            <label htmlFor="customerTier" className="form-label">Customer Tier :</label>
            <input type="text" className="form-control" id="customerTier" name="customerTier" value={newTicket.customerTier} onChange={handleInputChange} required />
          </div>
          <div className="mb-3">
            <label htmlFor="issueType" className="form-label">Issue Type :</label>
            <input type="text" className="form-control" id="issueType" name="issueType" value={newTicket.issueType} onChange={handleInputChange} required />
          </div>

          <button type="submit" className="btn btn-primary" disabled={loading}>Add Ticket</button>
        </form>
      </div>

      <table className="table mt-4">
        <thead>
          <tr>
            <th>Ticket ID</th>
            <th>Title</th>
            <th>Description</th>
            <th>Priority</th>
            <th>Severity</th>
            <th>customer Tier</th>
            <th>Issue Type </th>
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
                <button className="btn btn-primary" onClick={() => fetchTicket(ticket.ticketId)}>View</button>
                <button><Link to={`/update-customer-ticket/${ticket.ticketId}`}> Update </Link></button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {selectedTicket && (
        <div>
          <h3>Ticket Details :</h3>
          <p>Title : {selectedTicket.title}</p>
          <p>Description : {selectedTicket.description}</p>
          <p>Priority : {selectedTicket.priority}</p>
          <p>Severity : {selectedTicket.severity}</p>
          <p>CustomerTier : {selectedTicket.customerTier}</p>
          <p>IssueType : {selectedTicket.issueType}</p>
          <p>Status : {selectedTicket.status}</p>
          <p>Creation Date :{selectedTicket.creationDate}</p>
          <p>Last Updated:{selectedTicket.lastUpdated}</p>

        </div>
      )}
    </div>
  );
};

export default CustomerTicketManagement;