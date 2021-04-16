import React, {useEffect} from 'react';
import {useCookies} from "react-cookie";

export function LogoutPage() {

    const [cookies, setCookie, removeCookie] = useCookies(['userInfo']);

    useEffect(() => {
        removeCookie("userInfo")
        window.location.href = "/login";
    }, [])

    return (
        <h1>Logout...</h1>
    )
}
