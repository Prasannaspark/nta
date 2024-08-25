// import AdminService from '../services/AdminService';
// import { Link } from 'react-router-dom';
// import { useState, useEffect } from 'react';
// import 'bootstrap/dist/css/bootstrap.min.css';
// import './UserManagement.css';

// const UserManagement = () => {
//     const [users, setUsers] = useState([]);
   
//     useEffect(() => {
//         fetchUsers();
//     }, []);

//     const fetchUsers = () => {
//         AdminService.viewUsers()
//           .then(response => {
//             setUsers(response);
//             console.log(response);
//           })
//           .catch(error => {
//             console.error('Error fetching users : ', error);
//           });
//     };
      
//     return (
//         <div className="container mt-5 pt-5">
//             <h2 className="text-center mb-5 pt-3">USERS</h2>
//             <div className="table-responsive">
//                 <table className="table table-striped table-bordered">
//                     <thead>
//                         <tr>
//                             <th>User ID</th>
//                             <th>Username</th>
//                             <th>User Tier</th>
//                             <th>Email</th>
//                             <th>Role </th>
//                             <th>Role Update</th>
//                             <th>Tier Update</th>
//                         </tr>
//                     </thead>
//                     <tbody>
//                         {users.map(user => (
//                             <tr key={user.id}>
//                                 <td>{user.id}</td>
//                                 <td>{user.username}</td>
//                                 <td>{user.tier}</td>
//                                 <td>{user.email}</td>
//                                 <td>{user.role.name}</td>
//                                 <td>
//                                 <button class="btn btn-success"><Link to={`/update-userrole/${user.id}`} style={{ textDecoration: 'none', color: 'white' }}>
//                                         Update
//                                     </Link></button>
//                                 </td>
//                                 <td>
//                                 <button class="btn btn-success"><Link to={`/tierupdate/${user.id}`} style={{ textDecoration: 'none', color: 'white' }}>
//                                         Update
//                                     </Link></button>
//                                 </td>
//                             </tr>
//                         ))}
//                     </tbody>
//                 </table>
//             </div>
//         </div>
//     );
// };

// export default UserManagement;

// import AdminService from '../services/AdminService';
// import { Link } from 'react-router-dom';
// import { useState, useEffect } from 'react';
// import 'bootstrap/dist/css/bootstrap.min.css';
// import './UserManagement.css';

// const UserManagement = () => {
//     const [users, setUsers] = useState([]);
//     const [inputQuery, setInputQuery] = useState('');
//     const [searchQuery, setSearchQuery] = useState('');

//     useEffect(() => {
//         fetchUsers();
//     }, []);

//     const fetchUsers = () => {
//         AdminService.viewUsers()
//           .then(response => {
//             setUsers(response);
//             console.log(response);
//           })
//           .catch(error => {
//             console.error('Error fetching users : ', error);
//           });
//     };

//     const handleInputChange = (e) => {
//         setInputQuery(e.target.value);
//     };

//     const handleSearchClick = () => {
//         setSearchQuery(inputQuery);
//     };

//     const filteredUsers = users.filter(user => 
//         user.username.toLowerCase().includes(searchQuery.toLowerCase())
//     );
      
//     return (
//         <div className="container mt-5 pt-5">
//             <h2 className="text-center mb-5 pt-3">USERS</h2>
//             <div className="mb-3 d-flex">
//                 <input 
//                     type="text" 
//                     className="form-control" 
//                     placeholder="Search by Username" 
//                     value={inputQuery} 
//                     onChange={handleInputChange} 
//                 />
//                 <button className="btn btn-primary ml-2" onClick={handleSearchClick}>
//                     <i className="fas fa-search"></i>
//                 </button>
//             </div>
//             <div className="table-responsive">
//                 <table className="table table-striped table-bordered">
//                     <thead>
//                         <tr>
//                             <th>User ID</th>
//                             <th>Username</th>
//                             <th>User Tier</th>
//                             <th>Email</th>
//                             <th>Role</th>
//                             <th>Role Update</th>
//                             <th>Tier Update</th>
//                         </tr>
//                     </thead>
//                     <tbody>
//                         {filteredUsers.map(user => (
//                             <tr key={user.id}>
//                                 <td>{user.id}</td>
//                                 <td>{user.username}</td>
//                                 <td>{user.tier}</td>
//                                 <td>{user.email}</td>
//                                 <td>{user.role.name}</td>
//                                 <td>
//                                     <button className="btn btn-success">
//                                         <Link to={`/update-userrole/${user.id}`} style={{ textDecoration: 'none', color: 'white' }}>
//                                             Update
//                                         </Link>
//                                     </button>
//                                 </td>
//                                 <td>
//                                     <button className="btn btn-success">
//                                         <Link to={`/tierupdate/${user.id}`} style={{ textDecoration: 'none', color: 'white' }}>
//                                             Update
//                                         </Link>
//                                     </button>
//                                 </td>
//                             </tr>
//                         ))}
//                     </tbody>
//                 </table>
//             </div>
//         </div>
//     );
// };

