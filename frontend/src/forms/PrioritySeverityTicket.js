import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import AdminService from '../services/AdminService';

function PrioritySeverityTicket() {
    const navigate = useNavigate();
    const { ticketId } = useParams();
  
    const [ticketData, setTicketData] = useState({
        priority:'',
        severity:'',
        issuetype:'',
        status:'INPROGRESS',
    });

    const [submittedMessage, setSubmittedMessage] = useState('');

    useEffect(() => {
        fetchTicketDataById(ticketId);
    }, [ticketId]);

    const fetchTicketDataById = async (ticketId) => {
        try {
            const response = await AdminService.getTicket(ticketId);
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
            await AdminService.priorityseverityset(ticketData);
            setSubmittedMessage('Submitted successfully'); // Set message upon successful submission
        } catch (error) {
            console.error('Error updating ticket:', error);
            alert(error.message || 'An error occurred while updating ticket.');
        }
    };


    const handleNavigateToTicketManagement = () => {
      navigate('/ticketmanagement'); // Replace '/ticketmanagement' with your actual route
  };
    
    return (
        <div className="auth-container mt-5">
            <h2>SET PRIORITY SEVERITY AND ISSUE TYPE</h2>

            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label htmlFor="priority" className="form-label">Priority :</label>
                    <select className="form-select" id="priority" name="priority" value={ticketData.priority || ''} onChange={handleInputChange} required>
                        <option value="">Select Priority</option>
                        <option value="LOW">Low</option>
                        <option value="MEDIUM">Medium</option>
                        <option value="HIGH">High</option>
                    </select>
                </div>
                <div className="mb-3">
                    <label htmlFor="severity" className="form-label">Severity :</label>
                    <select className="form-select" id="severity" name="severity" value={ticketData.severity || ''} onChange={handleInputChange} required>
                        <option value="">Select Severity</option>
                        <option value="LOW">Low</option>
                        <option value="MEDIUM">Medium</option>
                        <option value="HIGH">High</option>
                    </select>
                </div>
                <div className="mb-3">
              <label htmlFor="issuetype" className="form-label">Issue Type :</label>
              <select className="form-select" id="issuetype" name="issuetype" value={ticketData.issuetype || ''} onChange={handleInputChange} required>
                <option value="">Select Issue Type</option>
                <option value="CONNECTIVITY">Connectivity</option>
                <option value="ADMINISTRATIVE">Administrative</option>
                <option value="HARDWARE">Hardware</option>
                <option value="PERFORMANCE">Performance</option>

              </select>
            </div>



                <button type="submit" className="btn btn-default">SET</button>
            </form>

            {submittedMessage && (
                <div className="alert alert-success text-center mt-3" role="alert">
                    {submittedMessage}

                    <button className="btn btn-link" onClick={handleNavigateToTicketManagement}>Go to Ticket Dashboard</button>                    
                </div>
            )}
        </div>
    );
}

export default PrioritySeverityTicket;
