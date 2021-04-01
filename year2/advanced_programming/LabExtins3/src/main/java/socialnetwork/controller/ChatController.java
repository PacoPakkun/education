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
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import socialnetwork.domain.Message;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.UtilizatorService;
import socialnetwork.utils.ChangeEvent;
import socialnetwork.utils.Observer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ChatController implements Observer<ChangeEvent> {
    UtilizatorService service;
    Stage stage;
    Utilizator user;
    Utilizator friend;
    List<Message> mesaje;

    @FXML
    Label label;
    @FXML
    Label label2;
    @FXML
    AnchorPane chat;
    @FXML
    TextField text;
    @FXML
    DatePicker from;
    @FXML
    DatePicker to;

    @Override
    public void update(ChangeEvent messageTaskChangeEvent) {
        initModel();
    }

    public void setService(UtilizatorService service) {
        this.service = service;
        this.service.addObserver(this);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUser(String username, String friendname) {
        this.user = service.findByName(username).get();
        this.friend = service.findByName(friendname).get();
        label.setText("Logged in as " + user.getLastName() + " " + user.getFirstName());
        label2.setText(friend.getLastName() + " " + friend.getFirstName());
        initModel();
    }

    public void initModel() {
        Iterable<Message> msg = service.getConversation(user.getId(), friend.getId());
        mesaje = StreamSupport.stream(msg.spliterator(), false)
                .collect(Collectors.toList());
        Collections.reverse(mesaje);
        chat.getChildren().clear();
        double pos = 400;
        for (int i = 0; i < mesaje.size(); i++) {
            Message m = mesaje.get(i);
            Rectangle r = new Rectangle(100, 20, Paint.valueOf("white"));
            r.setArcWidth(20);
            r.setArcHeight(20);
            r.setLayoutY(pos - 2);
            Label l = new Label();
            l.setText(m.getMessage());
            l.setLayoutY(pos);
            pos -= 25;
            if (m.getFrom().equals(user)) {
                r.setLayoutX(275);
                l.setLayoutX(285);
                r.setStyle("-fx-fill: #39806e");
                l.setStyle("-fx-text-fill: white");
            } else {
                r.setLayoutX(15);
                l.setLayoutX(25);
            }
            chat.getChildren().add(r);
            chat.getChildren().add(l);
        }
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

    public void backHandler() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/mainView.fxml"));
        AnchorPane layout = loader.load();
        stage.setScene(new Scene(layout));
        stage.setWidth(430);
        MainController controller = loader.getController();
        controller.setService(service);
        controller.setUser(user.getLastName());
        controller.setStage(stage);
    }

    public void sendHandler() {
        Iterable<Message> msg = service.getAllMessages();
        List<Message> m = StreamSupport.stream(msg.spliterator(), false)
                .collect(Collectors.toList());
        if (m.size() == 0)
            service.sendMessage(new Message((long) 1, user, friend, text.getText()));
        else
            service.sendMessage(new Message(m.get(m.size() - 1).getId() + 1, user, friend, text.getText()));
        text.setText("");
    }

    public void downloadHandler() throws FileNotFoundException, DocumentException {
        if (from.getValue() != null && to.getValue() != null) {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("D:\\UBB\\MAP\\LabExtins2\\data\\chat.pdf"));
            document.open();
            document.addTitle("Received messages by " + friend.getLastName() + " " + friend.getFirstName() + " from " + from.getValue().toString() + " to " + to.getValue().toString());
            Paragraph text = new Paragraph();
            text.add(new Paragraph("Received messages by " + friend.getLastName() + " " + friend.getFirstName() + " from " + from.getValue().toString() + " to " + to.getValue().toString()));
            text.add(new Paragraph("\n"));

            List<Message> mmmm = StreamSupport.stream(mesaje.spliterator(), false)
                    .filter(m ->
                            m.getFrom().getId() == friend.getId() && m.getData().toLocalDate().isAfter(from.getValue().minusDays(1)) && m.getData().toLocalDate().isBefore(to.getValue().plusDays(1)))
                    .collect(Collectors.toList());

            for (Message message : mmmm) {
                text.add(new Paragraph(message.toString()));
            }
            document.add(text);
            document.close();

            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"","Conversation downloaded!");
        }
    }
}