// export default UserManagement;

// import React, { useState, useEffect } from 'react';
// import { Link } from 'react-router-dom';
// import AdminService from '../services/AdminService';
// import 'bootstrap/dist/css/bootstrap.min.css';
// import { FaSearch } from 'react-icons/fa';
// import './UserManagement.css';

// const UserManagement = () => {
//     const [users, setUsers] = useState([]);
//     const [inputQuery, setInputQuery] = useState('');
//     const [searchQuery, setSearchQuery] = useState('');

//     useEffect(() => {
//         fetchUsers();
//     }, []);

//     const fetchUsers = () => {
//         AdminService.viewUsers()
//           .then(response => {
//             setUsers(response);
//             console.log(response);
//           })
//           .catch(error => {
//             console.error('Error fetching users : ', error);
//           });
//     };

//     const handleInputChange = (e) => {
//         setInputQuery(e.target.value);
//     };

//     const handleSearchClick = () => {
//         setSearchQuery(inputQuery);
//     };

//     const filteredUsers = users.filter(user => 
//         user.username.toLowerCase().includes(searchQuery.toLowerCase())
//     );
      
//     return (
//         <div className="container mt-5 pt-5">
//             <h2 className="text-center mb-5 pt-3">USERS</h2>
//             <div className="mb-3 d-flex">
//                 <input 
//                     type="text" 
//                     className="form-control" 
//                     placeholder="Search by Username" 
//                     value={inputQuery} 
//                     onChange={handleInputChange} 
//                 />
//                 <button className="btn btn-primary ml-2" onClick={handleSearchClick}>
//                     <FaSearch />
//                 </button>
//             </div>
//             <div className="table-responsive">
//                 <table className="table table-striped table-bordered">
//                     <thead>
//                         <tr>
//                             <th>User ID</th>
//                             <th>Username</th>
//                             <th>User Tier</th>
//                             <th>Email</th>
//                             <th>Role</th>
//                             <th>Request</th>
//                             <th>Role Update</th>
//                             <th>Tier Update</th>
//                         </tr>
//                     </thead>
//                     <tbody>
//                         {filteredUsers.map(user => (
//                             <tr key={user.id}>
//                                 <td>{user.id}</td>
//                                 <td>{user.username}</td>
//                                 <td>{user.tier}</td>
//                                 <td>{user.email}</td>
//                                 <td>{user.role.name}</td>
//                                 <td>{user.request}</td>
//                                 <td>
//                                     <button className="btn btn-success">
//                                         <Link to={`/update-userrole/${user.id}`} style={{ textDecoration: 'none', color: 'white' }}>
//                                             Update
//                                         </Link>
//                                     </button>
//                                 </td>
//                                 <td>
//                                     <button className="btn btn-success">
//                                         <Link to={`/tierupdate/${user.id}`} style={{ textDecoration: 'none', color: 'white' }}>
//                                             Update
//                                         </Link>
//                                     </button>
//                                 </td>
//                             </tr>
//                         ))}
//                     </tbody>
//                 </table>
//             </div>
//         </div>
//     );
// };

// export default UserManagement;

// import React, { useState, useEffect } from 'react';
// import { Link } from 'react-router-dom';
// import AdminService from '../services/AdminService';
// import 'bootstrap/dist/css/bootstrap.min.css';
// import { FaSearch } from 'react-icons/fa';
// import './UserManagement.css';

// const UserManagement = () => {
//     const [users, setUsers] = useState([]);
//     const [inputQuery, setInputQuery] = useState('');
//     const [searchQuery, setSearchQuery] = useState('');
//     const [filter, setFilter] = useState('username');

//     useEffect(() => {
//         fetchUsers();
//     }, []);

//     const fetchUsers = () => {
//         AdminService.viewUsers()
//           .then(response => {
//             setUsers(response);
//             console.log(response);
//           })
//           .catch(error => {
//             console.error('Error fetching users : ', error);
//           });
//     };

//     const handleInputChange = (e) => {
//         setInputQuery(e.target.value);
//     };

//     const handleFilterChange = (e) => {
//         setFilter(e.target.value);
//     };

//     const handleSearchClick = () => {
//         setSearchQuery(inputQuery);
//     };

//     const filteredUsers = users.filter(user => {
//         if (filter === 'username') {
//             return user.username.toLowerCase().includes(searchQuery.toLowerCase());
//         } else if (filter === 'request') {
//             return user.request && user.request.toLowerCase().includes(searchQuery.toLowerCase());
//         }
//         return false;
//     });

