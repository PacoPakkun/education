package validator;

import domain.Participant;

public class ParticipantValidator implements Validator<Participant>{
    public void validate(Participant participant) throws Exception {
        if(participant.getNume().equals("")){
            throw new Exception("Nume invalid");
        }
        if(participant.getVarsta()<=0){
            throw new Exception("Varsta invalida");
        }
    }
}
