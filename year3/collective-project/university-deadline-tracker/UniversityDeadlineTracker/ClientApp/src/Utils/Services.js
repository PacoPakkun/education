import {getToken, getUser} from "./Token";

// user

export const login = (username, password) => {
    const requestOptions = {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({Username: username, Password: password})
    };
    return fetch('api/account/login', requestOptions).then(response => {
            if (response.status === 200) {
                return response.json()
            } else
                return null
        }
    )
}

export const addUser = (user) => {
    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user)
    };
    return fetch('api/users', requestOptions)
}

// usertasks

export const getUserTasksForUser = async () => {
    const requestOptions = {
        method: 'GET',
        headers: {'Authorization': 'Bearer ' + getToken()}
    };
    const usertasks = await fetch(`api/users/usertasks/${getUser().id}`, requestOptions).then(data =>
        data.json()
    )
    return Promise.all(usertasks.map(async (usertask) => {
        const task = await getTaskById(usertask.taskId);
        const subject = await getSubjectById(task.subjectId);
        return {...usertask, task: {...task, subject: subject}};
    }))
}

export const updateUserTask = (userTask) => {
    const requestOptions = {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            'Authorization': 'Bearer ' + getToken()
        },
        body: JSON.stringify(userTask),
    };
    return fetch(`api/userstasks/${userTask.id}`, requestOptions)
};

// tasks

export const getTaskById = (id) => {
    const requestOptions = {
        method: 'GET',
        headers: {'Authorization': 'Bearer ' + getToken()}
    };
    return fetch(`api/tasks/${id}`, requestOptions).then(data =>
        data.json()
    )
}

// subjects

export const getSubjectById = (id) => {
    const requestOptions = {
        method: 'GET',
        headers: {'Authorization': 'Bearer ' + getToken()}
    };
    return fetch(`api/subjects/${id}`, requestOptions).then(data =>
        data.json()
    )
}

export const getUnassignedSubjects = async () => {
    const requestOptions = {
        method: 'GET',
        headers: {'Authorization': 'Bearer ' + getToken()}
    };
    return fetch(`api/subjects/notAssigned/${getUser().id}`, requestOptions).then(data =>
        data.json()
    )
}

export const enrollUserToSubject = async (subjectId) => {
    const requestOptions = {
        method: 'POST',
        headers: {'Authorization': 'Bearer ' + getToken()}
    };
    return fetch(`api/users/${getUser().id}/addsubject/${subjectId}`, requestOptions)
}

export const getTeacherforSubject = (subjectId) => {
    const requestOptions = {
        method: 'GET',
        headers: {'Authorization': 'Bearer ' + getToken()}
    };
    // return fetch(`api/subjects/teacher/${subjectId}`, requestOptions)
    return 'Mihis Andreea'
}