package ctrl;

import domain.Inscriere;
import domain.Participant;
import domain.Proba;
import domain.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import services.IService;
import services.Observer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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

    private IService service;
    private User loggedUser;

    public void setService(IService service) throws Exception {
        this.service = service;
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
        List<Proba> results = service.findAllProbe();

        probe.getItems().clear();
        for (Proba p : results) {
            probe.getItems().add(new ProbaDTO(p.getId(), p.getStil(), p.getDistanta(), service.nrParticipanti(p)));
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
                List<Proba> results = service.findAllProbe();
                probe.getItems().clear();
                for (Proba p : results) {
                    probe.getItems().add(new ProbaDTO(p.getId(), p.getStil(), p.getDistanta(), service.nrParticipanti(p)));
                }
                if (!comboProbe.getSelectionModel().isEmpty()) {
                    List<Participant> p = service.getInscrisi((Proba) comboProbe.getSelectionModel().getSelectedItem());
                    participanti.getItems().clear();
                    for (Participant participant : p) {
                        participanti.getItems().add(new ParticipantDTO(participant.getNume(), participant.getVarsta(), service.getInscrieri(participant)));
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
            service.login(loggedUser, this);
            load();
            username.setVisible(false);
            password.setVisible(false);
            labelUsername.setVisible(false);
            labelPassword.setVisible(false);
            button.setVisible(false);
            tabs.setVisible(true);
            logout.setVisible(true);
        } catch (Exception ex) {
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
        service.logout(loggedUser);
    }

    @FXML
    public void comboSelectionHandler() throws Exception {
        List<Participant> results = service.getInscrisi((Proba) comboProbe.getSelectionModel().getSelectedItem());
        participanti.getItems().clear();
        for (Participant participant : results) {
            participanti.getItems().add(new ParticipantDTO(participant.getNume(), participant.getVarsta(), service.getInscrieri(participant)));
        }
    }

    @FXML
    public void registerHandler() {
        try {
            Participant participant = new Participant(nume.getText(), Integer.parseInt(varsta.getText()));
            participant = service.addParticipant(participant);
            if (choiceProbe.getSelectionModel().getSelectedItems().size() == 0) {
                throw new Exception("Selection empty");
            }
            for (Proba proba : choiceProbe.getSelectionModel().getSelectedItems()) {
                service.addInscriere(new Inscriere(participant.getId(), proba.getId()));
            }
        } catch (Exception ex) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText(ex.getMessage());
            errorAlert.showAndWait();
        }
    }
}
