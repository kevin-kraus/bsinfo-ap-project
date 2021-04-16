import React, {useState} from 'react';
import Form from 'react-bootstrap/Form'
import {UserData} from "../../types/UserData";
import Button from 'react-bootstrap/Button'
import styles from "./ProfileEditor.module.scss"
import {UserService} from "../../service/UserService";
import {ContactEditor} from "../ContactEditor/ContactEditor";
import {useHistory} from "react-router-dom";
import {useCookies} from "react-cookie"

type EditorProps = {
    userData: UserData
}


export function ProfileEditor(props: EditorProps) {
    let [firstName, setFirstName] = useState(props.userData.firstName);
    let [lastName, setLastName] = useState(props.userData.lastName);
    let [userType, setUserType] = useState(props.userData.userType);
    let [emailAddress, setEmailAddress] = useState(props.userData.emailAddress);
    let history = useHistory();

    const [cookies, ,] = useCookies(['userInfo']);

    async function saveChanges(e: any) {
        e.preventDefault();
        let newUserData: UserData = {
            username: props.userData.username,
            firstName: firstName,
            lastName: lastName,
            userType: userType,
            emailAddress: emailAddress
        }
        let result = await UserService.saveChanges(newUserData)
        if (result.success) {
            alert("Änderungen wurden gespeichert!");
            history.push("/manageUsers");
        } else {
            alert("Irgentetwas ist schiefgelaufen!")
        }
    }

    return (
        <Form className={styles.form}>
            <Form.Group>
                <Form.Label>Vorname</Form.Label>
                <Form.Control type="firstName" value={firstName} onChange={e => setFirstName(e.target.value)}/>
            </Form.Group>
            <Form.Group>
                <Form.Label>Nachname</Form.Label>
                <Form.Control type="lastName" value={lastName} onChange={e => setLastName(e.target.value)}/>
            </Form.Group>
            <Form.Group>
                <Form.Label>E-Mail Adresse</Form.Label>
                <Form.Control type="email" value={emailAddress} onChange={e => setEmailAddress(e.target.value)}/>
            </Form.Group>
            {cookies.userInfo.userType === "ADMIN" &&
            <Form.Group>
                <Form.Label>Benutzer-Typ</Form.Label>
                <div>
                    <Form.Check inline checked={userType === "ADMIN"} label="Admin" type={"radio"}
                                onChange={() => setUserType("ADMIN")}/>
                    <Form.Check inline checked={userType === "STANDARD"} label="Standard"
                                onChange={() => setUserType("STANDARD")}
                                type={"radio"}/>
                </div>
            </Form.Group>
            }
            <Form.Group>
                <Form.Label>Kontaktmöglichkeiten</Form.Label>
                <ContactEditor username={props.userData.username}/>
            </Form.Group>
            <Button variant="primary" type="submit" onClick={saveChanges}>
                Speichern
            </Button>
            <Button variant="danger" type="submit" onClick={() => history.push("/manageUsers")}>
                Abbrechen
            </Button>
        </Form>
    )
}
