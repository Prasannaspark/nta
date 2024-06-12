import AdminService from '../services/AdminService';
import { Link } from 'react-router-dom';
import { useState } from 'react';
import {  useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
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
    <div className="container">
      <h2>Users</h2>
        <table className="table mt-4">
                <thead>
                <tr>
                    <th>User ID</th>
                    <th>Username</th>
                    <th>Password_Hash</th>
                    <th>Email</th>
                    <th>Role ID</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {users.map(user => (
                    <tr key={user.id}>
                    <td>{user.id}</td>
                    <td>{user.username}</td>
                    <td>{user.password}</td>
                    <td>{user.email}</td>
                    <td>{user.role.id}</td>
                    <td>
                        <button><Link to={`/update-userrole/${user.id}`}> Update </Link></button>
                    </td>
                    </tr>
                ))}
                </tbody>
            </table>
            </div>
        );
};

export default UserManagement;