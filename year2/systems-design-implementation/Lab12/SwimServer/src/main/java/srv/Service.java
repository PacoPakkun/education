package srv;

import domain.Inscriere;
import domain.Participant;
import domain.Proba;
import domain.User;
import repo.intf.InscriereRepository;
import repo.intf.ParticipantRepository;
import repo.intf.ProbaRepository;
import repo.intf.UserRepository;
import services.IService;
import services.Observer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service implements IService {

    private ParticipantRepository participantRepository;
    private ProbaRepository probaRepository;
    private InscriereRepository inscriereRepository;
    private UserRepository userRepository;
    private Map<String, User> loggedUsers;
    private NotificationService notificationService;
    private final int defaultThreadsNo = 5;

    public Service(ParticipantRepository participantRepository, ProbaRepository probaRepository, InscriereRepository inscriereRepository, UserRepository userRepository, NotificationService notificationService) {
        this.participantRepository = participantRepository;
        this.probaRepository = probaRepository;
        this.inscriereRepository = inscriereRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        loggedUsers = new ConcurrentHashMap<>();
        System.out.println("Server active..\nWaiting for clients..\n");
    }

    public void login(User user) throws Exception {
        User usr = null;
        for (User u : userRepository.findAll()) {
            if (u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())) {
                usr = u;
            }
        }
        if (usr == null) {
            throw new Exception("Invalid login!");
        } else {
            loggedUsers.put(usr.getUsername(), usr);
        }
        System.out.println("User " + user.getUsername() + " logged in..");
    }

    public void logout(User user) {
        loggedUsers.remove(user.getUsername());
        System.out.println("User " + user.getUsername() + " logged out..");
    }

    public List<Proba> findAllProbe() {
        System.out.println("Find All Probe requested..");
        return probaRepository.findAll();
    }

    public Participant addParticipant(Participant participant) throws Exception {
        System.out.println("Add Participant requested..");
        return participantRepository.add(participant);
    }

    public synchronized Inscriere addInscriere(Inscriere inscriere) throws Exception {
        System.out.println("Add Inscriere requested..");
        Inscriere i = inscriereRepository.add(inscriere);
//        notificationService.update();

        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);
        executor.execute(() -> {
            notificationService.update();
        });

        executor.shutdown();
        return i;
    }

    public int nrParticipanti(Proba proba) {
        System.out.println("Nr participanti requested..");
        return inscriereRepository.nrParticipanti(proba);
    }

    public List<Participant> getInscrisi(Proba proba) {
        System.out.println("Get inscrisi requested..");
        return inscriereRepository.getInscrisi(proba);
    }

    public List<Proba> getInscrieri(Participant participant) {
        System.out.println("Get inscrieri requested..");
        return inscriereRepository.getInscrieri(participant);
    }
}
