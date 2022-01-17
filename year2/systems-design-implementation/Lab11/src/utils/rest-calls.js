import {SWIM_URL} from './consts';

function status(response) {
    console.log('response status ' + response.status);
    if (response.status >= 200 && response.status < 300) {
        return Promise.resolve(response)
    } else {
        return Promise.reject(new Error(response.statusText))
    }
}

function json(response) {
    return response.json()
}

export function GetProbe() {
    var headers = new Headers();
    headers.append('Accept', 'application/json');
    var myInit = {
        method: 'GET',
        headers: headers,
        mode: 'cors'
    };
    var request = new Request(SWIM_URL, myInit);

    console.log('Inainte de fetch pentru ' + SWIM_URL)

    return fetch(request)
        .then(status)
        .then(json)
        .then(data => {
            console.log('Request succeeded with JSON response', data);
            return data;
        }).catch(error => {
            console.log('Request failed', error);
            return error;
        });

}

export function DeleteProbe(id) {
    console.log('inainte de fetch delete')
    var myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");

    var antet = {
        method: 'DELETE',
        headers: myHeaders,
        mode: 'cors'
    };

    var userDelUrl = SWIM_URL + '/' + id;

    return fetch(userDelUrl, antet)
        .then(status)
        .then(response => {
            console.log('Delete status ' + response.status);
            return response.text();
        }).catch(e => {
            console.log('error ' + e);
            return Promise.reject(e);
        });
}

export function AddProba(proba) {
    console.log('inainte de fetch post' + JSON.stringify(proba));

    var myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");
    myHeaders.append("Content-Type", "application/json");

    var antet = {
        method: 'POST',
        headers: myHeaders,
        mode: 'cors',
        body: JSON.stringify(proba)
    };

    return fetch(SWIM_URL, antet)
        .then(status)
        .then(response => {
            return response.text();
        }).catch(error => {
            console.log('Request failed', error);
            return Promise.reject(error);
        }); //;
}

export function FindProba(id) {
    console.log('inainte de fetch find')
    var myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");

    var antet = {
        method: 'GET',
        headers: myHeaders,
        mode: 'cors'
    };

    var url = SWIM_URL + '/' + id;

    return fetch(url, antet)
        .then(status)
        .then(response => {
            console.log('Find status ' + response.status);
            return response.text();
        }).catch(e => {
            console.log('error ' + e);
            return Promise.reject(e);
        });
}

export function UpdateProba(id, proba) {
    console.log('inainte de fetch update' + JSON.stringify(proba));

    var myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");
    myHeaders.append("Content-Type", "application/json");

    var antet = {
        method: 'PUT',
        headers: myHeaders,
        mode: 'cors',
        body: JSON.stringify(proba)
    };

    var url = SWIM_URL + '/' + id;

    return fetch(url, antet)
        .then(status)
        .then(response => {
            return response.text();
        }).catch(error => {
            console.log('Request failed', error);
            return Promise.reject(error);
        }); //;
}