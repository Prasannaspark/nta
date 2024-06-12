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
import CriticalTicketManagement from "./forms/CriticalTicketsManagement";
import UpdateCriticalTicket from "./forms/UpdateCriticalTicket";
import UpdateNotUrgentButCriticalTicket from "./forms/UpdateNotUrgentButCriticalTicket";
import UpdateUrgentButNotSevereTicket from "./forms/UpdateUrgentButNotSevereTicket";
import NotUrgentButCriticalTicketManagement from "./forms/NotUrgentButCriticalTicketManagement";
import UrgentButNotSevereTicketManagement from "./forms//UrgentButNotSevereTicketManagement";
import MinorTicketManagement from "./forms/MinorTicketManagement";
import UpdateMinorTicket from "./forms/UpdateMinorTicket";
import RoutineTicketManagement from "./forms/RoutineTicketManagement";
import UpdateRoutineTicket from "./forms/UpdateRoutineTicket";
const App = () => {
  const [showAdmin, setShowAdmin] = useState(false);
  const [showSpeciaistIT, setShowSpeciaistIT] = useState(false);
  const [showCapableIT, setShowCapableIT] = useState(false);
  const [showAvailableIT, setShowAvailableIT] = useState(false);
  const [showUser, setShowUser] = useState(false);
  const [currentUser, setCurrentUser] = useState(undefined);

  useEffect(() => {
    const user = AuthService.getCurrentUser();

    if (user) {
      setShowAdmin(user.role.includes("ROLE_ADMIN"));
      setCurrentUser(user);
      setShowSpeciaistIT(user.role.includes("ROLE_SPECIALIST_ITSUPPORT"));
      setCurrentUser(user);
      setShowCapableIT(user.role.includes("ROLE_CAPABLE_ITSUPPORT"));
      setCurrentUser(user);
      setShowAvailableIT(user.role.includes("ROLE_AVAILABLE_ITSUPPORT"));
      setCurrentUser(user);
      setShowUser(user.role.includes("ROLE_USER"));
      setCurrentUser(user);
      console.log(currentUser);
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
    setShowSpeciaistIT(false);
    setShowCapableIT(false);
    setShowAvailableIT(false);
    setShowUser(false);
    setCurrentUser(undefined);
  };

  return (
    <div>
      <nav className="navbar navbar-expand navbar-dark bg-dark">
        <Link to={"/"} className="navbar-brand">
          Network Ticketing Application
        </Link>
        <div className="navbar-nav mr-auto">
          <li className="nav-item">
            <Link to={"/home"} className="nav-link">
              Home
            </Link>
          </li>

          {showAdmin && (
            <li className="nav-item">
              <Link to={"/usermanagement"} className="nav-link">
                User Dashboard
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

          {showUser && (
            <li className="nav-item">
              <Link to={"/customerticketmanagement"} className="nav-link">
                Ticket Dashboard
              </Link>
            </li>
          )}

          {showSpeciaistIT && (
            <li className="nav-item">
              <Link to={"/criticalticketmanagement"} className="nav-link">
                Critical Ticket Dashboard
              </Link>
            </li>
          )}
           {showCapableIT && (
            <li className="nav-item">
              <Link to={"/noturgentbutcriticalticketmanagement"} className="nav-link">
                Not Urgent But Critical Ticket Dashboard
              </Link>
            </li>
          )}
           {showCapableIT && (
            <li className="nav-item">
              <Link to={"/urgentbutnotsevereticketmanagement"} className="nav-link">
                Urgent But Not Severe Ticket Dashboard
              </Link>
            </li>
          )}
          {showAvailableIT && (
            <li className="nav-item">
              <Link to={"/minorticketmanagement"} className="nav-link">
                Minor Ticket Dashboard
              </Link>
            </li>
          )}
          {showAvailableIT && (
            <li className="nav-item">
              <Link to={"/routineticketmanagement"} className="nav-link">
                Routine Ticket Dashboard
              </Link>
            </li>
          )}
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
          <Route path="/criticalticketmanagement" element={<CriticalTicketManagement />} />
          <Route path="/update-critical-ticket/:ticketId" element={<UpdateCriticalTicket />} />
          <Route path="/noturgentbutcriticalticketmanagement" element={<NotUrgentButCriticalTicketManagement />} />
          <Route path="/update-noturgentbutcritical-ticket/:ticketId" element={<UpdateNotUrgentButCriticalTicket />} />
          <Route path="/urgentbutnotsevereticketmanagement" element={<UrgentButNotSevereTicketManagement />} />
          <Route path="/update-urgentbutnotsevere-ticket/:ticketId" element={<UpdateUrgentButNotSevereTicket />} />
          <Route path="/minorticketmanagement" element={<MinorTicketManagement />} />
          <Route path="/update-minor-ticket/:ticketId" element={<UpdateMinorTicket />} />
          <Route path="/routineticketmanagement" element={<RoutineTicketManagement />} />
          <Route path="/update-routine-ticket/:ticketId" element={<UpdateRoutineTicket />} />
        </Routes>
      </div>

    </div>
  );
};

export default App;
