import React, {useEffect} from 'react';
import {UserList} from "../../components/UserList/UserList";
import styles from './UserManagementPage.module.scss'
import {useCookies} from "react-cookie";

export function UserManagementPage() {

    const [cookies, setCookie, removeCookie] = useCookies(['userInfo']);
    useEffect(() => {
        if (cookies.userInfo === undefined) {
            removeCookie("userInfo")
            window.location.href = "/login"
        } else if (cookies.userInfo.userType == "STANDARD") {
            window.location.href = "/edit/" + cookies.userInfo.username
        }
    }, [cookies])
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
