import React, {useEffect, useState} from 'react'
import styles from './ContactEditor.module.scss'
import Table from 'react-bootstrap/Table';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import {ContactData} from "../../types/ContactData";
import {ContactService} from "../../service/ContactService";
import trash from './assets/trashcan.svg'
import check from './assets/check.svg'

type ContactEditorProps = {
    username: string
}

export function ContactEditor(props: ContactEditorProps) {

    const [userContactData, setUserContactData] = useState<ContactData[]>();
    const [editableRow, setEditableRow] = useState(false);
    const [newType, setNewType] = useState("EMAIL");
    const [newValue, setNewValue] = useState("");

    async function fetchContactData() {
        const result = await ContactService.getUserContactData(props.username);
        setUserContactData(result);
    }

    async function saveNewValues() {
        const result = await ContactService.saveNewUserContactData(props.username, newType, newValue)
        if (result.id !== null) {
            alert("Neuer Eintrag wurde gespeichert.")
            setNewValue("");
            setNewType("");
            setEditableRow(false);
            fetchContactData();
        } else {
            alert("Irgendetwas ist schiefgelaufen!")
        }
    }

    useEffect(() => {
        fetchContactData()
    }, [])


    return (
        <Table striped bordered hover>
            <thead>
            <tr>
                <th>Typ</th>
                <th>Wert</th>
                <th className={styles.actionCol}></th>
            </tr>
            </thead>
            <tbody>
            {userContactData !== undefined && userContactData.length !== 0 &&
            userContactData.map((element) => (
                <tr>
                    <td>{element.contactType}</td>
                    <td>{element.value}</td>
                    <td><Button variant={"danger"}><img src={trash}/></Button></td>
                </tr>
            ))}

            {editableRow &&
            <tr>
                <td>
                    <Form.Control as="select" onChange={(e) => setNewType(e.target.value)}>
                        <option>EMAIL</option>
                        <option>FAX</option>
                        <option>TELEPHONE</option>
                        <option>ADDRESS</option>
                        <option>SKYPE</option>
                    </Form.Control></td>
                <td>
                    <Form.Control type="text" placeholder="+491631234567"
                                  onChange={(e) => setNewValue(e.target.value)}/></td>
                <td><Button variant={"success"} onClick={() => saveNewValues()}><img src={check}/></Button></td>
            </tr>
            }

            {userContactData !== undefined && userContactData.length === 0 &&
            <tr>
                <td colSpan={3}><h1>Keine Kontaktmöglichkeiten gespeichert!</h1></td>
            </tr>
            }
            <tr>
                <td colSpan={3} className={styles.addRow}><Button onClick={() => setEditableRow(true)}
                                                                  disabled={editableRow}>Hinzufügen</Button></td>
            </tr>
            </tbody>
        </Table>
    )
}
