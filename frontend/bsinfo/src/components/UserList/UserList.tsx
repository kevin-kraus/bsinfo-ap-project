import React, {useEffect, useState} from 'react'
import Table from 'react-bootstrap/Table'
import Button from 'react-bootstrap/Button'
import styles from "./UserList.module.scss"
import {UserService} from "../../service/UserService";
import {UserData} from "../../types/UserData";

export function UserList() {
    let [userData, setUserData] = useState<UserData[]>([]);

    async function fetchUsers() {
        const users = await UserService.getAllUsers();
        setUserData(users)
    }

    useEffect(() => {
        fetchUsers();
    }, [])

    async function deleteUser(userToBeDeleted: UserData) {
        const result = await UserService.deleteUser(userToBeDeleted);
        if (result === "SUCCESSFUL") {
            setUserData([]);
            fetchUsers();
        } else {
            alert("Something went wrong!")
        }
    }

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
                <th>Aktionen</th>
            </tr>
            </thead>
            <tbody>
            {userData.map((userData) => {
                    return (
                        <tr>
                            <td>{userData.id}</td>
                            <td>{userData.username}</td>
                            <td>{userData.firstName}</td>
                            <td>{userData.lastName}</td>
                            <td>{userData.emailAddress}</td>
                            <td>{userData.userType}</td>
                            <td>
                                <Button variant={"outline-info"}
                                        onClick={() => window.location.href = "/edit/kkraus"}>Bearbeiten</Button>
                                <Button variant={"outline-danger"} onClick={() => deleteUser(userData)}>Löschen</Button>
                            </td>
                        </tr>
                    )
                }
            )
            }

            {userData.length === 0 &&
            <tr>
                <td colSpan={7}>
                    <h3 className={styles.noUsers}>Keine Benutzer vorhanden!</h3>
                </td>
            </tr>
            }

            </tbody>
        </Table>
    )
}