//     return (
//         <div className="container mt-5 pt-5">
//             <h2 className="text-center mb-5 pt-3">USERS</h2>
//             <div className="mb-3 d-flex filter-container">
//                 <select className="form-control filter-select mr-2" value={filter} onChange={handleFilterChange}>
//                     <option value="username">Username</option>
//                     <option value="request">REQUEST</option>
//                 </select>
//                 <input 
//                     type="text" 
//                     className="form-control search-input" 
//                     placeholder={`Search by ${filter}`} 
//                     value={inputQuery} 
//                     onChange={handleInputChange} 
//                 />
//                 <button className="btn btn-primary search-button ml-2" onClick={handleSearchClick}>
//                     <FaSearch />
//                 </button>
//             </div>
//             <div className="table-responsive">
//                 <table className="table table-striped table-bordered">
//                     <thead>
//                         <tr>
//                             <th>User ID</th>
//                             <th>Username</th>
//                             <th>User Tier</th>
//                             <th>Email</th>
//                             <th>Role</th>
//                             <th>Request</th>
//                             <th>Role Update</th>
//                             <th>Tier Update</th>
//                         </tr>
//                     </thead>
//                     <tbody>
//                         {filteredUsers.map(user => (
//                             <tr key={user.id}>
//                                 <td>{user.id}</td>
//                                 <td>{user.username}</td>
//                                 <td>{user.tier}</td>
//                                 <td>{user.email}</td>
//                                 <td>{user.role.name}</td>
//                                 <td>{user.request}</td>
//                                 <td>
//                                     <button className="btn btn-success">
//                                         <Link to={`/update-userrole/${user.id}`} style={{ textDecoration: 'none', color: 'white' }}>
//                                             Update
//                                         </Link>
//                                     </button>
//                                 </td>
//                                 <td>
//                                     <button className="btn btn-success">
//                                         <Link to={`/tierupdate/${user.id}`} style={{ textDecoration: 'none', color: 'white' }}>
//                                             Update
//                                         </Link>
//                                     </button>
//                                 </td>
//                             </tr>
//                         ))}
//                     </tbody>
//                 </table>
//             </div>
//         </div>
//     );
// };

// export default UserManagement;

import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import AdminService from '../services/AdminService';
import 'bootstrap/dist/css/bootstrap.min.css';
import { FaSearch } from 'react-icons/fa';
import './UserManagement.css';

const UserManagement = () => {
    const [users, setUsers] = useState([]);
    const [inputQuery, setInputQuery] = useState('');
    const [searchQuery, setSearchQuery] = useState('');
    const [filter, setFilter] = useState('username');

    useEffect(() => {
        fetchUsers();
    }, []);

    const fetchUsers = () => {
        AdminService.viewUsers()
          .then(response => {
            setUsers(response);
            console.log(response);
          })
          .catch(error => {
            console.error('Error fetching users : ', error);
          });
    };

    const handleInputChange = (e) => {
        setInputQuery(e.target.value);
    };

    const handleFilterChange = (e) => {
        setFilter(e.target.value);
    };

    const handleSearchClick = () => {
        setSearchQuery(inputQuery);
    };

    const filteredUsers = users.filter(user => {
        if (filter === 'username') {
            return user.username.toLowerCase().includes(searchQuery.toLowerCase());
        } else if (filter === 'request') {
            return user.request && user.request.toLowerCase().includes(searchQuery.toLowerCase());
        }
        return false;
    });

    return (
        <div className="container mt-5 pt-5">
            <h2 className="text-center mb-5 pt-3">USERS</h2>
            <div className="mb-3 d-flex filter-container">
                <select className="form-control filter-select mr-2" value={filter} onChange={handleFilterChange}>
                    <option value="username">Username</option>
                    <option value="request">Request</option>
                </select>
                <input 
                    type="text" 
                    className="form-control search-input" 
                    placeholder={`Search by ${filter}`} 
                    value={inputQuery} 
                    onChange={handleInputChange} 
                />
                <button className="btn btn-primary search-button ml-2" onClick={handleSearchClick}>
                    <FaSearch />
                </button>
            </div>
            <div className="table-responsive">
                <table className="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th>User ID</th>
                            <th>Username</th>
                            <th>User Tier</th>
                            <th>Email</th>
                            <th>Role</th>
                            <th>Request</th>
                            <th>Role Update</th>
                            <th>Tier Update</th>
                        </tr>
                    </thead>
                    <tbody>
                        {filteredUsers.map(user => (
                            <tr key={user.id}>
                                <td>{user.id}</td>
                                <td>{user.username}</td>
                                <td>{user.tier}</td>
                                <td>{user.email}</td>
                                <td>{user.role.name}</td>
                                <td>{user.request}</td>
                                <td>
                                    <button className="btn btn-success btn-sm">
                                        <Link to={`/update-userrole/${user.id}`} style={{ textDecoration: 'none', color: 'white' }}>
                                            Update
                                        </Link>
                                    </button>
                                </td>
                                <td>
                                    <button className="btn btn-success btn-sm">
                                        <Link to={`/tierupdate/${user.id}`} style={{ textDecoration: 'none', color: 'white' }}>
                                            Update
                                        </Link>
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default UserManagement;




