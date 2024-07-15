import AdminService from '../services/AdminService';
import { Link } from 'react-router-dom';
import { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './UserManagement.css';

const UserManagement = () => {
    const [users, setUsers] = useState([]);
   
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
      
    return (
        <div className="container mt-5 pt-5">
            <h2 className="text-center mb-5 pt-3">USERS</h2>
            <div className="table-responsive">
                <table className="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th>User ID</th>
                            <th>Username</th>
                            <th>Password_Hash</th>
                            <th>User Tier</th>
                            <th>Email</th>
                            <th>Role </th>
                            <th>Role Update</th>
                            <th>Tier Update</th>
                        </tr>
                    </thead>
                    <tbody>
                        {users.map(user => (
                            <tr key={user.id}>
                                <td>{user.id}</td>
                                <td>{user.username}</td>
                                <td>{user.password}</td>
                                <td>{user.tier}</td>
                                <td>{user.email}</td>
                                <td>{user.role.name}</td>
                                <td>
                                <button class="btn btn-success"><Link to={`/update-userrole/${user.id}`} style={{ textDecoration: 'none', color: 'white' }}>
                                        Update
                                    </Link></button>
                                </td>
                                <td>
                                <button class="btn btn-success"><Link to={`/tierupdate/${user.id}`} style={{ textDecoration: 'none', color: 'white' }}>
                                        Update
                                    </Link></button>
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
