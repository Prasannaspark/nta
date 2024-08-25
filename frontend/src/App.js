import React, { useState, useEffect } from "react";
import { Routes, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AuthService from "./services/auth.service";

import Login from "./components/Login";
import Register from "./components/Register";
import Home from "./components/Home";
import Profile from "./components/Profile";

import EventBus from "./common/EventBus";
import UserManagement from "./forms/UserManagement";
import UpdateUser from "./forms/UpdateUser";
import UpdateCustomerTicket from "./forms/UpdateCustomerTicket";
import TicketManagement from "./forms/TicketManagement";
import CustomerTicketManagement from "./forms/CustomerTicketManagement";

import PrioritySeverityTicket from "./forms/PrioritySeverityTicket";
import HardwareHighPriority from "./forms/HardwareHighPriority";
import HardwareMediumPriority from "./forms/HardwareMediumPriority";
import HardwareLowPriority from "./forms/HardwareLowPriority";
import UpdateHardwareTickets from "./forms/UpdateHardwareTicket";
import HardwareCritical from "./forms/HardwareCritical";


import ConnectivityHighPriority from "./forms/ConnectivityHighPriority";
import ConnectivityLowPriority from "./forms/ConnectivityLowPriority";
import ConnectivityMediumPriority from "./forms/ConnectivityMediumPriority";
import UpdateConnectivityTicket from "./forms/UpdateConnectivityTicket";
import ConnectivityCritical from "./forms/ConnectivityCritical";

import PerformanceHighPriority from "./forms/PerformanceHighPriority";
import PerformanceLowPriority from "./forms/PerformanceLowPriority";
import PerformanceMediumPriority from "./forms/PerformanceMediumPriority";
import UpdatePerformanceTicket from "./forms/UpdatePerformanceTicket";
import PerformanceCritical from "./forms/PerformanceCritical";

import AdministrativeHighPriority from "./forms/AdministrativeHighPriority";
import AdministrativeLowPriority from "./forms/AdministrativeLowPriority";
import AdministrativeMediumPriority from "./forms/AdministrativeMediumPriority";
import UpdateAdministrativeTicket from "./forms/UpdateAdministrativeTicket";
import AdministrativeCritical from "./forms/AdministrativeCritical";

import GenerateReport from "./forms/GenerateReport";
import TicketAgingReport from "./forms/TicketAgingReport";
import ResolutionTimeReport from "./forms/ResolutionTimeReport";
import PriorityAnalysisReport from "./forms/PriorityAnalysisReport";
import AdminUpdateTier from "./forms/Adminupdatetier";
import CustomerRequesttoupdatetier from "./forms/CustomerRequestTier";


const App = () => {
  const [showAdmin, setShowAdmin] = useState(false);
  const [showUser, setShowUser] = useState(false);
  const [currentUser, setCurrentUser] = useState(undefined);
  const [showHardwareIT, setshowHardwareIT] = useState(false);
  const [showPerformanceIT, setShowPerformanceIT] = useState(false);
  const [showConnectivityIT, setShowConnectivityIT] = useState(false);
  const [showAdministrativeIT, setShowAdministrativeIT] = useState(false);

  useEffect(() => {
    const user = AuthService.getCurrentUser();

    if (user) {
      setShowAdmin(user.role.includes("ROLE_SYSTEM_ADMIN"));
      setCurrentUser(user);
      setShowPerformanceIT(user.role.includes("ROLE_PERFORMANCE_SUPPORT"));
      setCurrentUser(user);
      setShowConnectivityIT(user.role.includes("ROLE_CONNECTIVITY_SUPPORT"));
      setCurrentUser(user);
      setShowAdministrativeIT(user.role.includes("ROLE_ADMINISTRATIVE_SUPPORT"));
      setCurrentUser(user);
      setShowUser(user.role.includes("ROLE_USER"));
      setCurrentUser(user);
      setshowHardwareIT(user.role.includes("ROLE_HARDWARE_SUPPORT"));
      setCurrentUser(user);
    }
    EventBus.on("logout", () => {
      logOut();
    });

    return () => {
      EventBus.remove("logout");
    };
  }, []);

  const logOut = () => {
    AuthService.logout();
    setShowAdmin(false);
    setShowUser(false);
    setShowPerformanceIT(false);
    setShowConnectivityIT(false);
    setShowAdministrativeIT(false);
    setshowHardwareIT(false);
    setCurrentUser(undefined);
  };

  return (
    <div>
      <nav className="navbar fixed-top navbar-expand custom-navbar">
        <Link to={"/"} className="navbar-brand">
              NetOpsHelpDesk
        </Link>
        <div className="navbar-nav mr-auto">
          <li className="nav-item">
            <Link to={"/home"} className="nav-link">

            </Link>
          </li>

          {showAdmin && (
            <li className="nav-item">
              <Link to={"/usermanagement"} className="nav-link">
                User's Dashboard
              </Link>
            </li>
          )} 

          {showAdmin && (
            <li className="nav-item">
              <Link to={"/ticketmanagement"} className="nav-link">
                Ticket Dashboard
              </Link>
            </li>
          )}

          {showAdmin && (
            <li className="nav-item">
              <Link to={"/generatereport"} className="nav-link">
                Generate Report
              </Link>
            </li>
          )}

          {showUser && (
            <li className="nav-item">
              <Link to={"/customerticketmanagement"} className="nav-link">
                Ticket Dashboard
              </Link>
            </li>
          )}



          {showHardwareIT && (
            <li className="nav-item">
              <Link to={"hardwarehighpriority"} className="nav-link">
                High Priority tickets
              </Link>
            </li>
          )
          }

          {showHardwareIT && (
            <li className="nav-item">
              <Link to={"hardwaremediumpriority"} className="nav-link">
                Medium Priority tickets
              </Link>
            </li>
          )
          }

          {showHardwareIT && (
            <li className="nav-item">
              <Link to={"hardwarelowpriority"} className="nav-link">
                Low Priority tickets
              </Link>
            </li>
          )
          }

          {showHardwareIT && (
            <li className="nav-item">
              <Link to={"criticalhardware"} className="nav-link">
                Critical tickets
              </Link>
            </li>
          )
          }


          {showAdministrativeIT && (
            <li className="nav-item">
              <Link to={"administrativehighpriority"} className="nav-link">
                High Priority tickets
              </Link>
            </li>
          )
          }

          {showAdministrativeIT && (
            <li className="nav-item">
              <Link to={"administrativemediumpriority"} className="nav-link">
                Medium Priority tickets
              </Link>
            </li>
          )
          }

          {showAdministrativeIT && (
            <li className="nav-item">
              <Link to={"administrativelowpriority"} className="nav-link">
                Low Priority tickets
              </Link>
            </li>
          )
          }
          {showAdministrativeIT && (
            <li className="nav-item">
              <Link to={"criticaladministrative"} className="nav-link">
                Critical tickets
              </Link>
            </li>
          )
          }

          {showPerformanceIT && (
            <li className="nav-item">
              <Link to={"performancehighpriority"} className="nav-link">
                High Priority tickets
              </Link>
            </li>
          )
          }

          {showPerformanceIT && (
            <li className="nav-item">
              <Link to={"performancemediumpriority"} className="nav-link">
                Medium Priority tickets
              </Link>
            </li>
          )
          }

          {showPerformanceIT && (
            <li className="nav-item">
              <Link to={"performancelowpriority"} className="nav-link">
                Low Priority tickets
              </Link>
            </li>
          )
          }

          {showPerformanceIT && (
            <li className="nav-item">
              <Link to={"criticalperformance"} className="nav-link">
                Critical tickets
              </Link>
            </li>
          )
          }

          {showConnectivityIT && (
            <li className="nav-item">
              <Link to={"connectivityhighpriority"} className="nav-link">
                High Priority tickets
              </Link>
            </li>
          )
          }

          {showConnectivityIT && (
            <li className="nav-item">
              <Link to={"connectivitymediumpriority"} className="nav-link">
                Medium Priority tickets
              </Link>
            </li>
          )
          }

          {showConnectivityIT && (
            <li className="nav-item">
              <Link to={"connectivitylowpriority"} className="nav-link">
                Low Priority tickets
              </Link>
            </li>
          )
          }
          {showConnectivityIT && (
            <li className="nav-item">
              <Link to={"criticalconnectivity"} className="nav-link">
                Critical tickets
              </Link>
            </li>
          )
          }



        </div>
        {currentUser ? (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/profile"} className="nav-link">
                {currentUser.username}
              </Link>
            </li>
            <li className="nav-item">
              <a href="/login" className="nav-link" onClick={logOut}>
                LogOut
              </a>
            </li>
          </div>
        ) : (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/login"} className="nav-link">
                Sign in
              </Link>
            </li>

            <li className="nav-item">
              <Link to={"/register"} className="nav-link">
                Sign up
              </Link>
            </li>
          </div>
        )}
      </nav>

      <div className="container mt-3">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/home" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/usermanagement" element={<UserManagement />} />
          <Route path="/ticketmanagement" element={<TicketManagement />} />
          <Route path="/update-userrole/:id" element={<UpdateUser />} />
          <Route path="/customerticketmanagement" element={<CustomerTicketManagement />} />
          <Route path="/update-customer-ticket/:ticketId" element={<UpdateCustomerTicket />} />
          <Route path="/admin-priorityseverity-ticket/:ticketId" element={<PrioritySeverityTicket />} />
          <Route path="/hardwarehighpriority" element={<HardwareHighPriority />} />
          <Route path="/hardwaremediumpriority" element={<HardwareMediumPriority />} />
          <Route path="/hardwareLowpriority" element={<HardwareLowPriority />} />
          <Route path="/UpdateHardwareTickets/:ticketId" element={<UpdateHardwareTickets />} />
          <Route path="/performancehighpriority" element={<PerformanceHighPriority />} />
          <Route path="/performancemediumpriority" element={<PerformanceMediumPriority />} />
          <Route path="/performanceLowpriority" element={<PerformanceLowPriority />} />
          <Route path="/UpdatePerformanceTickets/:ticketId" element={<UpdatePerformanceTicket />} />
          <Route path="/connectivityhighpriority" element={<ConnectivityHighPriority />} />
          <Route path="/connectivitymediumpriority" element={<ConnectivityMediumPriority />} />
          <Route path="/connectivityLowpriority" element={<ConnectivityLowPriority />} />
          <Route path="/UpdateConnectivityTickets/:ticketId" element={<UpdateConnectivityTicket />} />
          <Route path="/administrativehighpriority" element={<AdministrativeHighPriority />} />
          <Route path="/administrativemediumpriority" element={<AdministrativeMediumPriority />} />
          <Route path="/administrativeLowpriority" element={<AdministrativeLowPriority />} />
          <Route path="/UpdateAdministrativeTickets/:ticketId" element={<UpdateAdministrativeTicket />} />
          <Route path="/criticaladministrative" element={<AdministrativeCritical />} />
          <Route path="/criticalconnectivity" element={<ConnectivityCritical />} />
          <Route path="/criticalperformance" element={<PerformanceCritical />} />
          <Route path="/criticalhardware" element={<HardwareCritical />} />
          <Route path="/generatereport" element={<GenerateReport/>}/>
          <Route path="/ticketAgingReport" element={<TicketAgingReport/>}/>
          <Route path="/resolutionTimeReport" element={<ResolutionTimeReport/>}/>
          <Route path="/priorityAnalysisReport" element={<PriorityAnalysisReport/>}/>
          <Route path="/tierupdate/:id" element={<AdminUpdateTier/>}/>
          <Route path="/requestforTier" element={<CustomerRequesttoupdatetier/>}/>
        </Routes>
      </div>

    </div>


  );
};

export default App;
