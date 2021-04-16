import React, {useEffect, useState} from 'react'
import {useParams} from "react-router-dom";
import {UserData} from "../../types/UserData";
import {UserService} from "../../service/UserService";
import {ProfileEditor} from "../../components/ProfileEditor/ProfileEditor";
import styles from "./EditorPage.module.scss"
import {useCookies} from "react-cookie";

export function EditorPage() {

    const [cookies, setCookie, removeCookie] = useCookies(['userInfo']);
    useEffect(() => {
        if (cookies.userInfo === undefined) {
            removeCookie("userInfo")
            window.location.href = "/login"
        } else if (cookies.userInfo.username !== userId && cookies.userInfo.userType == "STANDARD") {
            window.location.href = "/edit/" + userId
        }
    }, [cookies])
    // @ts-ignore
    let {userId} = useParams();
    let [userData, setUserData] = useState<UserData>();

    async function fetchUser(username: string) {
        let result = await UserService.fetchUser(username);
        setUserData(result);
    }

    useEffect(() => {
        fetchUser(userId);
    })

    return (
        <div className={styles.page}>
            {userData !== undefined &&
            <div className={styles.form}>
                <div className={styles.header}>
                    <h1>Profil Editor für {userData.username}</h1>
                </div>
                <ProfileEditor userData={userData}/>
            </div>
            }
        </div>

    )
}
