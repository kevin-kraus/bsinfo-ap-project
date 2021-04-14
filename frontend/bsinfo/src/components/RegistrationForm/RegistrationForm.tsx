import React, {useState} from "react";
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button'
import styles from './RegistrationForm.module.scss';
import {UserService} from "../../service/UserService";
import {UserData} from "../../types/UserData";

export function RegistrationForm() {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const allFieldsFilled = username === "" || password === "" || firstName === "" || lastName === "" || email === "";

    async function registerClickHandler(e: any) {
        e.preventDefault()
        // const form = e.currentTarget;
        const userData: UserData = {
            username: username,
            password: password,
            firstName: firstName,
            lastName: lastName,
            emailAddress: email
        }

        const result = await UserService.registerNewUser(userData)

        console.log(result);
    }

    return (
        <div className={styles.registrationFormContainer}>
            <Form noValidate onSubmit={registerClickHandler}>
                <Form.Group controlId="formBasicEmail">
                    <Form.Label><strong>Benutzername</strong></Form.Label>
                    <Form.Control
                        type="username" onChange={e => setUsername(e.target.value)}
                        placeholder="Gebe deinen Benutzernamen ein"/>
                    <Form.Text className="text-muted">
                        Bitte beachte, dass du keine E-Mail Adresse verwenden kannst!
                    </Form.Text>
                </Form.Group>
                <Form.Group controlId="formBasicPassword">
                    <Form.Label><strong>Passwort</strong></Form.Label>
                    <Form.Control
                        type="password" onChange={e => setPassword(e.target.value)}
                        placeholder="Gebe dein Passwort ein"/>
                </Form.Group>
                <Form.Group controlId="formBasicPassword">
                    <Form.Label><strong>Vorname</strong></Form.Label>
                    <Form.Control
                        type="text" onChange={e => setFirstName(e.target.value)}
                        placeholder="Gebe deinen Vornamen ein"/>
                </Form.Group>
                <Form.Group>
                    <Form.Label><strong>Nachname</strong></Form.Label>
                    <Form.Control
                        type="text" onChange={e => setLastName(e.target.value)}
                        placeholder="Gebe deinen Nachnamen ein"/>
                </Form.Group>
                <Form.Group>
                    <Form.Label><strong>E-Mail</strong></Form.Label>
                    <Form.Control
                        type="email" onChange={e => setEmail(e.target.value)}
                        placeholder="Gebe deine E-Mail Adresse ein"/>
                </Form.Group>
                <Button disabled={allFieldsFilled} variant="primary" type="submit">
                    Registrieren
                </Button>
            </Form>
        </div>
    )
}
