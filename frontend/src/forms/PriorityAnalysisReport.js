import React, { useEffect, useState } from 'react';
import { Table, Button, Container } from 'react-bootstrap';
import { pdf } from '@react-pdf/renderer';
import ReportPDF from '../components/ReportPDF';
import AdminService from '../services/AdminService';
 
const PriorityAnalysisReport = () => {
  const [report, setReport] = useState([]);
  const [error, setError] = useState(null);
 
  useEffect(() => {
    fetchData();
  }, []);
 
  const fetchData = async () => {
    try {
      const response = await AdminService.getPriorityAnalysisReport();
      setReport(response);
    } catch (error) {
      setError(error);
      console.error('Error fetching priority analysis report:', error);
    }
  };
 
  const handleDownloadPDF = async () => {
    try {
      const blob = await pdf(<ReportPDF data={report} />).toBlob();
      const url = URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.download = 'priority-analysis-report.pdf';
      link.click();
    } catch (error) {
      console.error('Error generating PDF:', error);
    }
  };
 
  if (error) {
    return <div>Error fetching priority analysis report: {error.message}</div>;
  }
 
  return (
    <Container className="my-5">
      <h2 className="text-center mb-4">Priority Analysis Report</h2>
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
                <th>Priority</th>
                <th>Count</th>
              </tr>
            </thead>
            <tbody>
              {report.map((rep) => (
                <tr key={rep.priority}>
                  <td>{rep.priority}</td>
                  <td>{rep.count}</td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          <p>No data available for priority analysis report.</p>
        )}
      </div>
    </Container>
  );
};
 
export default PriorityAnalysisReport;