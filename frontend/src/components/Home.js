// // import React, { useEffect, useRef } from "react";
// // import myImage from "./ticket-managementhelpdesk.jpeg";

// // const Home = () => {
// //   const imgRef = useRef(null);

// //   useEffect(() => {
// //     const img = imgRef.current;

// //     const breakAndReassemble = () => {
// //       img.style.transition = "transform 0.5s ease, filter 0.5s ease";
// //       img.style.transform = "scale(1.1) rotate(5deg)";
// //       img.style.filter = "brightness(1.2) contrast(1.1)";

// //       setTimeout(() => {
// //         img.style.transform = "scale(1) rotate(0deg)";
// //         img.style.filter = "brightness(1) contrast(1)";
// //       }, 500);
// //     };

// //     img.addEventListener("mouseover", breakAndReassemble);

// //     return () => {
// //       img.removeEventListener("mouseover", breakAndReassemble);
// //     };
// //   }, []);

// //   return (
// //     <div className="container-fluid p-0" style={{ backgroundColor: '#f8f9fa' }}>
// //       <header className="jumbotron text-center py-5 mb-0" style={{ 
// //         background: 'linear-gradient(135deg, #e6ffe6 0%, #b3d9ff 100%)',
// //         boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)'
// //       }}>
// //         <h1 className="display-4 font-weight-bold mb-4" style={{ color: '#333' }}>WELCOME TO NetOpsHelpDesk</h1>
// //         <p className="lead font-italic" style={{ 
// //           color: '#dc3545', 
// //           padding: '15px',
// //           background: 'rgba(255, 255, 255, 0.7)',
// //           borderRadius: '10px',
// //           boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)'
// //         }}>
// //           "Your network's lifeline where every ticket finds its solution"
// //         </p>
// //       </header>

// //       <div className="text-center my-5">
// //         <div className="image-container d-inline-block position-relative" style={{ 
// //           maxWidth: '80%', 
// //           overflow: 'hidden', 
// //           borderRadius: '15px',
// //           boxShadow: '0 10px 20px rgba(0, 0, 0, 0.2)'
// //         }}>
// //           <img
// //             ref={imgRef}
// //             src={myImage}
// //             alt="NetOpsHelpDesk"
// //             className="img-fluid"
// //             style={{ transition: 'transform 0.5s ease, filter 0.5s ease' }}
// //           />
// //         </div>
// //       </div>

// //       <section className="features py-5" style={{ backgroundColor: '#e9ecef' }}>
// //         <div className="container">
// //           <h2 className="text-center mb-5" style={{ 
// //             fontSize: '2.5rem', 
// //             fontWeight: 'bold',
// //             color: '#333',
// //             textShadow: '2px 2px 4px rgba(0,0,0,0.1)'
// //           }}>Our Services</h2>
// //           <div className="row">
// //             {[
// //               { name: 'Ticket Management', icon: 'fas fa-ticket-alt', description: 'Efficiently manage and track all your support tickets.' },
// //               { name: 'Network Diagnostics', icon: 'fas fa-network-wired', description: 'Advanced tools for diagnosing network issues quickly.' },
// //               { name: '24/7 Support', icon: 'fas fa-headset', description: 'Round-the-clock assistance for all your network needs.' }
// //             ].map((service, index) => (
// //               <div key={index} className="col-md-4 mb-4">
// //                 <div className="card h-100 shadow-sm" style={{
// //                   transition: 'transform 0.3s ease, box-shadow 0.3s ease',
// //                 }}>
// //                   <div className="card-body text-center">
// //                     <i className={`${service.icon} fa-3x mb-3 text-primary`}></i>
// //                     <h5 className="card-title">{service.name}</h5>
// //                     <p className="card-text">{service.description}</p>
// //                   </div>
// //                 </div>
// //               </div>
// //             ))}
// //           </div>
// //         </div>
// //       </section>

// //       <footer className="bg-dark text-white text-center py-4">
// //         <div className="container">
// //           <p className="mb-0">&copy; 2024 NetOpsHelpDesk. All rights reserved.</p>
// //         </div>
// //       </footer>
// //     </div>
// //   );
// // };

// // export default Home;

// import React, { useEffect, useRef } from "react";
// import myImage from "./ticket-managementhelpdesk.jpeg";

// const Home = () => {
//   const imgRef = useRef(null);

//   useEffect(() => {
//     const img = imgRef.current;

//     const breakAndReassemble = () => {
//       img.style.transition = "transform 0.5s ease, filter 0.5s ease";
//       img.style.transform = "scale(1.1) rotate(5deg)";
//       img.style.filter = "brightness(1.2) contrast(1.1)";

//       setTimeout(() => {
//         img.style.transform = "scale(1) rotate(0deg)";
//         img.style.filter = "brightness(1) contrast(1)";
//       }, 500);
//     };

