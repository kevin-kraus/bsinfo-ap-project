import React, {useState} from 'react'
import styles from "./LoginPage.module.scss"
import Button from "react-bootstrap/Button"
import {LoginForm} from "../../components/LoginForm/LoginForm";
import {RegistrationForm} from "../../components/RegistrationForm/RegistrationForm";

type LoginProps = {
    mode: "login" | "registration"
}


export function LoginPage({mode}: LoginProps) {

    const [selectedMode, setSelectedMode] = useState(mode)

    function switchMode(e: any) {
        if (selectedMode === "login") {
            setSelectedMode("registration");
        } else {
            setSelectedMode("login");
        }
    }

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
