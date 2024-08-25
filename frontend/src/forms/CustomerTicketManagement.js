// import React, { useState, useRef, useEffect } from 'react';
// import { useNavigate, Link } from 'react-router-dom';
// import CustomerService from '../services/CustomerService';
// import AuthService from '../services/auth.service';
// import 'bootstrap/dist/css/bootstrap.min.css';
// import './CustomerTicketManagement.css';
// const CustomerTicketManagement = () => {
//   const [newTicket, setNewTicket] = useState({
//     title: '',
//     description: '',
//     customerTier: AuthService.getCurrentUser().tier,
//     username: AuthService.getCurrentUser().username
//   });
//   const [tickets, setTickets] = useState([]);
//   const [loading, setLoading] = useState(false);
//   const [message, setMessage] = useState('');
//   const [selectedTicket, setSelectedTicket] = useState(null);
//   const [displayTicketList, setDisplayTicketList] = useState(true); // State to toggle between displaying ticket list and adding new ticket
//   const [reqTierPage, setreqTierPage] = useState(null);

//   const form = useRef();
//   const navigate = useNavigate();

//   useEffect(() => {
//     if (displayTicketList) {
//       fetchTickets();
//     }
//   }, [displayTicketList]);

//   const fetchTickets = () => {
//     const id = AuthService.getCurrentUser().username;
//     CustomerService.viewTickets(id)
//       .then(response => {
//         setTickets(response);
//         console.log(response);
//       })
//       .catch(error => {
//         console.error('Error fetching customers:', error);
//       });
//   };

//   const handleInputChange = (e) => {
//     const { name, value } = e.target;
//     setNewTicket({ ...newTicket, [name]: value });
//   };

//   const handleSubmit = (e) => {
//     e.preventDefault();
//     setLoading(true);
//     setMessage('');
//     CustomerService.addTicket(newTicket)
//       .then(() => {
//         alert('Ticket added successfully');
//         setNewTicket({
//           title: '',
//           description: '',
//           customerTier: '',
//           issueType: '',
//           username: ''
//         });
//         fetchTickets();
//         setDisplayTicketList(true); 
//       })
//       .catch(error => {
//         console.error('Error adding ticket:', error);
//         alert('An error occurred while adding ticket');
//       })
//       .finally(() => {
//         setLoading(false);
//       });
//   };

//   const fetchTicket = async (ticketId) => {
//     try {
//       const ticket = await CustomerService.getTicket(ticketId);
//       setSelectedTicket(ticket); // Set the selected ticket
//     } catch (error) {
//       console.error('Error fetching ticket by ID:', error);
//     }
//   };

//   return (
//     <div className="container mt-5 pt-3">
//       <h2 className='pb-3'>NetOpsHelpDesk's CUSTOMER</h2>
//       <div className="mb-3">
//         <button className="btn btn-success me-2" onClick={() => setDisplayTicketList(false)}>ADD TICKET</button>
//       </div>
//       <div className="mb-3">
//         <button className="btn btn-warning" onClick={() => setDisplayTicketList(true)}>VIEW TICKET LIST</button>
//       </div>

//       <div className="mb-3">
//         <button className="btn btn-warning" onClick={() => setDisplayTicketList(false)}>Request Tier Modification</button>
//       </div>
//       {displayTicketList ? (
//         <table className="table table-striped table-bordered">
//           <thead>
//             <tr>
//               <th>Ticket ID</th>
//               <th>Title</th>
//               <th>Description</th>
//               <th>Customer Tier</th>
//               <th>Issue Type</th>
//               <th>Status</th>
//               <th>Creation Date</th>
//               <th>Last Updated</th>
//               <th>Actions</th>
//             </tr>
//           </thead>
//           <tbody>
//             {tickets.map(ticket => (
//               <tr key={ticket.ticketId}>
//                 <td>{ticket.ticketId}</td>
//                 <td>{ticket.title}</td>
//                 <td>{ticket.description}</td>
//                 <td>{ticket.customerTier}</td>
//                 <td>{ticket.issueType}</td>
//                 <td>{ticket.status}</td>
//                 <td>{ticket.creationDate}</td>
//                 <td>{ticket.lastUpdated}</td>
//                 <td>
//                   <button className="btn btn-default btn-sm mx-1" onClick={() => fetchTicket(ticket.ticketId)}>View</button>
//                  <Link to={`/update-customer-ticket/${ticket.ticketId}`} className="btn btn-default btn-sm mx-1">Update</Link>
//                 </td>
//               </tr>
//             ))}
//           </tbody>
//         </table>
//       ) : (
//         <div className="card card-container">
//           <h3>Create Ticket</h3>
//           <form onSubmit={handleSubmit} ref={form}>
//             <div className="mb-3">
//               <label htmlFor="title" className="form-label">Title :</label>
//               <input type="text" className="form-control" id="title" name="title" value={newTicket.title} onChange={handleInputChange} required />
//             </div>
//             <div className="mb-3">
//               <label htmlFor="description" className="form-label">Description :</label>
//               <input type="text" className="form-control" id="description" name="description" value={newTicket.description} onChange={handleInputChange} required />
//             </div>
            
