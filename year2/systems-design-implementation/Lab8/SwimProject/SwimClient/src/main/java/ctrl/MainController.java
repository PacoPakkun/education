package ctrl;

import domain.Inscriere;
import domain.Participant;
import domain.Proba;
import domain.User;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import services.Observer;
import swim.SwimProtocol;
import swim.SwimServiceGrpc;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainController extends UnicastRemoteObject implements Observer, Serializable {

    public MainController() throws RemoteException {
    }

    public class ParticipantDTO {
        private String nume;
        private int varsta;
        private List<Proba> inscrieri;

        public ParticipantDTO(String nume, int varsta, List<Proba> inscrieri) {
            this.nume = nume;
            this.varsta = varsta;
            this.inscrieri = inscrieri;
        }

        public String getNume() {
            return nume;
        }

        public int getVarsta() {
            return varsta;
        }

        public List<Proba> getInscrieri() {
            return inscrieri;
        }

        public void setNume(String nume) {
            this.nume = nume;
        }

        public void setVarsta(int varsta) {
            this.varsta = varsta;
        }

        public void setInscrieri(List<Proba> inscrieri) {
            this.inscrieri = inscrieri;
        }
    }

    public class ProbaDTO {
        private int id;
        private String stil;
        private int distanta;
        private int nr;

        public ProbaDTO(int id, String stil, int distanta, int nr) {
            this.id = id;
            this.stil = stil;
            this.distanta = distanta;
            this.nr = nr;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStil() {
            return stil;
        }

        public void setStil(String stil) {
            this.stil = stil;
        }

        public int getDistanta() {
            return distanta;
        }

        public void setDistanta(int distanta) {
            this.distanta = distanta;
        }

        public int getNr() {
            return nr;
        }

        public void setNr(int nr) {
            this.nr = nr;
        }
    }

    private SwimServiceGrpc.SwimServiceBlockingStub service;
    private SwimServiceGrpc.SwimServiceStub asyncService;
    private User loggedUser;

    public void setService(SwimServiceGrpc.SwimServiceBlockingStub service, SwimServiceGrpc.SwimServiceStub asyncService) throws Exception {
        this.service = service;
        this.asyncService = asyncService;
    }

    @FXML
    TabPane tabs;
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Label labelUsername, labelPassword;
    @FXML
    Button button, logout;

    @FXML
    TableView<ProbaDTO> probe;
    @FXML
    ComboBox comboProbe;

    @FXML
    TableView<ParticipantDTO> participanti;

    @FXML
    TextField nume, varsta;
    @FXML
    ListView<Proba> choiceProbe;

    @FXML
    public void initialize() {
    }

    @FXML
    public void load() throws Exception {
        SwimProtocol.Null request = SwimProtocol.Null.newBuilder().build();
        SwimProtocol.SwimResponse response;
        try {
            response = service.findAllProbe(request);
        } catch (StatusRuntimeException e) {
            return;
        }
        List<Proba> results = new ArrayList<>();
        for (SwimProtocol.Proba p : response.getProbeList()) {
            Proba proba = new Proba(p.getId(), p.getDistanta(), p.getStil());
            results.add(proba);
        }

        probe.getItems().clear();
        for (Proba p : results) {
            SwimProtocol.Proba proba = SwimProtocol.Proba.newBuilder().setId(p.getId()).setStil(p.getStil()).setDistanta(p.getDistanta()).build();
            SwimProtocol.SwimRequest request2 = SwimProtocol.SwimRequest.newBuilder().setProba(proba).build();
            try {
                response = service.nrParticipanti(request2);
            } catch (StatusRuntimeException e) {
                return;
            }
            probe.getItems().add(new ProbaDTO(p.getId(), p.getStil(), p.getDistanta(), response.getNr()));
        }

        comboProbe.setItems(null);
        ObservableList<Proba> model = FXCollections.observableArrayList();
        model.setAll(results);
        comboProbe.setItems(model);

        choiceProbe.getItems().clear();
        choiceProbe.getItems().addAll(results);
        choiceProbe.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void update(Inscriere inscriere) throws Exception {
        Platform.runLater(() -> {
            try {
                SwimProtocol.Null request = SwimProtocol.Null.newBuilder().build();
                SwimProtocol.SwimResponse response;
                try {
                    response = service.findAllProbe(request);
                } catch (StatusRuntimeException e) {
                    return;
                }
                List<Proba> results = new ArrayList<>();
                for (SwimProtocol.Proba p : response.getProbeList()) {
                    Proba proba = new Proba(p.getId(), p.getDistanta(), p.getStil());
                    results.add(proba);
                }

                probe.getItems().clear();
                for (Proba p : results) {
                    SwimProtocol.Proba proba = SwimProtocol.Proba.newBuilder().setId(p.getId()).setStil(p.getStil()).setDistanta(p.getDistanta()).build();
                    SwimProtocol.SwimRequest request2 = SwimProtocol.SwimRequest.newBuilder().setProba(proba).build();
                    try {
                        response = service.nrParticipanti(request2);
                    } catch (StatusRuntimeException e) {
                        return;
                    }
                    probe.getItems().add(new ProbaDTO(p.getId(), p.getStil(), p.getDistanta(), response.getNr()));
                }
                if (!comboProbe.getSelectionModel().isEmpty()) {
                    Proba pr = (Proba) comboProbe.getSelectionModel().getSelectedItem();
                    SwimProtocol.Proba proba = SwimProtocol.Proba.newBuilder().setId(pr.getId()).setDistanta(pr.getDistanta()).setStil(pr.getStil()).build();
                    SwimProtocol.SwimRequest request3 = SwimProtocol.SwimRequest.newBuilder().setProba(proba).build();
                    try {
                        response = service.getInscrisi(request3);
                    } catch (StatusRuntimeException e) {
                        return;
                    }
                    List<Participant> results2 = new ArrayList<>();
                    for (SwimProtocol.Participant p : response.getParticipantiList()) {
                        Participant participant = new Participant(p.getId(), p.getNume(), p.getVarsta());
                        results2.add(participant);
                    }
                    participanti.getItems().clear();
                    for (Participant participant : results2) {
                        SwimProtocol.Participant p = SwimProtocol.Participant.newBuilder().setId(participant.getId()).setNume(participant.getNume()).setVarsta(participant.getVarsta()).build();
                        SwimProtocol.SwimRequest request4 = SwimProtocol.SwimRequest.newBuilder().setParticipant(p).build();
                        try {
                            response = service.getInscrieri(request4);
                        } catch (StatusRuntimeException e) {
                            return;
                        }
                        List<Proba> results3 = new ArrayList<>();
                        for (SwimProtocol.Proba pro : response.getProbeList()) {
                            Proba proba1 = new Proba(pro.getId(), pro.getDistanta(), pro.getStil());
                            results3.add(proba1);
                        }

                        participanti.getItems().add(new ParticipantDTO(participant.getNume(), participant.getVarsta(), results3));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void loginHandler() {
        try {
            loggedUser = new User(username.getText(), password.getText());
            SwimProtocol.User user = SwimProtocol.User.newBuilder().setUsername(loggedUser.getUsername()).setPassword(loggedUser.getPassword()).build();
            SwimProtocol.SwimRequest request = SwimProtocol.SwimRequest.newBuilder().setUser(user).build();
            try {
                asyncService.login(request, new StreamObserver<SwimProtocol.SwimResponse>() {
                    @Override
                    public void onNext(SwimProtocol.SwimResponse value) {
                        try {
                            System.out.println("update called");
                            update(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println("stream error");
                        System.out.println(t.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("stream ended");
                    }
                });

            } catch (StatusRuntimeException e) {
                System.out.println(e.getStatus());
            }

            load();
            username.setVisible(false);
            password.setVisible(false);
            labelUsername.setVisible(false);
            labelPassword.setVisible(false);
            button.setVisible(false);
            tabs.setVisible(true);
            logout.setVisible(true);
        } catch (
                Exception ex) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("ERROR");
            errorAlert.setContentText(ex.getMessage());
            errorAlert.showAndWait();
        }

    }

    @FXML
    public void logoutHandler() throws Exception {
        username.setVisible(true);
        password.setVisible(true);
        labelUsername.setVisible(true);
        labelPassword.setVisible(true);
        button.setVisible(true);
        tabs.setVisible(false);
        logout.setVisible(false);
        SwimProtocol.User user = SwimProtocol.User.newBuilder().setUsername(loggedUser.getUsername()).setPassword(loggedUser.getPassword()).build();
        SwimProtocol.SwimRequest request = SwimProtocol.SwimRequest.newBuilder().setUser(user).build();
        SwimProtocol.SwimResponse response;
        try {
            response = service.logout(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void comboSelectionHandler() throws Exception {
        Proba pr = (Proba) comboProbe.getSelectionModel().getSelectedItem();
        SwimProtocol.Proba proba = SwimProtocol.Proba.newBuilder().setId(pr.getId()).setDistanta(pr.getDistanta()).setStil(pr.getStil()).build();
        SwimProtocol.SwimRequest request = SwimProtocol.SwimRequest.newBuilder().setProba(proba).build();
        SwimProtocol.SwimResponse response = service.getInscrisi(request);
        List<Participant> results = new ArrayList<>();
        for (SwimProtocol.Participant p : response.getParticipantiList()) {
            Participant participant = new Participant(p.getId(), p.getNume(), p.getVarsta());
            results.add(participant);
        }

        participanti.getItems().clear();
        for (Participant participant : results) {
            SwimProtocol.Participant p = SwimProtocol.Participant.newBuilder().setId(participant.getId()).setNume(participant.getNume()).setVarsta(participant.getVarsta()).build();
            SwimProtocol.SwimRequest request2 = SwimProtocol.SwimRequest.newBuilder().setParticipant(p).build();
            try {
                response = service.getInscrieri(request2);
            } catch (StatusRuntimeException e) {
                return;
            }
            List<Proba> results2 = new ArrayList<>();
            for (SwimProtocol.Proba pro : response.getProbeList()) {
                Proba proba1 = new Proba(pro.getId(), pro.getDistanta(), pro.getStil());
                results2.add(proba1);
            }
            participanti.getItems().add(new ParticipantDTO(participant.getNume(), participant.getVarsta(), results2));
        }
    }

    @FXML
    public void registerHandler() {
        try {
            SwimProtocol.Participant participant = SwimProtocol.Participant.newBuilder().setNume(nume.getText()).setVarsta(Integer.parseInt(varsta.getText())).build();
            SwimProtocol.SwimRequest req = SwimProtocol.SwimRequest.newBuilder().setParticipant(participant).build();
            SwimProtocol.SwimResponse response = service.addParticipant(req);

            if (choiceProbe.getSelectionModel().getSelectedItems().size() == 0) {
                throw new Exception("Selection empty");
            }
            for (Proba proba : choiceProbe.getSelectionModel().getSelectedItems()) {
                SwimProtocol.Inscriere i = SwimProtocol.Inscriere.newBuilder().setIdParticipant(response.getParticipant().getId()).setIdProba(proba.getId()).build();
                req = SwimProtocol.SwimRequest.newBuilder().setInscriere(i).build();
                service.addInscriere(req);
            }
        } catch (Exception ex) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText(ex.getMessage());
            errorAlert.showAndWait();
        }
    }
}
