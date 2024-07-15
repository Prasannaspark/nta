import React, { useEffect, useState } from 'react';
import { Table, Button, Container } from 'react-bootstrap';
import { pdf } from '@react-pdf/renderer';
import ReportPDF from '../components/ReportPDF';
import AdminService from '../services/AdminService';
 
const ResolutionTimeReport = () => {
  const [report, setReport] = useState([]);
  const [error, setError] = useState(null);
 
  useEffect(() => {
    fetchData();
  }, []);
 
  const fetchData = async () => {
    try {
      const response = await AdminService.getResolutionTimeReport();
      setReport(response);
    } catch (error) {
      setError(error);
      console.error('Error fetching resolution time report:', error);
    }
  };
 
  const handleDownloadPDF = async () => {
    try {
      const blob = await pdf(<ReportPDF data={report} />).toBlob();
      const url = URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.download = 'resolution-time-report.pdf';
      link.click();
    } catch (error) {
      console.error('Error generationg PDF:', error);
    }
  };
 
 
  if (error) {
    return <div>Error fetching resolution time report.</div>;
  }
 
  return (
    <Container className="my-5">
      <h2 className="text-center mb-4">Resolution Time Report</h2>
      <Button
        variant="primary"
        onClick={handleDownloadPDF}
        className="mb-3"
      >
        Download PDF
      </Button>
      <div>
        {Array.isArray(report) && report.length > 0 ? (
          <Table striped bordered hover responsive className="shadow-sm">
            <thead className="bg-dark ">
              <tr>
                <th>Ticket ID</th>
                <th>Username</th>
                <th>Subject</th>
                <th>Creation Date</th>
                <th>Status</th>
                <th>Priority</th>
                <th>Last Updated</th>
                <th>Resolution Time (Days)</th>
              </tr>
            </thead>
            <tbody>
              {report.map((rep) => (
                <tr key={rep.ticket_id}>
                  <td>{rep.ticketId}</td>
                  <td>{rep.username}</td>
                  <td>{rep.title}</td>
                  <td>{rep.creationDate}</td>
                  <td>{rep.status}</td>
                  <td>{rep.priority}</td>
                  <td>{rep.lastUpdated}</td>
                  <td>{rep.resolutiontime}</td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          <p>No data available for resolution time report.</p>
        )}
      </div>
    </Container>
  );
};
 
export default ResolutionTimeReport;