import {ErrorCause} from "./ErrorCause";
import {UserData} from "./UserData";

export interface UserResponse {
    success: boolean,
    errorCause?: ErrorCause,
    userData?: UserData
}
