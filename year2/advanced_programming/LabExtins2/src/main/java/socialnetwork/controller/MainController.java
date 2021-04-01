package socialnetwork.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import socialnetwork.domain.*;
import socialnetwork.service.UtilizatorService;
import socialnetwork.utils.ChangeEvent;
import socialnetwork.utils.Observer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
    Button chat;
    @FXML
    Button remove;
    @FXML
    TableView<Request> requests;
    @FXML
    TableColumn<Request, String> numeRequest;
    @FXML
    TableColumn<Request, String> prenumeRequest;
    @FXML
    TableColumn<Request, String> statusRequest;
    @FXML
    Button accept;
    @FXML
    Button decline;
    @FXML
    TableView<Utilizator> people;
    @FXML
    TableColumn<Utilizator, String> numePeople;
    @FXML
    TableColumn<Utilizator, String> prenumePeople;
    @FXML
    Button add;
    @FXML
    Button cancel;
    @FXML
    LineChart<Number, Number> chart;
    @FXML
    DatePicker from;
    @FXML
    DatePicker to;

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
        stage.setWidth(430);
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
            accept.setDisable(true);
            decline.setDisable(true);
            //service.removeRequest(r.getId());
        } else {
            MessageAlert.showErrorMessage(null, "Nu s-a selectat nimic.");
        }
    }

    public void declineHandler() {
        Request r = requests.getSelectionModel().getSelectedItem();
        if (r != null) {
            service.declineRequest(r);
            accept.setDisable(true);
            decline.setDisable(true);
            //service.removeRequest(r.getId());
        } else {
            MessageAlert.showErrorMessage(null, "Nu s-a selectat nimic.");
        }
    }

    public void addHandler() {
        Utilizator u = people.getSelectionModel().getSelectedItem();
        if (u != null) {
            service.addRequest(new Request(new Tuple<Long, Long>(user.getId(), u.getId()), "pending"));
            add.setDisable(true);
        } else {
            MessageAlert.showErrorMessage(null, "Nu s-a selectat nimic.");
        }
    }

    public void checkPeopleHandler() {
        Utilizator u = people.getSelectionModel().getSelectedItem();
        Optional<Request> req = service.findRequest(new Tuple<Long, Long>(user.getId(), u.getId()));
        if (u != null) {
            if (req.isPresent() && !req.get().getStatus().equals("declined")) {
                add.setDisable(true);
                if (req.get().getId().getLeft() == user.getId())
                    cancel.setDisable(false);
                else
                    cancel.setDisable(true);
            } else {
                add.setDisable(false);
                cancel.setDisable(true);
            }
        } else {
            add.setDisable(false);
            cancel.setDisable(true);
        }
    }


    public void checkRequestHandler() {
        Request r = requests.getSelectionModel().getSelectedItem();
        if (r != null) {
            if (!r.getStatus().equals("pending")) {
                accept.setDisable(true);
                decline.setDisable(true);
            } else {
                accept.setDisable(false);
                decline.setDisable(false);
            }
        }
    }

    public void checkFriendsHandler() {
        Utilizator u = friends.getSelectionModel().getSelectedItem();
        if (u == null) {
            chat.setDisable(true);
            remove.setDisable(true);
        } else {
            chat.setDisable(false);
            remove.setDisable(false);
        }
    }

    public void chatHandler() throws IOException {
        Utilizator u = friends.getSelectionModel().getSelectedItem();
        if (u != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/chatView.fxml"));
            AnchorPane layout = loader.load();
            stage.setScene(new Scene(layout));
            stage.setWidth(430);
            ChatController controller = loader.getController();
            controller.setService(service);
            controller.setUser(user.getLastName(), u.getLastName());
            controller.setStage(stage);
        } else {
            MessageAlert.showErrorMessage(null, "Nu s-a selectat nimic.");
        }
    }

    public void cancelHandler() {
        Utilizator u = people.getSelectionModel().getSelectedItem();
        if (u != null) {
            service.removeRequest(new Tuple<Long, Long>(user.getId(), u.getId()));
            add.setDisable(false);
            cancel.setDisable(true);
        } else {
            MessageAlert.showErrorMessage(null, "Nu s-a selectat nimic.");
        }
    }

    public void chartHandler() {
        if (from.getValue() != null && to.getValue() != null) {
            chart.getData().clear();
            XYChart.Series fseries = new XYChart.Series();
            fseries.setName("Friendships");
            XYChart.Series mseries = new XYChart.Series();
            mseries.setName("Messages");
            Iterable<Prietenie> list = service.getAllFriendships();
            List<Prietenie> prietenii = StreamSupport.stream(list.spliterator(), false)
                    .filter(p ->
                            (p.getId().getLeft() == user.getId() || p.getId().getRight() == user.getId()) && p.getDate().toLocalDate().isAfter(from.getValue().minusDays(1)) && p.getDate().toLocalDate().isBefore(to.getValue().plusDays(1)))
                    .collect(Collectors.toList());
            Iterable<Message> list2 = service.getAllMessages();
            List<Message> mesaje = StreamSupport.stream(list2.spliterator(), false)
                    .filter(m ->
                            (m.getFrom().getId() == user.getId() || StreamSupport.stream(m.getTo().spliterator(), false).filter(u -> u.getId() == user.getId()).collect(Collectors.toList()).size() != 0) && m.getData().toLocalDate().isAfter(from.getValue().minusDays(1)) && m.getData().toLocalDate().isBefore(to.getValue().plusDays(1)))
                    .collect(Collectors.toList());
            for (LocalDate date = from.getValue(); date.isBefore(to.getValue().plusDays(1)); date = date.plusDays(1)) {
                int fcount = 0, mcount = 0;
                for (Prietenie p : prietenii) {
                    if (p.getDate().toLocalDate().equals(date)) {
                        fcount++;
                    }
                }
                for (Message m : mesaje) {
                    if (m.getData().toLocalDate().equals(date)) {
                        mcount++;
                    }
                }
                fseries.getData().add(new XYChart.Data(date.toString(), fcount));
                mseries.getData().add(new XYChart.Data(date.toString(), mcount));
            }
            chart.getData().add(fseries);
            chart.getData().add(mseries);
        }
    }

    public void downloadHandler() throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("D:\\UBB\\MAP\\LabExtins2\\data\\activity.pdf"));
        document.open();
        document.addTitle("Activity of " + user.getLastName() + " " + user.getFirstName() + " from " + from.getValue().toString() + " to " + to.getValue().toString());
        Paragraph text = new Paragraph();
        text.add(new Paragraph("Activity of " + user.getLastName() + " " + user.getFirstName() + " from " + from.getValue().toString() + " to " + to.getValue().toString()));
        text.add(new Paragraph("\n"));

        Iterable<Prietenie> list = service.getAllFriendships();
        List<Prietenie> prietenii = StreamSupport.stream(list.spliterator(), false)
                .filter(p ->
                        (p.getId().getLeft() == user.getId() || p.getId().getRight() == user.getId()) && p.getDate().toLocalDate().isAfter(from.getValue().minusDays(1)) && p.getDate().toLocalDate().isBefore(to.getValue().plusDays(1)))
                .collect(Collectors.toList());
        Iterable<Message> list2 = service.getAllMessages();
        List<Message> mesaje = StreamSupport.stream(list2.spliterator(), false)
                .filter(m ->
                        (m.getFrom().getId() == user.getId() || StreamSupport.stream(m.getTo().spliterator(), false).filter(u -> u.getId() == user.getId()).collect(Collectors.toList()).size() != 0) && m.getData().toLocalDate().isAfter(from.getValue().minusDays(1)) && m.getData().toLocalDate().isBefore(to.getValue().plusDays(1)))
                .collect(Collectors.toList());

        text.add(new Paragraph("Friendships:\n"));
        for (Prietenie prietenie : prietenii) {
            text.add(new Paragraph(prietenie.toString()));
        }
        text.add(new Paragraph("\n"));
        text.add(new Paragraph("Messages:\n"));
        for (Message message : mesaje) {
            text.add(new Paragraph(message.toString()));
        }
        document.add(text);
        document.close();

        MessageAlert.showErrorMessage(null, "Activity downloaded!");
        //MessageAlert.showMessage(null,null,null,"Activity downloaded!");
    }
}
