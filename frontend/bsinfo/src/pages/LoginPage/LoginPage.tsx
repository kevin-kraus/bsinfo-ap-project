import React from 'react'
import styles from "./LoginPage.module.scss"
import {LoginForm} from "../../components/LoginForm/LoginForm";

export function LoginPage() {
    return (
        <div className={styles.page}>
            <div className={styles.form}>
                <LoginForm/>
            </div>
        </div>
    )
}
