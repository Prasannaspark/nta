// import React, { useState, useEffect } from 'react';
// import { useParams, useNavigate } from 'react-router-dom';
// import SpecialistITService from '../services/SpecialistITService';
 
// function UpdateCriticalTicket() {
//   const navigate = useNavigate();
//   const { ticketId } = useParams();
 
//   const [ticketData, setTicketData] = useState({
//    status:''
//   });
 
//   useEffect(() => {
//     fetchTicketDataById(ticketId);
//   }, [ticketId]);
 
//   const fetchTicketDataById = async (ticketId) => {
//     try {
       
//       const response = await SpecialistITService.getTicket(ticketId);
//       if (response) {
//         setTicketData(response);
//       } else {
//         console.error('Error: Ticket data is undefined.');
//       }
//     } catch (error) {
//       console.error('Error fetching ticket data:', error);
//     }
//   };
 
//   const handleInputChange = (e) => {
//     const { name, value } = e.target;
//     setTicketData((prevTicketData) => ({
//       ...prevTicketData,
//       [name]: value
//     }));
//   };
 
//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     try {
//       await SpecialistITService.updateCriticalTicket(ticketData);
//       navigate("/criticalticketmanagement");
//     } catch (error) {
//       console.error('Error updating ticket:', error);
//       alert(error.message || 'An error occurred while updating ticket.');
//     }
//   };
 
//   return (
//     <div className="auth-container">
//       <h2>Update Ticket</h2>
//       <form onSubmit={handleSubmit}>

//       <div className="mb-3">
//   <label htmlFor="status" className="form-label">Status:</label>
//   <select
//     className="form-select"
//     id="status"
//     name="status"
//     value={ticketData.status || ''}
//     onChange={handleInputChange}
//     required
//   >
//     <option value="">Select Status</option>
//     <option value="CLOSE">CLOSE</option>
//   </select>
// </div>

          
//         <button type="submit">Ticket Resolved </button>


//       </form>
//     </div>
//   );
// }
 
// export default UpdateCriticalTicket;


import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import SpecialistITService from '../services/SpecialistITService';

function UpdateCriticalTicket() {
  const navigate = useNavigate();
  const { ticketId } = useParams();

  const [ticketData, setTicketData] = useState({
    status: ''
  });

  useEffect(() => {
    fetchTicketDataById(ticketId);
  }, [ticketId]);

  const fetchTicketDataById = async (ticketId) => {
    try {
      const response = await SpecialistITService.getTicket(ticketId);
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
      await SpecialistITService.updateCriticalTicket(ticketData);
      navigate("/criticalticketmanagement");
    } catch (error) {
      console.error('Error updating ticket:', error);
      alert(error.message || 'An error occurred while updating ticket.');
    }
  };

  return (
    <div className="auth-container">
      <h2>UPDATE TICKET</h2>
      <form onSubmit={handleSubmit}>

        <div className="mb-3">
          <label htmlFor="status" className="form-label">Status:</label>
          <select
            className="form-select"
            id="status"
            name="status"
            value={ticketData.status || ''}
            onChange={handleInputChange}
            required
          >
            <option value="">Select Status</option>
            <option value="CLOSE">CLOSE</option>
          </select>
        </div>

        <button type="submit" className="btn btn-primary">Ticket Resolved</button>

      </form>
    </div>
  );
}

export default UpdateCriticalTicket;
