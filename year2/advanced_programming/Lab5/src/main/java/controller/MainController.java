package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Request;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.UtilizatorService;
import socialnetwork.utils.ChangeEvent;
import socialnetwork.utils.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainController implements Observer<ChangeEvent> {
    UtilizatorService service;
    Stage stage;
    Utilizator user;
    ObservableList<Utilizator> friendsmodel = FXCollections.observableArrayList();
    ObservableList<Request> requestmodel = FXCollections.observableArrayList();
    ObservableList<Utilizator> usermodel = FXCollections.observableArrayList();

    @FXML
    Label label;
    @FXML
    TableView<Utilizator> friends;
    @FXML
    TableColumn<Utilizator, String> numeFriend;
    @FXML
    TableColumn<Utilizator, String> prenumeFriend;
    @FXML
    TableView<Request> requests;
    @FXML
    TableColumn<Request, String> numeRequest;
    @FXML
    TableColumn<Request, String> prenumeRequest;
    @FXML
    TableColumn<Request, String> statusRequest;
    @FXML
    TableView<Utilizator> people;
    @FXML
    TableColumn<Utilizator, String> numePeople;
    @FXML
    TableColumn<Utilizator, String> prenumePeople;
    @FXML
    Button add;

    @Override
    public void update(ChangeEvent messageTaskChangeEvent) {
        initFriendModel();
        numeFriend.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("firstName"));
        prenumeFriend.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("lastName"));
        friends.setItems(friendsmodel);
        initRequestModel();
        numeRequest.setCellValueFactory(new PropertyValueFactory<Request, String>("nume1"));
        prenumeRequest.setCellValueFactory(new PropertyValueFactory<Request, String>("prenume1"));
        statusRequest.setCellValueFactory(new PropertyValueFactory<Request, String>("status"));
        requests.setItems(requestmodel);
        initUserModel();
        numePeople.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("firstName"));
        prenumePeople.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("lastName"));
        people.setItems(usermodel);
    }

    public void setService(UtilizatorService service) {
        this.service = service;
        this.service.addObserver(this);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUser(String username) {
        this.user = service.findByName(username).get();
        label.setText("Logged in as " + user.getLastName() + " " + user.getFirstName());
        initFriendModel();
        initRequestModel();
        initUserModel();
    }

    private void initFriendModel() {
        Iterable<Utilizator> prieteni = service.findPrieteni(user.getId());
        List<Utilizator> list = StreamSupport.stream(prieteni.spliterator(), false)
                .collect(Collectors.toList());
        friendsmodel.setAll(list);
    }

    private void initRequestModel() {
        Iterable<Request> req = service.getAllRequests();
        List<Request> list = StreamSupport.stream(req.spliterator(), false)
                .map(r -> {
                    r.setNume1(service.findUtilizator(r.getId().getLeft()).get().getFirstName());
                    r.setNume2(service.findUtilizator(r.getId().getRight()).get().getFirstName());
                    r.setPrenume1(service.findUtilizator(r.getId().getLeft()).get().getLastName());
                    r.setPrenume2(service.findUtilizator(r.getId().getRight()).get().getLastName());
                    return r;
                })
                .filter(r ->
                        r.getId().getRight() == user.getId())
                .collect(Collectors.toList());
        requestmodel.setAll(list);
    }

    private void initUserModel() {
        Iterable<Utilizator> users = service.getAllUsers();
        List<Utilizator> list = StreamSupport.stream(users.spliterator(), false)
                .filter(u ->
                        u.getId() != user.getId() && !service.findPrietenie(new Tuple<Long, Long>(user.getId(), u.getId())).isPresent())
                .collect(Collectors.toList());
        usermodel.setAll(list);
    }

    @FXML
    public void initialize() {
        numeFriend.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("firstName"));
        prenumeFriend.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("lastName"));
        friends.setItems(friendsmodel);
        numeRequest.setCellValueFactory(new PropertyValueFactory<Request, String>("nume1"));
        prenumeRequest.setCellValueFactory(new PropertyValueFactory<Request, String>("prenume1"));
        statusRequest.setCellValueFactory(new PropertyValueFactory<Request, String>("status"));
        requests.setItems(requestmodel);
        numePeople.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("firstName"));
        prenumePeople.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("lastName"));
        people.setItems(usermodel);
    }

    public void logoutHandler() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/loginView.fxml"));
        AnchorPane layout = loader.load();
        stage.setScene(new Scene(layout));
        LoginController controller = loader.getController();
        controller.setService(service);
        controller.setStage(stage);
        stage.setWidth(600);
    }

    public void removeHandler() {
        Utilizator u = friends.getSelectionModel().getSelectedItem();
        if (u != null) {
            service.deletePrietenie(new Tuple<>(u.getId(), user.getId()));
        } else {
            MessageAlert.showErrorMessage(null, "Nu s-a selectat nimic.");
        }
    }

    public void acceptHandler() {
        Request r = requests.getSelectionModel().getSelectedItem();
        if (r != null) {
            service.addRequest(r);
        } else {
            MessageAlert.showErrorMessage(null, "Nu s-a selectat nimic.");
        }
    }

    public void declineHandler() {
        Request r = requests.getSelectionModel().getSelectedItem();
        if (r != null) {
            service.declineRequest(r);
        } else {
            MessageAlert.showErrorMessage(null, "Nu s-a selectat nimic.");
        }
    }

    public void addHandler() {
        Utilizator u = people.getSelectionModel().getSelectedItem();
        if (u != null) {
            service.addRequest(new Request(new Tuple<Long, Long>(user.getId(), u.getId()), "pending"));
        } else {
            MessageAlert.showErrorMessage(null, "Nu s-a selectat nimic.");
        }
    }

    public void checkRequestHandler() {
        Utilizator u = people.getSelectionModel().getSelectedItem();
        if (u != null) {
            if (service.findRequest(new Tuple<Long, Long>(user.getId(), u.getId())).isPresent())
                add.setDisable(true);
            else
                add.setDisable(false);
        }
    }
}
