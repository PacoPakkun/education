import * as React from 'react';
import './Default.css'
import {useHistory} from "react-router-dom";
import {Pages} from "../Utils/Enums";

export const Default = (props) => {
    const history = useHistory();

    return (
        <div className="default-component" style={{marginTop: props.main ? '3%' : '7%'}}>
            {props.main ?
                <p className="bigger">Welcome to <span className="accent">University Deadline Tracker</span>!</p>
                :
                <p className="bigger">Join our <span className="accent">community</span>!</p>
            }
            <ul>
                <li>Manage all your university tasks and deadlines on interactive boards</li>
                <li>Review your completed achievements in the backlog and witness your progress</li>
                <li>Join the student community for help and news</li>
            </ul>
            <p>Sign into your account to enjoy the full experience!</p>
            {!props.main && <div className="button" onClick={() => history.push(Pages.HOME)}>Login now!</div>}
        </div>
    );
}
;