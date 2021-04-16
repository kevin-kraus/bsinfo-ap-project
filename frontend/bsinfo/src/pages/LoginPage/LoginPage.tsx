import React, {useEffect, useState} from 'react'
import styles from "./LoginPage.module.scss"
import Button from "react-bootstrap/Button"
import {LoginForm} from "../../components/LoginForm/LoginForm";
import {RegistrationForm} from "../../components/RegistrationForm/RegistrationForm";
import {useCookies} from 'react-cookie';

type LoginProps = {
    mode: "login" | "registration"
}


export function LoginPage({mode}: LoginProps) {
    const [cookies, setCookie, removeCookie] = useCookies(['userInfo']);
    const [selectedMode, setSelectedMode] = useState(mode)

    function switchMode(e: any) {
        if (selectedMode === "login") {
            setSelectedMode("registration");
        } else {
            setSelectedMode("login");
        }
    }

    useEffect(() => {
        if (cookies.userInfo !== undefined) {
            if (cookies.userInfo.userType === "ADMIN") {
                window.location.href = "/manageUsers"
            } else {
                window.location.href = "/edit/" + cookies.userInfo.username
            }
        }
    }, [cookies])

    return (
        <div className={styles.page}>
            <div className={styles.form}>
                <div className={styles.header}><h1>{selectedMode === "login" ? "Login" : "Registrierung"}</h1></div>
                {selectedMode === "login" &&
                <LoginForm/>
                }

                {selectedMode === "registration" &&
                <RegistrationForm/>
                }
                <div className={styles.switcher}>
                    <Button className={styles.switcher} variant="info" onClick={switchMode}>
                        {selectedMode !== "login" ? "Zum Login" : "Zur Registrierung"}
                    </Button>
                </div>
            </div>
        </div>
    )
}
