package socialnetwork;

import javafx.scene.image.Image;
import socialnetwork.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import socialnetwork.config.ApplicationContext;
import socialnetwork.controller.MainController;
import socialnetwork.domain.validators.EventValidator;
import socialnetwork.domain.validators.MessageValidator;
import socialnetwork.domain.validators.PrietenieValidator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.repository.database.*;
import socialnetwork.service.UtilizatorService;

import java.lang.*;
import java.io.IOException;

public class MainApp extends Application {

    UtilizatorPageRepository userRepository;
    PrietenieDbRepository friendshipRepository;
    MessageDbRepository msgRepository;
    RequestDbRepository requestRepository;
    EventDbRepository eventRepository;
    UtilizatorService service;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        final String url = ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.url");
        final String username = ApplicationContext.getPROPERTIES().getProperty("databse.socialnetwork.username");
        final String pasword = ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.pasword");
        userRepository = new UtilizatorPageRepository(url, username, pasword, new UtilizatorValidator());
        friendshipRepository = new PrietenieDbRepository(url, username, pasword, new PrietenieValidator());
        msgRepository = new MessageDbRepository(url, username, pasword, new MessageValidator());
        requestRepository = new RequestDbRepository(url, username, pasword, new PrietenieValidator());
        eventRepository = new EventDbRepository(url, username, pasword, new EventValidator());
        service = new UtilizatorService(userRepository, friendshipRepository, msgRepository, requestRepository, eventRepository);

        initView(primaryStage);
        primaryStage.show();

    }

    private void initView(Stage primaryStage) throws IOException {
        primaryStage.getIcons().add(new Image("/design/icon.png"));
        primaryStage.setTitle("Groove");
        if (service.lastLogin().isPresent()) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/mainView.fxml"));
            AnchorPane layout = loader.load();
            primaryStage.setScene(new Scene(layout));
            primaryStage.setWidth(430);
            MainController controller = loader.getController();
            controller.setService(service);
            controller.setUser(service.lastLogin().get().getLastName());
            controller.setStage(primaryStage);
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/loginView.fxml"));
            AnchorPane layout = loader.load();
            primaryStage.setScene(new Scene(layout));
            primaryStage.setWidth(430);
            LoginController controller = loader.getController();
            controller.setService(service);
            controller.setStage(primaryStage);
        }
    }
}
