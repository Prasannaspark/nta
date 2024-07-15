import React, { useEffect, useState, useRef } from 'react';
import { Table, Button, Container } from 'react-bootstrap';
import { pdf } from '@react-pdf/renderer';
import ReportPDF from '../components/ReportPDF';
import AdminService from '../services/AdminService';
 
const TicketAgingReport = () => {
  const [ticketAgingReport, setTicketAgingReport] = useState([]);
  const [error, setError] = useState(null);
  const reportRef = useRef(null);
 
  useEffect(() => {
    const fetchData = async () => {
      try {
        const result = await AdminService.getTicketAgingReport();
        setTicketAgingReport(result);
      } catch (error) {
        setError(error);
        console.error('Error fetching ticket aging report:', error);
      }
    };
       
    fetchData();
  }, []);
 
  const handleDownloadPDF = async () => {
    const blob = await pdf(<ReportPDF data={ticketAgingReport} />).toBlob();
    const url = URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = 'ticket-aging-report.pdf';
    link.click();
  };
 
  if (error) {
    return <div>Error fetching data: {error.message}</div>;
  }
 
  return (
    <Container className="my-5">
      <h2 className="text-center mb-4">Ticket Aging Report</h2>
      <Button
        variant="primary"
        onClick={handleDownloadPDF}
        className="mb-3"
      >
        Download PDF
      </Button>
      <div ref={reportRef}>
        {Array.isArray(ticketAgingReport) && ticketAgingReport.length > 0 ? (
          <Table striped bordered hover responsive className="shadow-sm">
            <thead className="bg-dark ">
              <tr>
                <th>Ticket ID</th>
                <th>Title</th>
                <th>User Name</th>
                <th>Creation Date</th>
                <th>Last Updated</th>
                <th>Priority</th>
                <th>Status</th>
                <th>Aging Days</th>
              </tr>
            </thead>
            <tbody>
              {ticketAgingReport.map((item) => (
                <tr key={item.ticket_id}>
                  <td>{item.ticket_id}</td>
                  <td>{item.title}</td>
                  <td>{item.user_name}</td>
                  <td>{item.creation_date}</td>
                  <td>{item.last_updated}</td>
                  <td>{item.priority}</td>
                  <td>{item.status}</td>
                  <td>{item.aging_days}</td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          <p>No Reports found</p>
        )}
      </div>
    </Container>
  );
};
 
export default TicketAgingReport;