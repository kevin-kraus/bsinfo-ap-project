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
}
