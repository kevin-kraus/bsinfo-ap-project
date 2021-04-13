import axios from 'axios'
import {AuthResponse} from "../types/AuthResponse";
import {ErrorCause} from "../types/ErrorCause";

interface LoginFormData {
    username: string,
    password: string
}

export async function validateLoginData(data: LoginFormData): Promise<AuthResponse> {
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
