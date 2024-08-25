// // import AdminService from '../services/AdminService';
// // import { useState, useEffect } from 'react';
// // import { Link } from 'react-router-dom';
// // import 'bootstrap/dist/css/bootstrap.min.css';
// // import './TicketManagement.css';

// // const TicketManagement = () => {
// //   const [tickets, setTickets] = useState([]);

// //   useEffect(() => {
// //     fetchTickets();
// //   }, []);

// //   const fetchTickets = () => {
// //     AdminService.viewTickets()
// //       .then(response => {
// //         setTickets(response);
// //         console.log(response);
// //       })
// //       .catch(error => {
// //         console.error('Error fetching tickets: ', error);
// //       });
// //   };

  
// //   return (
// //     <div className="container mt-5 pt-5">
// //       <h2>Ticket Dashboard</h2>
// //       <table className="table table-striped table-bordered">
// //         <thead>
// //           <tr>
// //             <th>Ticket ID</th>
// //             <th>Title</th>
// //             <th>Description</th>
// //             <th>Priority</th>
// //             <th>Severity</th>
// //             <th>Status</th>
// //             <th>Creation Date</th>
// //             <th>Last Updated</th>
// //             <th>Customer Tier</th>
// //             <th>Issue Type</th>
// //             <th>User Name</th>
// //             <th>Set Priority Severity and IssueType</th>
// //           </tr>
// //         </thead>
// //         <tbody>
// //           {tickets.map(ticket => (
// //             <tr key={ticket.ticketId}>
// //               <td>{ticket.ticketId}</td>
// //               <td>{ticket.title}</td>
// //               <td>{ticket.description}</td>
// //               <td>{ticket.priority}</td>
// //               <td>{ticket.severity}</td>
// //               <td>{ticket.status}</td>
// //               <td>{ticket.creationDate}</td>
// //               <td>{ticket.lastUpdated}</td>
// //               <td>{ticket.customerTier}</td>
// //               <td>{ticket.issueType}</td>
// //               <td>{ticket.user.username}</td>
// //               <td>
// //                                 <button class="btn btn-success"><Link to={`/admin-priorityseverity-ticket/${ticket.ticketId}`} style={{ textDecoration: 'none', color: 'white' }}>
// //                                         Set 
// //                                     </Link></button>
// //                                 </td>
// //             </tr>
// //           ))}
// //         </tbody>
// //       </table>
// //     </div>
// //   );
// // };

// // export default TicketManagement;

// // import AdminService from '../services/AdminService';
// // import { useState, useEffect } from 'react';
// // import { Link } from 'react-router-dom';
// // import 'bootstrap/dist/css/bootstrap.min.css';
// // import './TicketManagement.css';

// // const TicketManagement = () => {
// //   const [tickets, setTickets] = useState([]);
// //   const [searchQuery, setSearchQuery] = useState('');

// //   useEffect(() => {
// //     fetchTickets();
// //   }, []);

// //   const fetchTickets = () => {
// //     AdminService.viewTickets()
// //       .then(response => {
// //         setTickets(response);
// //         console.log(response);
// //       })
// //       .catch(error => {
// //         console.error('Error fetching tickets: ', error);
// //       });
// //   };

// //   const handleSearch = (e) => {
// //     setSearchQuery(e.target.value);
// //   };

// //   const filteredTickets = tickets.filter(ticket => 
// //     ticket.user.username.toLowerCase().includes(searchQuery.toLowerCase())
// //   );

// //   return (
// //     <div className="container mt-5 pt-5">
// //       <h2>Ticket Dashboard</h2>
// //       <div className="mb-3">
// //         <input 
// //           type="text" 
// //           className="form-control" 
// //           placeholder="Search by Username" 
// //           value={searchQuery} 
// //           onChange={handleSearch} 
// //         />
// //       </div>
// //       <table className="table table-striped table-bordered">
// //         <thead>
// //           <tr>
// //             <th>Ticket ID</th>
// //             <th>Title</th>
// //             <th>Description</th>
// //             <th>Priority</th>
// //             <th>Severity</th>
// //             <th>Status</th>
// //             <th>Creation Date</th>
// //             <th>Last Updated</th>
// //             <th>Customer Tier</th>
// //             <th>Issue Type</th>
// //             <th>User Name</th>
// //             <th>Set Priority Severity and IssueType</th>
// //           </tr>
// //         </thead>
// //         <tbody>
// //           {filteredTickets.map(ticket => (
// //             <tr key={ticket.ticketId}>
// //               <td>{ticket.ticketId}</td>
// //               <td>{ticket.title}</td>
// //               <td>{ticket.description}</td>
// //               <td>{ticket.priority}</td>
// //               <td>{ticket.severity}</td>
// //               <td>{ticket.status}</td>
// //               <td>{ticket.creationDate}</td>
// //               <td>{ticket.lastUpdated}</td>
// //               <td>{ticket.customerTier}</td>
// //               <td>{ticket.issueType}</td>
// //               <td>{ticket.user.username}</td>
// //               <td>
// //                 <button className="btn btn-success">
// //                   <Link to={`/admin-priorityseverity-ticket/${ticket.ticketId}`} style={{ textDecoration: 'none', color: 'white' }}>
// //                     Set 
// //                   </Link>
// //                 </button>
// //               </td>
// //             </tr>
// //           ))}
// //         </tbody>
// //       </table>
// //     </div>
// //   );
// // };

