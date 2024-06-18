import React from "react";
import myImage from "./ticket-managementhelpdesk.jpeg"; // Import your image

const Home = () => {
  return (
    <div className="container" style={{ backgroundColor: '#ffe6f2', textAlign: 'center' }}>
      <header className="jumbotron" style={{ backgroundColor: '#e6ffe6', padding: '45px' }}>
        <h3>WELCOME TO NETWORK TICKETING APPLICATION</h3>
      </header>
      <div style={{ display: 'inline-block', maxWidth: '100%', maxHeight: '100%', overflow: 'hidden' }}>
        <img src={myImage} alt="Description of the image" style={{ maxWidth: '100%', height: 'auto', borderRadius: '10px' }} />
      </div>
      <div>
      <footer className="footer bg-dark text-white container "  style={{ padding: '9px' }} >
  <div className="footer-content">
    <p className="font-weight-bold  text-white">&copy; 2024 Network Ticketing Application. All rights reserved.</p>
  </div>
</footer>
      </div>
    </div>
  );
};

export default Home;