//     img.addEventListener("mouseover", breakAndReassemble);

//     return () => {
//       img.removeEventListener("mouseover", breakAndReassemble);
//     };
//   }, []);

//   const services = [
//     { name: 'Ticket Management', icon: 'fas fa-ticket-alt', description: 'Efficiently manage and track all your support tickets.' },
//     { name: 'Network Diagnostics', icon: 'fas fa-network-wired', description: 'Advanced tools for diagnosing network issues quickly.' },
//     { name: '24/7 Support', icon: 'fas fa-headset', description: 'Round-the-clock assistance for all your network needs.' },
//     { name: 'Performance Monitoring', icon: 'fas fa-tachometer-alt', description: 'Real-time monitoring of your network performance.' },
//     { name: 'Security Analysis', icon: 'fas fa-shield-alt', description: 'Comprehensive security checks and vulnerability assessments.' },
//     { name: 'Cloud Integration', icon: 'fas fa-cloud', description: 'Seamless integration with cloud services and infrastructure.' }
//   ];

//   return (
//     <div className="container-fluid p-0" style={{ backgroundColor: '#f8f9fa' }}>
//       <header className="jumbotron text-center py-5 mb-0" style={{ 
//         background: 'linear-gradient(135deg, #e6ffe6 0%, #b3d9ff 100%)',
//         boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)'
//       }}>
//         <h1 className="display-4 font-weight-bold mb-4" style={{ color: '#333' }}>WELCOME TO NetOpsHelpDesk</h1>
//         <p className="lead font-italic" style={{ 
//           color: '#dc3545', 
//           padding: '15px',
//           background: 'rgba(255, 255, 255, 0.7)',
//           borderRadius: '10px',
//           boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)'
//         }}>
//           "Your network's lifeline where every ticket finds its solution"
//         </p>
//       </header>

//       <div className="text-center my-5">
//         <div className="image-container d-inline-block position-relative" style={{ 
//           maxWidth: '80%', 
//           overflow: 'hidden', 
//           borderRadius: '15px',
//           boxShadow: '0 10px 20px rgba(0, 0, 0, 0.2)'
//         }}>
//           <img
//             ref={imgRef}
//             src={myImage}
//             alt="NetOpsHelpDesk"
//             className="img-fluid"
//             style={{ transition: 'transform 0.5s ease, filter 0.5s ease' }}
//           />
//         </div>
//       </div>

//       <section className="features py-5" style={{ backgroundColor: '#e9ecef' }}>
//         <div className="container">
//           <h2 className="text-center mb-5" style={{ 
//             fontSize: '2.5rem', 
//             fontWeight: 'bold',
//             color: '#333',
//             textShadow: '2px 2px 4px rgba(0,0,0,0.1)'
//           }}>Our Services</h2>
//           <div className="row">
//             {services.map((service, index) => (
//               <div key={index} className="col-lg-4 col-md-6 mb-4">
//                 <div className="card h-100 shadow-sm" style={{
//                   transition: 'all 0.3s ease',
//                   transform: 'translateY(0)',
//                   cursor: 'pointer'
//                 }}
//                 onMouseEnter={(e) => {
//                   e.currentTarget.style.transform = 'translateY(-10px)';
//                   e.currentTarget.style.boxShadow = '0 4px 20px rgba(0,0,0,0.1)';
//                 }}
//                 onMouseLeave={(e) => {
//                   e.currentTarget.style.transform = 'translateY(0)';
//                   e.currentTarget.style.boxShadow = '0 1px 3px rgba(0,0,0,0.12)';
//                 }}>
//                   <div className="card-body text-center">
//                     <div className="icon-wrapper mb-3" style={{
//                       width: '80px',
//                       height: '80px',
//                       borderRadius: '50%',
//                       background: 'linear-gradient(135deg, #6e8efb, #a777e3)',
//                       display: 'flex',
//                       alignItems: 'center',
//                       justifyContent: 'center',
//                       margin: '0 auto',
//                       transition: 'all 0.3s ease'
//                     }}>
//                       <i className={`${service.icon} fa-2x text-white`}></i>
//                     </div>
//                     <h5 className="card-title font-weight-bold mb-3">{service.name}</h5>
//                     <p className="card-text">{service.description}</p>
//                   </div>
//                 </div>
//               </div>
//             ))}
//           </div>
//         </div>
//       </section>

//       <footer className="bg-dark text-white text-center py-4">
//         <div className="container">
//           <p className="mb-0">&copy; 2024 NetOpsHelpDesk. All rights reserved.</p>
//         </div>
//       </footer>
//     </div>
//   );
// };

// export default Home;

import React, { useEffect, useRef } from "react";
import { FaTicketAlt, FaNetworkWired, FaHeadset, FaTachometerAlt, FaShieldAlt, FaCloud } from 'react-icons/fa';
import myImage from "./ticket-managementhelpdesk.jpeg";
 
