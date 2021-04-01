package socialnetwork;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import socialnetwork.config.ApplicationContext;
import socialnetwork.domain.validators.MessageValidator;
import socialnetwork.domain.validators.PrietenieValidator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.repository.database.MessageDbRepository;
import socialnetwork.repository.database.PrietenieDbRepository;
import socialnetwork.repository.database.RequestDbRepository;
import socialnetwork.repository.database.UtilizatorDbRepository;
import socialnetwork.service.UtilizatorService;

import java.io.IOException;

public class MainApp extends Application {

    UtilizatorDbRepository userRepository;
    PrietenieDbRepository friendshipFileRepository;
    MessageDbRepository msgFileRepository;
    RequestDbRepository requestFileRepository;
    UtilizatorService service;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        final String url = ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.url");
        final String username = ApplicationContext.getPROPERTIES().getProperty("databse.socialnetwork.username");
        final String pasword = ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.pasword");
        userRepository = new UtilizatorDbRepository(url, username, pasword, new UtilizatorValidator());
        friendshipFileRepository = new PrietenieDbRepository(url, username, pasword, new PrietenieValidator());
        msgFileRepository = new MessageDbRepository(url, username, pasword, new MessageValidator());
        requestFileRepository = new RequestDbRepository(url, username, pasword, new PrietenieValidator());
        service = new UtilizatorService(userRepository, friendshipFileRepository, msgFileRepository, requestFileRepository);

        initView(primaryStage);
        primaryStage.setWidth(600);
        primaryStage.show();

    }

    private void initView(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/loginView.fxml"));
        AnchorPane layout = loader.load();
        primaryStage.setScene(new Scene(layout));
        primaryStage.setTitle("");

        LoginController controller = loader.getController();
        controller.setService(service);
        controller.setStage(primaryStage);

    }
}
