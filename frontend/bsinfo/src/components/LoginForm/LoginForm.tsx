import React, {useState} from "react";
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button'
import styles from './LoginForm.module.scss';
import {validateLoginData} from "../../service/LoginService";

export function LoginForm() {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    async function loginClickHandler(e: React.MouseEvent<HTMLElement>) {
        e.preventDefault();
        const result = await validateLoginData({username: username, password: password});
        // TODO: handle result of authorization
    }

    return (
        <div className={styles.loginFormContainer}>
            <Form>
                <Form>
                    <Form.Group controlId="formBasicEmail">
                        <Form.Label><strong>Benutzername</strong></Form.Label>
                        <Form.Control type="username" onChange={e => setUsername(e.target.value)}
                                      placeholder="Gebe deinen Benutzernamen ein"/>
                        <Form.Text className="text-muted">
                            Bitte beachte, dass du keine E-Mail Adresse verwenden kannst!
                        </Form.Text>
                    </Form.Group>
                    <Form.Group controlId="formBasicPassword">
                        <Form.Label><strong>Passwort</strong></Form.Label>
                        <Form.Control type="password" onChange={e => setPassword(e.target.value)}
                                      placeholder="Passwort"/>
                    </Form.Group>
                    <Button variant="primary" type="submit" onClick={loginClickHandler}>
                        Anmelden
                    </Button>
                </Form>
            </Form>
        </div>
    )
}
