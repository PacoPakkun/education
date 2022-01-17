package srv;

import domain.Inscriere;
import domain.Participant;
import domain.Proba;
import domain.User;
import io.grpc.stub.StreamObserver;
import repo.intf.InscriereRepository;
import repo.intf.ParticipantRepository;
import repo.intf.ProbaRepository;
import repo.intf.UserRepository;
import services.IService;
import services.Observer;
import swim.SwimProtocol;
import swim.SwimServiceGrpc;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Service extends SwimServiceGrpc.SwimServiceImplBase {

    private ParticipantRepository participantRepository;
    private ProbaRepository probaRepository;
    private InscriereRepository inscriereRepository;
    private UserRepository userRepository;
    private Map<String, StreamObserver<SwimProtocol.SwimResponse>> loggedUsers;
    private final int defaultThreadsNo = 5;

    public Service(ParticipantRepository participantRepository, ProbaRepository probaRepository, InscriereRepository inscriereRepository, UserRepository userRepository) {
        this.participantRepository = participantRepository;
        this.probaRepository = probaRepository;
        this.inscriereRepository = inscriereRepository;
        this.userRepository = userRepository;
        loggedUsers = new ConcurrentHashMap<>();
        System.out.println("Server active..\nWaiting for clients..\n");
    }

    public void login(SwimProtocol.SwimRequest req, StreamObserver<SwimProtocol.SwimResponse> responseObserver) {
        SwimProtocol.User user = req.getUser();
        User usr = null;
        for (User u : userRepository.findAll()) {
            if (u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())) {
                usr = u;
            }
        }
        if (usr == null) {
            System.out.println("Invalid login!");
        } else {
            loggedUsers.put(usr.getUsername(), responseObserver);
        }
        System.out.println("User " + user.getUsername() + " logged in..");

//        SwimProtocol.SwimResponse reply = SwimProtocol.SwimResponse.newBuilder().build();
//        responseObserver.onNext(reply);
    }

    public void logout(SwimProtocol.SwimRequest req, StreamObserver<SwimProtocol.SwimResponse> responseObserver) {
        SwimProtocol.User user = req.getUser();
        loggedUsers.get(user.getUsername()).onCompleted();
        loggedUsers.remove(user.getUsername());
        responseObserver.onCompleted();
        System.out.println("User " + user.getUsername() + " logged out..");
    }

    public void findAllProbe(SwimProtocol.Null n, StreamObserver<SwimProtocol.SwimResponse> responseObserver) {
        System.out.println("Find All Probe requested..");
        SwimProtocol.SwimResponse.Builder reply = SwimProtocol.SwimResponse.newBuilder().setType(SwimProtocol.SwimResponse.Type.ProbeResponse);
        for (Proba p : probaRepository.findAll()) {
            SwimProtocol.Proba proba = SwimProtocol.Proba.newBuilder().setId(p.getId()).setDistanta(p.getDistanta()).setStil(p.getStil()).build();
            reply.addProbe(proba);
        }
        responseObserver.onNext(reply.build());
        responseObserver.onCompleted();
    }

    public void addParticipant(SwimProtocol.SwimRequest req, StreamObserver<SwimProtocol.SwimResponse> responseObserver) {
        try {
            System.out.println("Add Participant requested..");

            SwimProtocol.Participant participant = req.getParticipant();
            Participant p = participantRepository.add(new Participant(participant.getNume(), participant.getVarsta()));
            participant = SwimProtocol.Participant.newBuilder().setId(p.getId()).setNume(p.getNume()).setVarsta(p.getVarsta()).build();
            SwimProtocol.SwimResponse reply = SwimProtocol.SwimResponse.newBuilder().setParticipant(participant).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addInscriere(SwimProtocol.SwimRequest req, StreamObserver<SwimProtocol.SwimResponse> responseObserver) {
        try {
            System.out.println("Add Inscriere requested..");
            SwimProtocol.Inscriere inscriere = req.getInscriere();
            Inscriere i = inscriereRepository.add(new Inscriere(inscriere.getIdParticipant(), inscriere.getIdProba()));
            ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);
            for (StreamObserver<SwimProtocol.SwimResponse> client : loggedUsers.values()) {
                if (client != null)
                    executor.execute(() -> {
                        try {
                            System.out.println("Notifying client " + client);
//                            client.update(i);
                            SwimProtocol.SwimResponse reply = SwimProtocol.SwimResponse.newBuilder().setUpdate(true).build();
                            client.onNext(reply);
                        } catch (Exception e) {
                            System.err.println(e);
                        }
                    });
            }
            executor.shutdown();
            SwimProtocol.SwimResponse reply = SwimProtocol.SwimResponse.newBuilder().build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void nrParticipanti(SwimProtocol.SwimRequest req, StreamObserver<SwimProtocol.SwimResponse> responseObserver) {
        System.out.println("Nr participanti requested..");
        SwimProtocol.Proba proba = req.getProba();
        int nr = inscriereRepository.nrParticipanti(new Proba(proba.getId(), proba.getDistanta(), proba.getStil()));

        SwimProtocol.SwimResponse reply = SwimProtocol.SwimResponse.newBuilder().setNr(nr).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    public void getInscrisi(SwimProtocol.SwimRequest req, StreamObserver<SwimProtocol.SwimResponse> responseObserver) {
        System.out.println("Get inscrisi requested..");
        SwimProtocol.Proba proba = req.getProba();
        List<Participant> participanti = inscriereRepository.getInscrisi(new Proba(proba.getId(), proba.getDistanta(), proba.getStil()));

        SwimProtocol.SwimResponse.Builder reply = SwimProtocol.SwimResponse.newBuilder().setType(SwimProtocol.SwimResponse.Type.ParticipantiResponse);
        for (Participant p : participanti) {
            SwimProtocol.Participant participant = SwimProtocol.Participant.newBuilder().setId(p.getId()).setNume(p.getNume()).setVarsta(p.getVarsta()).build();
            reply.addParticipanti(participant);
        }
        responseObserver.onNext(reply.build());
        responseObserver.onCompleted();
    }

    public void getInscrieri(SwimProtocol.SwimRequest req, StreamObserver<SwimProtocol.SwimResponse> responseObserver) {
        System.out.println("Get inscrieri requested..");
        SwimProtocol.Participant participant = req.getParticipant();
        List<Proba> probe = inscriereRepository.getInscrieri(new Participant(participant.getId(), participant.getNume(), participant.getVarsta()));

        SwimProtocol.SwimResponse.Builder reply = SwimProtocol.SwimResponse.newBuilder().setType(SwimProtocol.SwimResponse.Type.ProbeResponse);
        for (Proba p : probe) {
            SwimProtocol.Proba proba = SwimProtocol.Proba.newBuilder().setId(p.getId()).setStil(p.getStil()).setDistanta(p.getDistanta()).build();
            reply.addProbe(proba);
        }
        responseObserver.onNext(reply.build());
        responseObserver.onCompleted();
    }
}