//             <button type="submit" className="btn btn-primary" disabled={loading}>ADD TICKET</button>
//           </form>
//         </div>
//       )}
//    {selectedTicket && (
//   <div>
//     <h3>Ticket Details :</h3>
//     <table className="table table-striped table-bordered">
//       <tbody>
//         <tr>
//           <th>Title</th>
//           <td>{selectedTicket.title}</td>
//         </tr>
//         <tr>
//           <th>Description</th>
//           <td>{selectedTicket.description}</td>
//         </tr>
//         <tr>
//           <th>Priority</th>
//           <td>{selectedTicket.priority}</td>
//         </tr>
//         <tr>
//           <th>Severity</th>
//           <td>{selectedTicket.severity}</td>
//         </tr>
//         <tr>
//           <th>Customer Tier</th>
//           <td>{selectedTicket.customerTier}</td>
//         </tr>
//         <tr>
//           <th>Issue Type</th>
//           <td>{selectedTicket.issueType}</td>
//         </tr>
//         <tr>
//           <th>Status</th>
//           <td>{selectedTicket.status}</td>
//         </tr>
//         <tr>
//           <th>Creation Date</th>
//           <td>{selectedTicket.creationDate}</td>
//         </tr>
//         <tr>
//           <th>Last Updated</th>
//           <td>{selectedTicket.lastUpdated}</td>
//         </tr>
//       </tbody>
//     </table>
//   </div>
// )}
//     </div>
//   );
// };

// export default CustomerTicketManagement;

// import React, { useState, useRef, useEffect } from 'react';
// import { useNavigate, Link } from 'react-router-dom';
// import CustomerService from '../services/CustomerService';
// import AuthService from '../services/auth.service';
// import 'bootstrap/dist/css/bootstrap.min.css';
// import './CustomerTicketManagement.css';

// const CustomerTicketManagement = () => {
//   const [newTicket, setNewTicket] = useState({
//     title: '',
//     description: '',
//     customerTier: AuthService.getCurrentUser().tier,
//     username: AuthService.getCurrentUser().username
//   });
//   const [tickets, setTickets] = useState([]);
//   const [loading, setLoading] = useState(false);
//   const [message, setMessage] = useState('');
//   const [selectedTicket, setSelectedTicket] = useState(null);
//   const [displayTicketList, setDisplayTicketList] = useState(true);

//   const form = useRef();
//   const navigate = useNavigate();

//   useEffect(() => {
//     if (displayTicketList) {
//       fetchTickets();
//     }
//   }, [displayTicketList]);

//   const fetchTickets = () => {
//     const id = AuthService.getCurrentUser().username;
//     CustomerService.viewTickets(id)
//       .then(response => {
//         setTickets(response);
//         console.log(response);
//       })
//       .catch(error => {
//         console.error('Error fetching customers:', error);
//       });
//   };

//   const handleInputChange = (e) => {
//     const { name, value } = e.target;
//     setNewTicket({ ...newTicket, [name]: value });
//   };

//   const handleSubmit = (e) => {
//     e.preventDefault();
//     setLoading(true);
//     setMessage('');
//     CustomerService.addTicket(newTicket)
//       .then(() => {
//         alert('Ticket added successfully');
//         setNewTicket({
//           title: '',
//           description: '',
//           customerTier: '',
//           issueType: '',
//           username: ''
//         });
//         fetchTickets();
//         setDisplayTicketList(true);
//       })
//       .catch(error => {
//         console.error('Error adding ticket:', error);
//         alert('An error occurred while adding ticket');
//       })
//       .finally(() => {
//         setLoading(false);
//       });
//   };

