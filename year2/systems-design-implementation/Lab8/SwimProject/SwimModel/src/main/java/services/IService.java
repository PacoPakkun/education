package services;

import domain.Inscriere;
import domain.Participant;
import domain.Proba;
import domain.User;

import java.util.List;

public interface IService {

    void login(User user, Observer client) throws Exception;

    void logout(User user) throws Exception;

    List<Proba> findAllProbe() throws Exception;

    Participant addParticipant(Participant participant) throws Exception;

    Inscriere addInscriere(Inscriere inscriere) throws Exception;

    int nrParticipanti(Proba proba) throws Exception;

    List<Participant> getInscrisi(Proba proba) throws Exception;

    List<Proba> getInscrieri(Participant participant) throws Exception;
}
