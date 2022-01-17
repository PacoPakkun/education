package srv;

import domain.Inscriere;
import domain.Participant;
import domain.Proba;
import domain.User;
import repo.intf.InscriereRepository;
import repo.intf.ParticipantRepository;
import repo.intf.ProbaRepository;
import repo.intf.UserRepository;

import java.util.List;

public class Service {

    private ParticipantRepository participantRepository;
    private ProbaRepository probaRepository;
    private InscriereRepository inscriereRepository;
    private UserRepository userRepository;

    public Service(ParticipantRepository participantRepository, ProbaRepository probaRepository, InscriereRepository inscriereRepository, UserRepository userRepository) {
        this.participantRepository = participantRepository;
        this.probaRepository = probaRepository;
        this.inscriereRepository = inscriereRepository;
        this.userRepository = userRepository;
    }

    public List<User> findAllUseri() {
        return userRepository.findAll();
    }

    public List<Proba> findAllProbe() {
        return probaRepository.findAll();
    }

    public Participant addParticipant(Participant participant) throws Exception {
        return participantRepository.add(participant);
    }

    public Inscriere addInscriere(Inscriere inscriere) throws Exception {
        return inscriereRepository.add(inscriere);
    }

    public int nrParticipanti(Proba proba) {
        return inscriereRepository.nrParticipanti(proba);
    }

    public List<Participant> getInscrisi(Proba proba) {
        return inscriereRepository.getInscrisi(proba);
    }

    public List<Proba> getInscrieri(Participant participant) {
        return inscriereRepository.getInscrieri(participant);
    }
}