//   const fetchTicket = async (ticketId) => {
//     try {
//       const ticket = await CustomerService.getTicket(ticketId);
//       setSelectedTicket(ticket);
//     } catch (error) {
//       console.error('Error fetching ticket by ID:', error);
//     }
//   };

//   return (
//     <div className="container mt-5 pt-3">
//       <h2 className='pb-3'>NetOpsHelpDesk's CUSTOMER</h2>

//       <div className="row mb-3">
//                 <div className="col-4">
//                     <button className="btn btn-success w-100 btn-same-height" onClick={() => setDisplayTicketList(false)}>ADD TICKET</button>
//                 </div>
//                 <div className="col-4">
//                     <button className="btn btn-success w-100 btn-same-height" onClick={() => setDisplayTicketList(true)}>VIEW TICKET LIST</button>
//                 </div>
//                 <div className="col-4">
//                     <button className="btn btn-success w-100 btn-same-height" onClick={() => navigate("/requestforTier")}>REQUEST TIER MODIFICATION</button>
//                 </div>
//             </div>
//       {displayTicketList ? (
//         <table className="table table-striped table-bordered">
//           <thead>
//             <tr>
//               <th>Ticket ID</th>
//               <th>Title</th>
//               <th>Description</th>
//               <th>Customer Tier</th>
//               <th>Issue Type</th>
//               <th>Status</th>
//               <th>Creation Date</th>
//               <th>Last Updated</th>
//               <th>Actions</th>
//             </tr>
//           </thead>
//           <tbody>
//             {tickets.map(ticket => (
//               <tr key={ticket.ticketId}>
//                 <td>{ticket.ticketId}</td>
//                 <td>{ticket.title}</td>
//                 <td>{ticket.description}</td>
//                 <td>{ticket.customerTier}</td>
//                 <td>{ticket.issueType}</td>
//                 <td>{ticket.status}</td>
//                 <td>{ticket.creationDate}</td>
//                 <td>{ticket.lastUpdated}</td>
//                 <td>
//                   <button className="btn btn-default btn-sm mx-1" onClick={() => fetchTicket(ticket.ticketId)}>View</button>
//                  <Link to={`/update-customer-ticket/${ticket.ticketId}`} className="btn btn-default btn-sm mx-1">Update</Link>
//                 </td>
//               </tr>
//             ))}
//           </tbody>
//         </table>
//       ) : (
//         <div className="card card-container">
//           <h3>Create Ticket</h3>
//           <form onSubmit={handleSubmit} ref={form}>
//             <div className="mb-3">
//               <label htmlFor="title" className="form-label">Title :</label>
//               <input type="text" className="form-control" id="title" name="title" value={newTicket.title} onChange={handleInputChange} required />
//             </div>
//             <div className="mb-3">
//               <label htmlFor="description" className="form-label">Description :</label>
//               <input type="text" className="form-control" id="description" name="description" value={newTicket.description} onChange={handleInputChange} required />
//             </div>
//             <button type="submit" className="btn btn-primary" disabled={loading}>ADD TICKET</button>
//           </form>
//         </div>
//       )}
//    {selectedTicket && (
//   <div>
//     <h3>Ticket Details :</h3>
//     <table className="table table-striped table-bordered">
//       <tbody>
//         <tr>
//           <th>Title</th>
//           <td>{selectedTicket.title}</td>
//         </tr>
//         <tr>
//           <th>Description</th>
//           <td>{selectedTicket.description}</td>
//         </tr>
//         <tr>
//           <th>Priority</th>
//           <td>{selectedTicket.priority}</td>
//         </tr>
//         <tr>
//           <th>Severity</th>
//           <td>{selectedTicket.severity}</td>
//         </tr>
//         <tr>
//           <th>Customer Tier</th>
//           <td>{selectedTicket.customerTier}</td>
//         </tr>
//         <tr>
//           <th>Issue Type</th>
//           <td>{selectedTicket.issueType}</td>
//         </tr>
//         <tr>
//           <th>Status</th>
//           <td>{selectedTicket.status}</td>
//         </tr>
//         <tr>
//           <th>Creation Date</th>
//           <td>{selectedTicket.creationDate}</td>
//         </tr>
//         <tr>
//           <th>Last Updated</th>
//           <td>{selectedTicket.lastUpdated}</td>
//         </tr>
//       </tbody>
//     </table>
//   </div>
// )}
//     </div>
//   );
// };

