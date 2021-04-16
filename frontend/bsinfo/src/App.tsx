import React from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import {UserManagementPage} from "./pages/UserManagementPage/UserManagementPage";
import {BrowserRouter as Router, Redirect, Route, Switch} from "react-router-dom"
import {LoginPage} from "./pages/LoginPage/LoginPage";
import {EditorPage} from "./pages/EditorPage/EditorPage";

function App() {
    return (
        <Router>
            <Switch>
                <Route path={"/login"}>
                    <LoginPage mode={"login"}/>
                </Route>
                <Route path={"/register"}>
                    <LoginPage mode={"registration"}/>
                </Route>
                <Route path={"/manageUsers"}>
                    <UserManagementPage/>
                </Route>
                <Route path={"/edit/:userId"}>
                    <EditorPage/>
                </Route>
                <Route path={"/"}>
                    <Redirect to={"/login"}/>
                </Route>
            </Switch>
        </Router>
    );
}

export default App;
