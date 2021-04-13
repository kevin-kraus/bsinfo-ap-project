import {ErrorCause} from "./ErrorCause";
import {UserData} from "./UserData";

export interface AuthResponse {
    success: boolean,
    errorCause?: ErrorCause,
    userData?: UserData
}
