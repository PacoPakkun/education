package socialnetwork.domain.validators;

import socialnetwork.domain.Utilizator;

public class UtilizatorValidator implements Validator<Utilizator> {
    @Override
    public void validate(Utilizator entity) throws ValidationException {
        if (entity == null) {
            throw new ValidationException("invalid");
        }
        if (entity.getFirstName().equals("") || entity.getLastName().equals("")) {
            throw new ValidationException("invalid");
        }
        if (entity.getId() == 0) {
            throw new ValidationException("invalid");
        }
        if (entity.getFirstName().length() < 3 || entity.getFirstName().length() > 20) {
            throw new ValidationException("invalid");
        }
        if (!entity.getFirstName().matches("[a-zA-Z]+") || !entity.getLastName().matches("[a-zA-Z]+")) {
            throw new ValidationException("invalidd");
        }
    }
}
