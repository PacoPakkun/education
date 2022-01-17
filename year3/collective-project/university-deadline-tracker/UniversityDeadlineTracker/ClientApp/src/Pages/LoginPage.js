import React from 'react';
import "./LoginPage.css"
import {Pages, SubjectType} from "../Utils/Enums";
import {useHistory} from "react-router-dom";
import {enrollUserToSubject, getUnassignedSubjects, getTeacherforSubject} from "../Utils/Services";
import {CircularProgress} from "@mui/material";
import {Default} from "../Components/Default";
import {Login} from "../Components/Login";
import {getUser} from "../Utils/Token";
import {CSSTransition, TransitionGroup} from "react-transition-group";
import {LIGHTER_GREY, ORANGE_ACCENT} from "../Utils/Constants";

export const LoginPage = (props) => {
        let history = useHistory();
        const [subjects, setSubjects] = React.useState(null)

        React.useEffect(() => {
            if (!props.token) return;

            getUnassignedSubjects().then(data => {
                setSubjects(data)
            })
        }, [props.token])

        const getSubjectButtons = () => {
            return <TransitionGroup>
                {subjects?.map(subject => {
                    return <CSSTransition key={subject.id} timeout={450} classNames="subject-transition">
                        <div className="subject" onClick={() => {
                            enrollUserToSubject(subject.id).then(r => {
                                setSubjects(subjects.filter(s => s.id !== subject.id))
                            })
                        }}>
                            <div className="subject-year"
                                 style={{backgroundColor: subject.type === SubjectType.OBLIGATORIU ? ORANGE_ACCENT : LIGHTER_GREY}}>
                                y{subject.year}
                            </div>
                            <div className="subject-name">{subject.name}</div>
                            <div className="subject-teacher">{`Teacher: ${getTeacherforSubject(subject.id)}`}</div>
                        </div>
                    </CSSTransition>
                })}
            </TransitionGroup>
        }

        return (
            <React.Fragment>{
                props.token ?
                    <div className="login-page">
                        <p className="hello">Hello <span className="orange">{getUser().username}</span>!</p>
                        <p>Welcome back to your University Deadline Tracker!</p>
                        <div className="button" onClick={() => history.push(Pages.BOARD)}>Check your Boards!</div>
                        <p className="enroll">Enroll to subjects to stay up to date with upcoming tasks!</p>
                        <div className="subject-board">
                            <div className="filter"/>
                            {subjects ?
                                getSubjectButtons()
                                :
                                <div className="board-spinner">
                                    <CircularProgress color="inherit"/>
                                </div>
                            }
                        </div>
                    </div>
                    :
                    <React.Fragment>
                        <Default main/>
                        <Login token={props.token} setToken={props.setToken}/>
                    </React.Fragment>
            }
            </React.Fragment>
        );
    }
;
export default LoginPage;