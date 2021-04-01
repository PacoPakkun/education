package socialnetwork.domain.validators;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Utilizator;

public class PrietenieValidator implements Validator<Prietenie> {
    @Override
    public void validate(Prietenie p) throws ValidationException {
        if (p == null || p.getId().getLeft() == p.getId().getRight()) {
            throw new ValidationException("invalid");
        }
    }
}
