import React, {useState} from 'react'
import styles from "./LoginPage.module.scss"
import {RegistrationForm} from "../../components/RegistrationForm/RegistrationForm";
import {LoginForm} from "../../components/LoginForm/LoginForm";
import Button from "react-bootstrap/Button"

export function LoginPage() {

    const [selectedMode, setSelectedMode] = useState("login")

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
                <div className={styles.switcher}>
                    <Button className={styles.switcher} variant="info" onClick={switchMode}>
                        {selectedMode !== "login" ? "Zum Login" : "Zur Registrierung"}
                    </Button>
                </div>
                {selectedMode === "login" &&
                <LoginForm/>
                }

                {selectedMode === "registration" &&
                <RegistrationForm/>
                }
            </div>
        </div>
    )
}
