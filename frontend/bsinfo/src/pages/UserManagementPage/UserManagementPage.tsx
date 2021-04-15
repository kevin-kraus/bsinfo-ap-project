import React from 'react';
import {UserList} from "../../components/UserList/UserList";
import styles from './UserManagementPage.module.scss'

export function UserManagementPage() {

    return (
        <div className={styles.container}>

            <UserList/>

        </div>
    )
}
