import React from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import {UserManagementPage} from "./pages/UserManagementPage/UserManagementPage";
import {BrowserRouter as Router, Redirect, Route, Switch} from "react-router-dom"
import {LoginPage} from "./pages/LoginPage/LoginPage";

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
                <Route path={"/"}>
                    <Redirect to={"/login"}/>
                </Route>
            </Switch>
        </Router>
    );
}

export default App;
