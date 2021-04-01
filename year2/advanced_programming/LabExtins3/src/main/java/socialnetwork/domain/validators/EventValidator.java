package socialnetwork.domain.validators;

import socialnetwork.domain.Eveniment;

public class EventValidator implements Validator<Eveniment> {
    @Override
    public void validate(Eveniment p) throws ValidationException {
        if (p.getData() == null) {
            throw new ValidationException("Data invalida");
        }
        if (p.getTitlu() == "") {
            throw new ValidationException("Titlu invalid");
        }
        if (p.getDescriere() == "") {
            throw new ValidationException("Descriere invalida");
        }
    }
}