// // export default TicketManagement;



// // import AdminService from '../services/AdminService';
// // import { useState, useEffect } from 'react';
// // import { Link } from 'react-router-dom';
// // import 'bootstrap/dist/css/bootstrap.min.css';
// // import './TicketManagement.css';

// // const TicketManagement = () => {
// //   const [tickets, setTickets] = useState([]);
// //   const [inputQuery, setInputQuery] = useState('');
// //   const [searchQuery, setSearchQuery] = useState('');

// //   useEffect(() => {
// //     fetchTickets();
// //   }, []);

// //   const fetchTickets = () => {
// //     AdminService.viewTickets()
// //       .then(response => {
// //         setTickets(response);
// //         console.log(response);
// //       })
// //       .catch(error => {
// //         console.error('Error fetching tickets: ', error);
// //       });
// //   };

// //   const handleInputChange = (e) => {
// //     setInputQuery(e.target.value);
// //   };

// //   const handleSearchClick = () => {
// //     setSearchQuery(inputQuery);
// //   };

// //   const filteredTickets = tickets.filter(ticket => 
// //     ticket.user.username.toLowerCase().includes(searchQuery.toLowerCase())
// //   );

// //   return (
// //     <div className="container mt-5 pt-5">
// //       <h2>Ticket Dashboard</h2>
// //       <div className="mb-3 d-flex">
// //         <input 
// //           type="text" 
// //           className="form-control" 
// //           placeholder="Search by Username" 
// //           value={inputQuery} 
// //           onChange={handleInputChange} 
// //         />
// //         <button className="btn btn-primary ml-2" onClick={handleSearchClick}>
// //           <i className="fas fa-search"></i>
// //         </button>
// //       </div>
// //       <table className="table table-striped table-bordered">
// //         <thead>
// //           <tr>
// //             <th>Ticket ID</th>
// //             <th>Title</th>
// //             <th>Description</th>
// //             <th>Priority</th>
// //             <th>Severity</th>
// //             <th>Status</th>
// //             <th>Creation Date</th>
// //             <th>Last Updated</th>
// //             <th>Customer Tier</th>
// //             <th>Issue Type</th>
// //             <th>User Name</th>
// //             <th>Set Priority Severity and IssueType</th>
// //           </tr>
// //         </thead>
// //         <tbody>
// //           {filteredTickets.map(ticket => (
// //             <tr key={ticket.ticketId}>
// //               <td>{ticket.ticketId}</td>
// //               <td>{ticket.title}</td>
// //               <td>{ticket.description}</td>
// //               <td>{ticket.priority}</td>
// //               <td>{ticket.severity}</td>
// //               <td>{ticket.status}</td>
// //               <td>{ticket.creationDate}</td>
// //               <td>{ticket.lastUpdated}</td>
// //               <td>{ticket.customerTier}</td>
// //               <td>{ticket.issueType}</td>
// //               <td>{ticket.user.username}</td>
// //               <td>
// //                 <button className="btn btn-success">
// //                   <Link to={`/admin-priorityseverity-ticket/${ticket.ticketId}`} style={{ textDecoration: 'none', color: 'white' }}>
// //                     Set 
// //                   </Link>
// //                 </button>
// //               </td>
// //             </tr>
// //           ))}
// //         </tbody>
// //       </table>
// //     </div>
// //   );
// // };

// // export default TicketManagement;


