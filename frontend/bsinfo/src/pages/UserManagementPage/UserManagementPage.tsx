import React from 'react';
import {UserList} from "../../components/UserList/UserList";
import styles from './UserManagementPage.module.scss'

export function UserManagementPage() {

    return (
        <div className={styles.page}>
            <div className={styles.container}>
                <div className={styles.header}>
                    <h1>User Management</h1>
                </div>
                <UserList/>
            </div>

        </div>
    )
}
