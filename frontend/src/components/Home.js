import React, { useEffect, useRef } from "react";

import myImage from "./ticket-managementhelpdesk.jpeg"; 

const Home = () => {
  const imgRef = useRef(null);

  useEffect(() => {
    const img = imgRef.current;

    const breakAndReassemble = () => {
     
      img.style.transition = "transform 1s ease";
      img.style.transform = "scale(1.5) rotate(20deg)";

     
      setTimeout(() => {
        img.style.transition = "transform 1s ease";
        img.style.transform = "scale(1) rotate(0deg)";
      }, 1000); 
    };

    img.addEventListener("mouseover", breakAndReassemble);

    return () => {
      img.removeEventListener("mouseover", breakAndReassemble);
    };
  }, []);

  return (
    <div className="container" style={{ backgroundColor: '#ffe6f2', textAlign: 'center' }}>
      <header className="jumbotron" style={{ backgroundColor: '#e6ffe6', padding: '45px' }}>
        <h3><bold>WELCOME TO NetOpsHelpDesk</bold></h3> <br/>
        <p style={{ color: 'red', padding: '10px' }}><strong>
    " Your network’s lifeline where every ticket finds its solution "
    </strong></p>      </header>
      <div style={{ display: 'inline-block', maxWidth: '100%', maxHeight: '100%', overflow: 'hidden' }}>
        <img
          ref={imgRef}
          src={myImage}
          alt="Description of the image"
          className="break-reassemble-img"
          style={{ maxWidth: '100%', height: 'auto', borderRadius: '10px' }}
        />
      </div>
      <footer className="footer bg-dark text-white container" style={{ padding: '9px' }}>
        <div className="footer-content">
          <p className="font-weight-bold text-white">&copy; 2024 Network Ticketing Application. All rights reserved.</p>
        </div>
      </footer>
    </div>
  );
};

export default Home;