// import React, { useState, useEffect } from 'react';
// import { Link } from 'react-router-dom';
// import AdminService from '../services/AdminService';
// import 'bootstrap/dist/css/bootstrap.min.css';
// import { FaSearch } from 'react-icons/fa';
// import './TicketManagement.css';

// const TicketManagement = () => {
//   const [tickets, setTickets] = useState([]);
//   const [inputQuery, setInputQuery] = useState('');
//   const [searchQuery, setSearchQuery] = useState('');

//   useEffect(() => {
//     fetchTickets();
//   }, []);

//   const fetchTickets = () => {
//     AdminService.viewTickets()
//       .then(response => {
//         setTickets(response);
//         console.log(response);
//       })
//       .catch(error => {
//         console.error('Error fetching tickets: ', error);
//       });
//   };

//   const handleInputChange = (e) => {
//     setInputQuery(e.target.value);
//   };

//   const handleSearchClick = () => {
//     setSearchQuery(inputQuery);
//   };

//   const filteredTickets = tickets.filter(ticket => 
//     ticket.user.username.toLowerCase().includes(searchQuery.toLowerCase())
//   );

//   return (
//     <div className="container mt-5 pt-5">
//       <h2>Ticket Dashboard</h2>
//       <div className="mb-3 d-flex">
//         <input 
//           type="text" 
//           className="form-control" 
//           placeholder="Search by Username" 
//           value={inputQuery} 
//           onChange={handleInputChange} 
//         />
//         <button className="btn btn-primary ml-2" onClick={handleSearchClick}>
//           <FaSearch />
//         </button>
//       </div>
//       <table className="table table-striped table-bordered">
//         <thead>
//           <tr>
//             <th>Ticket ID</th>
//             <th>Title</th>
//             <th>Description</th>
//             <th>Priority</th>
//             <th>Severity</th>
//             <th>Status</th>
//             <th>Creation Date</th>
//             <th>Last Updated</th>
//             <th>Customer Tier</th>
//             <th>Issue Type</th>
//             <th>User Name</th>
//             <th>Set Priority Severity and IssueType</th>
//           </tr>
//         </thead>
//         <tbody>
//           {filteredTickets.map(ticket => (
//             <tr key={ticket.ticketId}>
//               <td>{ticket.ticketId}</td>
//               <td>{ticket.title}</td>
//               <td>{ticket.description}</td>
//               <td>{ticket.priority}</td>
//               <td>{ticket.severity}</td>
//               <td>{ticket.status}</td>
//               <td>{ticket.creationDate}</td>
//               <td>{ticket.lastUpdated}</td>
//               <td>{ticket.customerTier}</td>
//               <td>{ticket.issueType}</td>
//               <td>{ticket.user.username}</td>
//               <td>
//                 <button className="btn btn-success">
//                   <Link to={`/admin-priorityseverity-ticket/${ticket.ticketId}`} style={{ textDecoration: 'none', color: 'white' }}>
//                     Set 
//                   </Link>
//                 </button>
//               </td>
//             </tr>
//           ))}
//         </tbody>
//       </table>
//     </div>
//   );
// };

// export default TicketManagement;


// import React, { useState, useEffect } from 'react';
// import { Link } from 'react-router-dom';
// import AdminService from '../services/AdminService';
// import 'bootstrap/dist/css/bootstrap.min.css';
// import { FaSearch } from 'react-icons/fa';
// import './TicketManagement.css';

// const TicketManagement = () => {
//   const [tickets, setTickets] = useState([]);
//   const [inputUsername, setInputUsername] = useState('');
//   const [inputStatus, setInputStatus] = useState('');
//   const [searchUsername, setSearchUsername] = useState('');
//   const [searchStatus, setSearchStatus] = useState('');

//   useEffect(() => {
//     fetchTickets();
//   }, []);

//   const fetchTickets = () => {
//     AdminService.viewTickets()
//       .then(response => {
//         setTickets(response);
//         console.log(response);
//       })
//       .catch(error => {
//         console.error('Error fetching tickets: ', error);
//       });
//   };

//   const handleUsernameChange = (e) => {
//     setInputUsername(e.target.value);
//   };

//   const handleStatusChange = (e) => {
//     setInputStatus(e.target.value);
//   };

//   const handleSearchClick = () => {
//     setSearchUsername(inputUsername);
//     setSearchStatus(inputStatus);
//   };

//   const filteredTickets = tickets.filter(ticket => 
//     (ticket.user.username.toLowerCase().includes(searchUsername.toLowerCase()) || !searchUsername) &&
//     (ticket.status.toLowerCase().includes(searchStatus.toLowerCase()) || !searchStatus)
//   );

