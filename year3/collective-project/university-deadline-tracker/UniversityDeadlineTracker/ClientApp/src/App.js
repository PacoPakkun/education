import React, {useState} from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import './App.css'
import {useToken} from "./Utils/Token";
import {Pages} from "./Utils/Enums";
import {Header} from "./Components/Header";
import LoginPage from "./Pages/LoginPage";
import {BoardPage} from "./Pages/BoardPage";
import BacklogPage from "./Pages/BacklogPage";
import CommunityPage from "./Pages/CommunityPage";

export const App = () => {
    const {token, setToken} = useToken();
    
    return (
        <React.Fragment>
            <Router>
                <Header token={token} setToken={setToken}/>
                <Switch>
                    <Route exact path={Pages.HOME}>
                        <LoginPage token={token} setToken={setToken}/>
                    </Route>
                    <Route path={Pages.BOARD}>
                        <BoardPage token={token}/>
                    </Route>
                    <Route path={Pages.BACKLOG}>
                        <BacklogPage token={token}/>
                    </Route>
                    <Route path={Pages.COMMUNITY}>
                        <CommunityPage token={token}/>
                    </Route>
                </Switch>
            </Router>
        </React.Fragment>
    );
}
export default App
