package socialnetwork.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import socialnetwork.domain.Eveniment;
import socialnetwork.domain.Message;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.service.UtilizatorService;
import socialnetwork.utils.ChangeEvent;
import socialnetwork.utils.Observer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class EventController implements Observer<ChangeEvent> {
    UtilizatorService service;
    Stage stage;
    Utilizator user;

    @FXML
    Label label;
    @FXML
    TextField title;
    @FXML
    TextArea info;
    @FXML
    DatePicker date;

    @Override
    public void update(ChangeEvent messageTaskChangeEvent) {
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
    }

    public void backHandler() throws IOException {
        stage.close();
    }

    public void createHandler() {
        try {
            if (date.getValue() == null)
                throw new ValidationException("Data invalida");
            Eveniment m = new Eveniment(user, title.getText(), info.getText(), Date.valueOf(date.getValue()));
            m.setId(service.getNextEventId());
            service.addEvent(m);
            stage.close();
        } catch (ValidationException ex) {
            MessageAlert.showErrorMessage(null, ex.getMessage());
        }
    }
}
