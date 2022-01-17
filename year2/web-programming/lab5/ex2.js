function validateNume() {
    var letters = /^[A-Za-z]+$/;
    if ($('#nume').val() === '' || !$('#nume').val().match(letters)) {
        $('#nume').css("border", "1px solid red")
        return 'Nume invalid!\n';
    } else {
        $('#nume').css("border", "")
        return '';
    }
}

function validateData() {
    if ($('#data').val() === '') {
        $('#data').css("border", "1px solid red")
        return 'Data invalida!\n';
    } else {
        $('#data').css("border", "")
        return '';
    }
}

function validateVarsta() {
    var bday = new Date($('#data').val())
    var age = ~~((Date.now() - bday) / 31557600000)
    if ($('#varsta').val() === '' || $('#varsta').val() < 1 || $('#varsta').val() > 99 || $('#varsta').val() != age) {
        $('#varsta').css("border", "1px solid red")
        return 'Varsta invalida!\n';
    } else {
        $('#varsta').css("border", "")
        return '';
    }
}

function validateEmail() {
    var letters = /\S+@\S+\.\S+/;
    if ($('#email').val() === '' || !$('#email').val().match(letters)) {
        $('#email').css("border", "1px solid red")
        return 'Email invalid!\n';
    } else {
        $('#email').css("border", "")
        return '';
    }
}

$("#submit").click(function () {
    var result =
        validateNume() +
        validateData() +
        validateVarsta() +
        validateEmail();
    if (result === '') {
        alert('Datele sunt completate corect.')
    } else {
        alert(result);
    }
})

// function validate() {
//     var nume = document.getElementById('nume');
//     var data = document.getElementById('data');
//     var varsta = document.getElementById('varsta');
//     var email = document.getElementById('email');
//     var result =
//         validateNume(nume) +
//         validateData(data) +
//         validateVarsta(varsta) +
//         validateEmail(email);
//     if (result === '') {
//         alert('Datele sunt completate corect.')
//     } else {
//         alert(result);
//     }
// }