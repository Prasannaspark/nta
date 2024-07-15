import React from 'react';

const GenerateReport = () => {
  const handleReportSelection = (reportType) => {
    switch (reportType) {
      case 'ticketAging':
        window.location.href = '/ticketAgingReport';
        break;
      case 'resolutionTime':
        window.location.href = '/resolutionTimeReport';
        break;
      case 'priorityAnalysis':
        window.location.href = '/priorityAnalysisReport';
        break;
      default:
        break;
    }
  };

  return (
    <div className="container">
      <h2 className="text-center pd-5"> NetOpsHelpDesk Generate Report</h2>
      <hr />
      <div className="row justify-content-center">
        <div className="col-md-4">
          <button className="btn btn-success btn-lg btn-block" onClick={() => handleReportSelection('ticketAging')}>
           Ticket Aging Report
          </button>
        </div>
        <div className="col-md-4">
          <button className="btn btn-success btn-lg btn-block mt-3 mt-md-0" onClick={() => handleReportSelection('resolutionTime')}>
            Resolution Time Report
          </button>
        </div>
        <div className="col-md-4">
          <button className="btn btn-success btn-lg btn-block mt-3 mt-md-0" onClick={() => handleReportSelection('priorityAnalysis')}>
            Priority Analysis Report
          </button>
        </div>
      </div>
    </div>
  );
};

export default GenerateReport;
