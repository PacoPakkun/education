package ctrl;

import domain.Manager;
import domain.Spectacol;
import repo.Repository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ManageController {
    Stage stage;
    Repository repo;
    String manage = "add";
    Spectacol spectacol;
    Manager manager;

    @FXML
    Button button;
    @FXML
    Label label;
    @FXML
    TextField nume, ora;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setRepo(Repository repo) {
        this.repo = repo;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public void setManage(String manage) {
        this.manage = manage;
        if (manage.equals("add")) {
            button.setText("Adauga");
        } else {
            button.setText("Modifica");
        }
    }

    public void setManage(String manage, Spectacol spectacol) {
        this.manage = manage;
        this.spectacol = spectacol;
        if (manage.equals("add")) {
            button.setText("Adauga");
        } else {
            button.setText("Modifica");
            label.setText("Spectacol #" + spectacol.getId());
            nume.setText(spectacol.getNume());
            ora.setText(spectacol.getOra());
        }
    }

    @FXML
    public void handler() {
        if (manage.equals("add")) {
            repo.addSpectacol(new Spectacol(nume.getText(), ora.getText()));
        } else {
            repo.updateSpectacol(new Spectacol(spectacol.getId(), nume.getText(), ora.getText()));
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Good ");
        alert.setContentText("Operation successful!");
        alert.showAndWait();
    }


    @FXML
    public void backHandler() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/MainWindow.fxml"));
            Parent root = loader.load();
            MainController ctrl = loader.getController();
            ctrl.setStage(stage);
            ctrl.loggedIn(manager);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Teatru Management System");
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setContentText("Error while starting app " + e);
            System.out.println(e);
            alert.showAndWait();
        }
    }
}
