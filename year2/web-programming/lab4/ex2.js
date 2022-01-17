function validateNume(nume) {
    var letters = /^[A-Za-z]+$/;
    if (nume.value === '' || !nume.value.match(letters)) {
        nume.style = "border: 1px solid red;"
        return 'Nume invalid!\n';
    } else {
        nume.style = ""
        return '';
    }
}

function validateData(data) {
    if (data.value === '') {
        data.style = "border: 1px solid red;"
        return 'Data invalida!\n';
    } else {
        data.style = ""
        return '';
    }
}

function validateVarsta(varsta) {
    var bday = new Date(document.getElementById('data').value)
    var age = ~~((Date.now() - bday) / 31557600000)
    if (varsta.value === '' || varsta.value < 1 || varsta.value > 99 || varsta.value != age) {
        varsta.style = "border: 1px solid red;"
        return 'Varsta invalida!\n';
    } else {
        varsta.style = ""
        return '';
    }
}

function validateEmail(email) {
    var letters = /\S+@\S+\.\S+/;
    if (email.value === '' || !email.value.match(letters)) {
        email.style = "border: 1px solid red;"
        return 'Email invalid!\n';
    } else {
        email.style = ""
        return '';
    }
}

function validate() {
    var nume = document.getElementById('nume');
    var data = document.getElementById('data');
    var varsta = document.getElementById('varsta');
    var email = document.getElementById('email');
    var result =
        validateNume(nume) +
        validateData(data) +
        validateVarsta(varsta) +
        validateEmail(email);
    if (result === '') {
        alert('Datele sunt completate corect.')
    } else {
        alert(result);
    }
}