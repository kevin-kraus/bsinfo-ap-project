import React, {useEffect, useState} from 'react'
import styles from './ContactEditor.module.scss'
import Table from 'react-bootstrap/Table';
import Button from 'react-bootstrap/Button';
import {ContactData} from "../../types/ContactData";
import {ContactService} from "../../service/ContactService";
import trash from './assets/trashcan.svg'

type ContactEditorProps = {
    username: string
}

export function ContactEditor(props: ContactEditorProps) {

    const [userContactData, setUserContactData] = useState<ContactData[]>();


    async function fetchContactData() {
        const result = await ContactService.getUserContactData(props.username);
        setUserContactData(result);
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

            {userContactData !== undefined && userContactData.length === 0 &&
            <tr>
                <td colSpan={3}><h1>Keine Kontaktmöglichkeiten gespeichert!</h1></td>
            </tr>
            }
            </tbody>
        </Table>
    )
}
