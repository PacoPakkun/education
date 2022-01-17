import React, {useEffect, useState} from "react";
import "./BoardPage.css";
import {getUserTasksForUser} from "../Utils/Services";
import {CircularProgress} from "@mui/material";
import SimpleSlider from "../Components/Slider";
import {Default} from "../Components/Default";

export const BoardPage = (props) => {
        const [tasks, setTasks] = useState(null);
        const [initialSlide, setInitialSlide] = useState(null);

        useEffect(() => {
            if (!props.token) return;

            getUserTasksForUser().then(data => {
                let groupedTasks = data.reduce(function (r, a) {
                    r[a.task.deadline] = r[a.task.deadline] || [];
                    r[a.task.deadline].push(a);
                    return r;
                }, Object.create(null));

                setTasks(
                    Object.values(groupedTasks).sort((a, b) => {
                        return (
                            new Date(a[0].task.deadline) - new Date(b[0].task.deadline)
                        );
                    })
                );
            })
        }, [props.token])

        useEffect(() => {
            for (let i = 0; i < tasks?.length; i++) {
                if (new Date(tasks[i][0].task.deadline) >= Date.now()) {
                    setInitialSlide(i);
                    return;
                }
            }
            setInitialSlide(0);
        }, [tasks])

        return (
            <React.Fragment>
                {props.token ?
                    (initialSlide ?
                            <div className="board-page">
                                <SimpleSlider tasks={tasks} initialSlide={initialSlide}/>
                            </div>
                            :
                            <div className="board-spinner">
                                <CircularProgress color="inherit"/>
                            </div>
                    )
                    :
                    <Default/>
                }
            </React.Fragment>
        );
    }
;
