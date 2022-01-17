import {useState} from 'react';

export const getToken = () => {
    
    return sessionStorage.getItem('token');
};

export const getUser = () => {
    return JSON.parse(sessionStorage.getItem('user'));
};

export const useToken = () => {

    const [token, setToken] = useState(getToken());

    const saveToken = (userToken, userData) => {
        if (userToken === null && userData === null)
            sessionStorage.clear();
        else {
            sessionStorage.setItem('token', userToken);
            sessionStorage.setItem('user', JSON.stringify(userData));
        }
        setToken(userToken);
    };

    return {
        token: token,
        setToken: saveToken
    }
}