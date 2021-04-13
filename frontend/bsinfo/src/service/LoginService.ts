import axios from 'axios'
import {UserResponse} from "../types/UserResponse";
import {ErrorCause} from "../types/ErrorCause";

interface LoginFormData {
    username: string,
    password: string
}

export async function validateLoginData(data: LoginFormData): Promise<UserResponse> {
    const loginUrl = "http://localhost:8080/api/v1/login";
    return await axios({
        method: 'post',
        url: loginUrl,
        data: data
    }).then(response => {
        return {
            success: true,
            userData: response.data
        }
    })
        .catch(error => {
            if (error.response.status === 401) {
                return {
                    success: false,
                    errorCause: ErrorCause.UNAUTHORIZED
                }
            } else if (error.response.status === 404) {
                return {
                    success: false,
                    errorCause: ErrorCause.NOT_FOUND
                }
            } else {
                return {
                    success: false,
                    errorCause: ErrorCause.UNKNOWN
                }
            }
        })
}
