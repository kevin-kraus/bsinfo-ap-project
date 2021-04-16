import React, {useState} from "react";
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button'
import styles from './LoginForm.module.scss';
import {validateLoginData} from "../../service/LoginService";
import {ErrorCause} from "../../types/ErrorCause";

export function LoginForm() {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const [validUsername, setUsernameValid] = useState("");
    const [validPassword, setPasswordValid] = useState("");


    async function loginClickHandler(e: any) {
        e.preventDefault()
        const form = e.currentTarget;
        if (form.checkValidity() === false) {
            e.stopPropagation();
        }

        const result = await validateLoginData({username: username, password: password});
        if (result.success) {
            setPasswordValid("true")
            setUsernameValid("true")
        } else if (result.errorCause === ErrorCause.NOT_FOUND) {
            setUsernameValid("false");
            setPasswordValid("false");
        } else if (result.errorCause === ErrorCause.UNAUTHORIZED) {
            setPasswordValid("false");
            setUsernameValid("true");
        }
    }

    return (
        <div className={styles.loginFormContainer}>
            <Form noValidate onSubmit={loginClickHandler}>
                <Form.Group controlId="formBasicEmail">
                    <Form.Label><strong>Benutzername</strong></Form.Label>
                    <Form.Control isValid={validUsername === "true"} isInvalid={validUsername === "false"}
                                  type="username" onChange={e => setUsername(e.target.value)}
                                  placeholder="Gebe deinen Benutzernamen ein"/>
                    <Form.Text className="text-muted">
                        Bitte beachte, dass du keine E-Mail Adresse verwenden kannst!
                    </Form.Text>
                </Form.Group>
                <Form.Group controlId="formBasicPassword">
                    <Form.Label><strong>Passwort</strong></Form.Label>
                    <Form.Control isValid={validPassword === "true"} isInvalid={validPassword === "false"}
                                  type="password" onChange={e => setPassword(e.target.value)}
                                  placeholder="Passwort"/>
                </Form.Group>
                <Button disabled={username.length === 0 || password.length === 0} variant="primary" type="submit">
                    Anmelden
                </Button>
                </Form>
        </div>
    )
}
