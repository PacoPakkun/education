package socialnetwork.controller;

import com.google.common.hash.Hashing;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.service.UtilizatorService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginController {
    UtilizatorService service;
    Stage stage;

    @FXML
    TextField username;
    @FXML
    TextField password;
    @FXML
    CheckBox remember;
    @FXML
    Label userlabel;
    @FXML
    Label passlabel;
    @FXML
    Button login;
    @FXML
    Button signup;
    @FXML
    AnchorPane pane;

    public void setService(UtilizatorService service) {
        this.service = service;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void loginHandler() throws IOException, NoSuchAlgorithmException {
        String hash = Hashing.sha256()
                .hashString(password.getText(), StandardCharsets.UTF_8)
                .toString();
        if (username.getText().equals("admin") && password.getText().equals("admin")) {
            MessageAlert.showMessage(null, null, null, "Logged in as admin!");
        } else if (service.findByName(username.getText()).isPresent() && service.findByName(username.getText()).get().getHash().equals(hash)) {
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

    public void signupHandler() {
        username.setVisible(false);
        password.setVisible(false);
        remember.setVisible(false);
        userlabel.setVisible(false);
        passlabel.setVisible(false);
        login.setVisible(false);
        signup.setVisible(false);

        TextField nume = new TextField();
        nume.setStyle("-fx-background-radius: 5em;");
        nume.setPrefWidth(180);
        nume.setPrefHeight(26);
        nume.setLayoutX(117);
        nume.setLayoutY(220);
        Label numelabel = new Label("Nume:");
        numelabel.setStyle("-fx-text-fill: white; -fx-font-size: 16");
        numelabel.setLayoutX(180);
        numelabel.setLayoutY(190);
        TextField prenume = new TextField();
        prenume.setStyle("-fx-background-radius: 5em;");
        prenume.setPrefWidth(180);
        prenume.setPrefHeight(26);
        prenume.setLayoutX(117);
        prenume.setLayoutY(280);
        Label prenumelabel = new Label("Prenume:");
        prenumelabel.setStyle("-fx-text-fill: white; -fx-font-size: 16");
        prenumelabel.setLayoutX(174);
        prenumelabel.setLayoutY(250);
        PasswordField parola = new PasswordField();
        parola.setStyle("-fx-background-radius: 5em;");
        parola.setPrefWidth(180);
        parola.setPrefHeight(26);
        parola.setLayoutX(117);
        parola.setLayoutY(340);
        Label parolalabel = new Label("Parola:");
        parolalabel.setStyle("-fx-text-fill: white; -fx-font-size: 16");
        parolalabel.setLayoutX(182);
        parolalabel.setLayoutY(310);
        Label piclabel = new Label("");
        piclabel.setStyle("-fx-text-fill: white; -fx-font-size: 16");
        piclabel.setLayoutX(120);
        piclabel.setLayoutY(370);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("D:\\UBB\\MAP\\LabExtins3\\src\\main\\resources\\design"));
        Button pic = new Button("Choose Profile Pic");
        pic.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(stage);
            try {
                piclabel.setText("( " + selectedFile.getCanonicalPath().replace("D:\\UBB\\MAP\\LabExtins3\\src\\main\\resources\\", "") + " )");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        pic.setStyle("-fx-font-size: 14; -fx-background-radius: 5em;");
        pic.setPrefWidth(180);
        pic.setPrefHeight(30);
        pic.setLayoutX(117);
        pic.setLayoutY(400);
        Button create = new Button("Create Account");
        create.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: #449480; -fx-background-radius: 5em;");
        create.setPrefWidth(180);
        create.setPrefHeight(30);
        create.setLayoutX(117);
        create.setLayoutY(460);
        ImageView back = new ImageView("file:///D:/UBB/MAP/LabExtins3/src/main/resources/design/back.png");
        back.setFitHeight(35);
        back.setFitWidth(35);
        back.setLayoutX(24);
        back.setLayoutY(17);
        back.setOnMouseClicked(e -> {
            pane.getChildren().remove(nume);
            pane.getChildren().remove(numelabel);
            pane.getChildren().remove(prenume);
            pane.getChildren().remove(prenumelabel);
            pane.getChildren().remove(parola);
            pane.getChildren().remove(parolalabel);
            pane.getChildren().remove(pic);
            pane.getChildren().remove(piclabel);
            pane.getChildren().remove(create);
            pane.getChildren().remove(back);
            username.setVisible(true);
            password.setVisible(true);
            remember.setVisible(true);
            userlabel.setVisible(true);
            passlabel.setVisible(true);
            login.setVisible(true);
            signup.setVisible(true);
        });
        create.setOnAction(e -> {
            try {
                Utilizator u = new Utilizator(service.getNextUserId(), nume.getText(), prenume.getText());
                u.setPic(piclabel.getText().replace("( ", "").replace(" )", ""));
                u.setHash(Hashing.sha256()
                        .hashString(parola.getText(), StandardCharsets.UTF_8)
                        .toString());
                service.addUtilizator(u);
                pane.getChildren().remove(nume);
                pane.getChildren().remove(numelabel);
                pane.getChildren().remove(prenume);
                pane.getChildren().remove(prenumelabel);
                pane.getChildren().remove(parola);
                pane.getChildren().remove(parolalabel);
                pane.getChildren().remove(pic);
                pane.getChildren().remove(piclabel);
                pane.getChildren().remove(create);
                pane.getChildren().remove(back);
                username.setVisible(true);
                password.setVisible(true);
                remember.setVisible(true);
                userlabel.setVisible(true);
                passlabel.setVisible(true);
                login.setVisible(true);
                signup.setVisible(true);
                username.setText(prenume.getText());
                password.setText(parola.getText());
            } catch (ValidationException ex) {
                MessageAlert.showErrorMessage(null, ex.getMessage());
            }
        });
        pane.getChildren().add(nume);
        pane.getChildren().add(numelabel);
        pane.getChildren().add(prenume);
        pane.getChildren().add(prenumelabel);
        pane.getChildren().add(parola);
        pane.getChildren().add(parolalabel);
        pane.getChildren().add(pic);
        pane.getChildren().add(piclabel);
        pane.getChildren().add(create);
        pane.getChildren().add(back);
    }

}
