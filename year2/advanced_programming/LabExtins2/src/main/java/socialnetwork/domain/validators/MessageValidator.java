package socialnetwork.domain.validators;

import socialnetwork.domain.Message;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Utilizator;

public class MessageValidator implements Validator<Message> {
    @Override
    public void validate(Message p) throws ValidationException {
        if (p == null || p.getId()<=0 || p.getMessage().equals("")) {
            throw new ValidationException("invalid");
        }
    }
}