// export default CustomerTicketManagement;


import React, { useState, useRef, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import CustomerService from '../services/CustomerService';
import AuthService from '../services/auth.service';
import 'bootstrap/dist/css/bootstrap.min.css';
import './CustomerTicketManagement.css';

const CustomerTicketManagement = () => {
  const [newTicket, setNewTicket] = useState({
    title: '',
    description: '',
    customerTier: AuthService.getCurrentUser().tier,
    username: AuthService.getCurrentUser().username
  });
  const [tickets, setTickets] = useState([]);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState('');
  const [selectedTicket, setSelectedTicket] = useState(null);
  const [displayTicketList, setDisplayTicketList] = useState(true);

  const form = useRef();
  const navigate = useNavigate();

  useEffect(() => {
    if (displayTicketList) {
      fetchTickets();
    }
  }, [displayTicketList]);

  const fetchTickets = () => {
    const id = AuthService.getCurrentUser().username;
    setLoading(true);
    CustomerService.viewTickets(id)
      .then(response => {
        setTickets(response);
        setMessage('');
      })
      .catch(error => {
        console.error('Error fetching customers:', error);
        setMessage('Error fetching tickets.');
      })
      .finally(() => {
        setLoading(false);
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
          customerTier: '',
          issueType: '',
          username: ''
        });
        fetchTickets();
        setDisplayTicketList(true);
      })
      .catch(error => {
        console.error('Error adding ticket:', error);
        setMessage('An error occurred while adding ticket.');
      })
      .finally(() => {
        setLoading(false);
      });
  };

  const fetchTicket = async (ticketId) => {
    try {
      const ticket = await CustomerService.getTicket(ticketId);
      setSelectedTicket(ticket);
    } catch (error) {
      console.error('Error fetching ticket by ID:', error);
      setMessage('Error fetching ticket details.');
    }
  };


  return (
    <div className="container mt-5 pt-3">
      <h2 className='pb-3'>NetOpsHelpDesk's CUSTOMER</h2>
      
      <div className="mb-3">
      
        <div className="row mb-3">
          <div className="col-4">
            <button className="btn btn-success w-100 btn-same-height" onClick={() => setDisplayTicketList(false)}>ADD TICKET</button>
          </div>
          <div className="col-4">
            <button className="btn btn-success w-100 btn-same-height" onClick={() => setDisplayTicketList(true)}>VIEW TICKET LIST</button>
          </div>
          <div className="col-4">
            <button className="btn btn-success w-100 btn-same-height" onClick={() => navigate("/requestforTier")}>REQUEST TIER MODIFICATION</button>
          </div>
        </div>
      </div>

      {loading && <div className="spinner-border text-primary" role="status">
        <span className="visually-hidden">Loading...</span>
      </div>}

     

      {displayTicketList ? (
        <table className="table table-striped table-bordered">
          <thead>
            <tr>
              <th>Ticket ID</th>
              <th>Title</th>
              <th>Description</th>
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
                <td>{ticket.customerTier}</td>
                <td>{ticket.issueType}</td>
                <td>{ticket.status}</td>
                <td>{ticket.creationDate}</td>
                <td>{ticket.lastUpdated}</td>
                <td>
                  <button className="btn btn-default btn-sm mx-1" onClick={() => fetchTicket(ticket.ticketId)}>View</button>
                  <Link to={`/update-customer-ticket/${ticket.ticketId}`} className="btn btn-default btn-sm mx-1">Update</Link>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <div className="card card-container">
          <h3>Create Ticket</h3>
          <form onSubmit={handleSubmit} ref={form}>
            <div className="mb-3">
              <label htmlFor="title" className="form-label">Title :</label>
              <input type="text" className="form-control" id="title" name="title" value={newTicket.title} onChange={handleInputChange} required />
            </div>
            <div className="mb-3">
              <label htmlFor="description" className="form-label">Description :</label>
              <input type="text" className="form-control" id="description" name="description" value={newTicket.description} onChange={handleInputChange} required />
            </div>
            <button type="submit" className="btn btn-primary" disabled={loading}>ADD TICKET</button>
          </form>
        </div>
      )}

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
      )}
    </div>
  );
};

export default CustomerTicketManagement;


