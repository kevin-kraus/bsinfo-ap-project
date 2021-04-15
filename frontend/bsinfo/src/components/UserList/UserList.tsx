import React, {useEffect, useState} from 'react'
import Table from 'react-bootstrap/Table'
import styles from "./UserList.module.scss"
import {UserService} from "../../service/UserService";
import {UserData} from "../../types/UserData";

export function UserList() {
    let [userData, setUserData] = useState<UserData[]>([]);

    // @ts-ignore
    useEffect(() => {
        async function fetchUsers() {
            const users = await UserService.getAllUsers();
            setUserData(users)
        }

        fetchUsers();
    }, [])

    return (
        <Table className={styles.userTable} striped bordered hover>
            <thead>
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Vorname</th>
                <th>Nachname</th>
                <th>E-Mail</th>
                <th>Benutzertyp</th>
            </tr>
            </thead>
            <tbody>
            {userData.length !== 0 && userData.map((userData, index) => {
                return (
                    <tr>
                        <td>{userData.id}</td>
                        <td>{userData.username}</td>
                        <td>{userData.firstName}</td>
                        <td>{userData.lastName}</td>
                        <td>{userData.emailAddress}</td>
                        <td>{userData.userType}</td>
                    </tr>
                )
            })

            }

            </tbody>
        </Table>
    )
}
