package socialnetwork.domain.validators;

import socialnetwork.domain.Utilizator;

public class UtilizatorValidator implements Validator<Utilizator> {
    @Override
    public void validate(Utilizator entity) throws ValidationException {
        if(entity.getFirstName().equals(""))
            throw new ValidationException("Nume Invalid!");
        if(entity.getLastName().equals(""))
            throw new ValidationException("Prenume Invalid!");
        if(entity.getHash().equals(""))
            throw new ValidationException("Parola Invalida!");
        if(entity.getPic().equals(""))
            throw new ValidationException("Poza Invalida!");
    }
}