const Home = () => {
  const imgRef = useRef(null);
 
  useEffect(() => {
    const img = imgRef.current;
 
    const breakAndReassemble = () => {
      img.style.transition = "transform 0.5s ease, filter 0.5s ease";
      img.style.transform = "scale(1.1) rotate(5deg)";
      img.style.filter = "brightness(1.2) contrast(1.1)";
 
      setTimeout(() => {
        img.style.transform = "scale(1) rotate(0deg)";
        img.style.filter = "brightness(1) contrast(1)";
      }, 500);
    };
 
    img.addEventListener("mouseover", breakAndReassemble);
 
    return () => {
      img.removeEventListener("mouseover", breakAndReassemble);
    };
  }, []);
 
  const services = [
    { name: 'Prioritization', icon: <FaTicketAlt />, description: 'Streamline your support process with efficient ticket management and tracking, ensuring no issue goes unresolved.' },
    { name: 'Timely Resolution', icon: <FaNetworkWired />, description: 'Utilize advanced diagnostic tools to swiftly identify and resolve network issues, minimizing downtime.' },
    { name: '24/7 Support', icon: <FaHeadset />, description: 'call for queries  18001030025' },
    { name: 'Reporting', icon: <FaTachometerAlt />, description: 'Access real-time analytics and performance metrics ' },
    { name: 'Tracking', icon: <FaShieldAlt />, description: 'Tracking the Ticket Status' },
    { name: 'User Interface', icon: <FaCloud />, description: 'Enjoy seamless integration with cloud services and infrastructure through an intuitive and user-friendly interface.' }
  ];
 
  return (
<div className="container-fluid p-5" style={{ backgroundColor: '#f8f9fa' }}>
<header className="jumbotron text-center py-5 mb-0" style={{
        background: 'linear-gradient(135deg, #e6ffe6 0%, #b3d9ff 100%)',
        boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)'
      }}>
<h1 className="display-4 font-weight-bold mb-4" style={{ color: '#333' }}>WELCOME TO NetOpsHelpDesk</h1>
<p className="lead font-italic" style={{
          color: '#dc3545',
          padding: '15px',
          background: 'rgba(255, 255, 255, 0.7)',
          borderRadius: '10px',
          boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)'
        }}>
          "Your network's lifeline where every ticket finds its solution"
</p>
</header>
 
      <div className="text-center my-5">
<div className="image-container d-inline-block position-relative" style={{
          maxWidth: '80%',
          overflow: 'hidden',
          borderRadius: '15px',
          boxShadow: '0 10px 20px rgba(0, 0, 0, 0.2)'
        }}>
<img
            ref={imgRef}
            src={myImage}
            alt="NetOpsHelpDesk"
            className="img-fluid"
            style={{ transition: 'transform 0.5s ease, filter 0.5s ease' }}
          />
</div>
</div>
 
      <section className="features py-5" style={{ backgroundColor: '#e9ecef' }}>
<div className="container">
<h2 className="text-center mb-5" style={{
            fontSize: '2.5rem',
            fontWeight: 'bold',
            color: '#333',
            textShadow: '2px 2px 4px rgba(0,0,0,0.1)'
          }}>Our Services</h2>
<div className="row">
            {services.map((service, index) => (
<div key={index} className="col-lg-4 col-md-6 mb-4">
<div className="card h-100 shadow-sm" style={{
                  transition: 'all 0.3s ease',
                  transform: 'translateY(0)',
                  cursor: 'pointer'
                }}
                onMouseEnter={(e) => {
                  e.currentTarget.style.transform = 'translateY(-10px)';
                  e.currentTarget.style.boxShadow = '0 4px 20px rgba(0,0,0,0.1)';
                }}
                onMouseLeave={(e) => {
                  e.currentTarget.style.transform = 'translateY(0)';
                  e.currentTarget.style.boxShadow = '0 1px 3px rgba(0,0,0,0.12)';
                }}>
<div className="card-body text-center">
<div className="icon-wrapper mb-3" style={{
                      width: '80px',
                      height: '80px',
                      borderRadius: '50%',
                      background: 'linear-gradient(135deg, #6e8efb, #a777e3)',
                      display: 'flex',
                      alignItems: 'center',
                      justifyContent: 'center',
                      margin: '0 auto',
                      transition: 'all 0.3s ease'
                    }}>
<div className="fa-2x text-white">{service.icon}</div>
</div>
<h5 className="card-title font-weight-bold mb-3">{service.name}</h5>
<p className="card-text">{service.description}</p>
</div>
</div>
</div>
            ))}
</div>
</div>
</section>
 
      <footer className="bg-dark text-white text-center py-4">
<div className="container">
<p className="mb-0">&copy; 2024 NetOpsHelpDesk. All rights reserved.</p>
</div>
</footer>
</div>
  );
};
 
export default Home;

