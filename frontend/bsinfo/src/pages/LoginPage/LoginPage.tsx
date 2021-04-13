import React from 'react'
import styles from "./LoginPage.module.scss"
import {RegistrationForm} from "../../components/RegistrationForm/RegistrationForm";

export function LoginPage() {
    return (
        <div className={styles.page}>
            <div className={styles.form}>
                <RegistrationForm/>
            </div>
        </div>
    )
}