//   return (
//     <div className="container mt-5 pt-5">
//       <h2>Ticket Dashboard</h2>
//       <div className="mb-3 d-flex">
//         <input 
//           type="text" 
//           className="form-control mr-2" 
//           placeholder="Search by Username" 
//           value={inputUsername} 
//           onChange={handleUsernameChange} 
//         />
//         <input 
//           type="text" 
//           className="form-control mr-2" 
//           placeholder="Search by Status" 
//           value={inputStatus} 
//           onChange={handleStatusChange} 
//         />
//         <button className="btn btn-primary" onClick={handleSearchClick}>
//           <FaSearch />
//         </button>
//       </div>
//       <table className="table table-striped table-bordered">
//         <thead>
//           <tr>
//             <th>Ticket ID</th>
//             <th>Title</th>
//             <th>Description</th>
//             <th>Priority</th>
//             <th>Severity</th>
//             <th>Status</th>
//             <th>Creation Date</th>
//             <th>Last Updated</th>
//             <th>Customer Tier</th>
//             <th>Issue Type</th>
//             <th>User Name</th>
//             <th>Set Priority Severity and IssueType</th>
//           </tr>
//         </thead>
//         <tbody>
//           {filteredTickets.map(ticket => (
//             <tr key={ticket.ticketId}>
//               <td>{ticket.ticketId}</td>
//               <td>{ticket.title}</td>
//               <td>{ticket.description}</td>
//               <td>{ticket.priority}</td>
//               <td>{ticket.severity}</td>
//               <td>{ticket.status}</td>
//               <td>{ticket.creationDate}</td>
//               <td>{ticket.lastUpdated}</td>
//               <td>{ticket.customerTier}</td>
//               <td>{ticket.issueType}</td>
//               <td>{ticket.user.username}</td>
//               <td>
//                 <button className="btn btn-success">
//                   <Link to={`/admin-priorityseverity-ticket/${ticket.ticketId}`} style={{ textDecoration: 'none', color: 'white' }}>
//                     Set 
//                   </Link>
//                 </button>
//               </td>
//             </tr>
//           ))}
//         </tbody>
//       </table>
//     </div>
//   );
// };

// export default TicketManagement;


import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import AdminService from '../services/AdminService';
import 'bootstrap/dist/css/bootstrap.min.css';
import { FaSearch } from 'react-icons/fa';
import './TicketManagement.css';

const TicketManagement = () => {
  const [tickets, setTickets] = useState([]);
  const [inputUsername, setInputUsername] = useState('');
  const [inputStatus, setInputStatus] = useState('');
  const [searchUsername, setSearchUsername] = useState('');
  const [searchStatus, setSearchStatus] = useState('');

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

  const handleUsernameChange = (e) => {
    setInputUsername(e.target.value);
  };

  const handleStatusChange = (e) => {
    setInputStatus(e.target.value);
  };

  const handleSearchClick = () => {
    setSearchUsername(inputUsername);
    setSearchStatus(inputStatus);
  };

  const filteredTickets = tickets.filter(ticket => 
    (ticket.user.username.toLowerCase().includes(searchUsername.toLowerCase()) || !searchUsername) &&
    (ticket.status.toLowerCase().includes(searchStatus.toLowerCase()) || !searchStatus)
  );

  return (
    <div className="container mt-5 pt-5">
      <h2>Ticket Dashboard</h2>
      <div className="mb-3 d-flex">
        <input 
          type="text" 
          className="form-control mr-2" 
          placeholder="Search by Username" 
          value={inputUsername} 
          onChange={handleUsernameChange} 
        />
        <input 
          type="text" 
          className="form-control mr-2" 
          placeholder="Search by Status" 
          value={inputStatus} 
          onChange={handleStatusChange} 
        />
        <button className="btn btn-primary" onClick={handleSearchClick}>
          <FaSearch />
        </button>
      </div>
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
            <th>User Name</th>
            <th>Set Priority Severity and IssueType</th>
          </tr>
        </thead>
        <tbody>
          {filteredTickets.map(ticket => (
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
              <td>{ticket.user.username}</td>
              <td>
                <button className="btn btn-success">
                  <Link to={`/admin-priorityseverity-ticket/${ticket.ticketId}`} style={{ textDecoration: 'none', color: 'white' }}>
                    Set 
                  </Link>
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default TicketManagement;



