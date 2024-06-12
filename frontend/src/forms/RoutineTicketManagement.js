import AvailableITService from '../services/AvailableITService'
import {  Link } from 'react-router-dom';
import { useState } from 'react';
import {  useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

const  RoutineTicketManagement= () => {

  const [tickets, setTickets] = useState([]);
  const [selectedTicket, setSelectedTicket] = useState(null);


  useEffect(() => {
    fetchTickets();
  }, []);

  const fetchTickets = () => {
    AvailableITService.viewRoutineTicket()
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
      const ticket = await AvailableITService.getTicket(ticketId);
      setSelectedTicket(ticket); // Set the selected device
    } catch (error) {
      console.error('Error fetching ticket by ID:', error);
    }
  };

  return (
    <div className="container">
      

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
                <button><Link to={`/update-routine-ticket/${ticket.ticketId}`}> Update </Link></button>
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

export default RoutineTicketManagement;