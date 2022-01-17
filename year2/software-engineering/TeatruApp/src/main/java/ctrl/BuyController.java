package ctrl;

import domain.Loc;
import domain.Spectator;
import domain.Status;
import repo.Repository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class BuyController {
    Stage stage;
    List<Loc> locuri;
    Repository repo;

    public void setRepo(Repository repo) {
        this.repo = repo;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    ListView bilete;
    @FXML
    TextField nume, tel;

    public void setBilete(List<Loc> locuri) {
        this.locuri = locuri;
        for (Loc loc : locuri) {
            bilete.getItems().add(loc.toString());
        }
    }

    @FXML
    public void initialize() {
    }

    @FXML
    public void buyHandler() {
        if (nume.getText() != "" && tel.getText() != "") {
            Spectator spectator = new Spectator(-1, nume.getText(), tel.getText());
            int id = repo.addSpectator(spectator);
            spectator.setId(id);
            for (Loc loc : locuri) {
                loc.setStare(Status.rezervat);
                loc.setSpectator(spectator);
                repo.updateLoc(loc);
            }
            Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
            errorAlert.setHeaderText("OK");
            errorAlert.setContentText("OK");
            errorAlert.showAndWait();
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("not OK");
            errorAlert.setContentText("not OK");
            errorAlert.showAndWait();
        }
    }

    @FXML
    public void backHandler() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/MainWindow.fxml"));
            Parent root = loader.load();
            MainController ctrl = loader.getController();
            ctrl.setStage(stage);
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
