import {UserData} from "../types/UserData";
import {ErrorCause} from "../types/ErrorCause";
import axios from 'axios';
import {UserResponse} from "../types/UserResponse";

export class UserService {

    static baseUrl = "http://localhost:8080/api/v1";

    public static async registerNewUser(data: UserData): Promise<UserResponse> {
        return await axios({
            method: 'post',
            url: UserService.baseUrl + "/user",
            data: data
        }).then(response => {
            return {
                success: true,
                userData: response.data
            }
        })
            .catch(error => {
                if (error.response.status === 409) {
                    return {
                        success: false,
                        errorCause: ErrorCause.USER_ALREADY_EXISTS
                    }
                } else {
                    return {
                        success: false,
                        errorCause: ErrorCause.UNKNOWN
                    }
                }
            })
    }

    public static async getAllUsers(): Promise<UserData[]> {
        return await axios({
            method: 'get',
            url: UserService.baseUrl + "/users"
        }).then(response => {
            return response.data
        })
    }

    public static async deleteUser(user: UserData): Promise<string> {
        return axios({
            method: 'DELETE',
            url: UserService.baseUrl + "/user/" + user.username
        }).then(response => {
            return response.data;
        }).catch(err => {
            return err;
        });
    }
}
