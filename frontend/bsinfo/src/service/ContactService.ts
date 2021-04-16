import axios from 'axios';
import {ContactData} from "../types/ContactData";

export class ContactService {
    private static baseUrl = "http://localhost:8080/api/v1/user/contact/";

    public static async getUserContactData(username: string): Promise<ContactData[]> {
        return await axios({
            method: 'GET',
            url: ContactService.baseUrl + username
        }).then(response => {
            return response.data
        })
    }

    static async saveNewUserContactData(username: string, newType: string, newValue: string) {
        return await axios({
            method: 'POST',
            url: ContactService.baseUrl + username,
            data: {
                contactType: newType,
                value: newValue
            }
        }).then(response => {
            return response.data
        })
    }

    static async deleteUserContactData(id: number, username: string) {
        return await axios({
            method: 'DELETE',
            url: ContactService.baseUrl + username + "/" + id,
        }).then(response => {
            return response.data
        })
    }
}
