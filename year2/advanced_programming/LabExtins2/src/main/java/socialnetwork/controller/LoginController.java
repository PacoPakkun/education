package socialnetwork.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import socialnetwork.service.UtilizatorService;

import java.io.IOException;

public class LoginController {
    UtilizatorService service;
    Stage stage;

    @FXML
    TextField username;

    @FXML
    TextField password;

    @FXML
    CheckBox remember;

    public void setService(UtilizatorService service) {
        this.service = service;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void loginHandler() throws IOException {
        if (username.getText().equals("admin") && password.getText().equals("admin")) {
            MessageAlert.showMessage(null, null, null, "Logged in as admin!");
        } else if (service.findByName(username.getText()).isPresent()) {
            if (remember.isSelected()) {
                if (!service.lastLogin().isPresent() || (service.lastLogin().isPresent() && !service.lastLogin().get().getLastName().equals(username.getText()))) {
                    service.setLogin(service.findByName(username.getText()).get());
                }
            } else {
                service.setLogin(null);
            }
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/mainView.fxml"));
            AnchorPane layout = loader.load();
            stage.setScene(new Scene(layout));
            stage.setWidth(430);
            MainController controller = loader.getController();
            controller.setService(service);
            controller.setUser(username.getText());
            controller.setStage(stage);
        } else {
            MessageAlert.showErrorMessage(null, "Incorrect username or password!");
        }
    }

}
