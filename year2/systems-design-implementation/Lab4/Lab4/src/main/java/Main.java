import ctrl.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import srv.Service;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("views\\MainWindow.fxml"));
            Parent root = loader.load();
            MainController ctrl = loader.getController();
            ctrl.setService(getService());
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Swim Contest Manager");
            primaryStage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setContentText("Error while starting app " + e);
            System.out.println(e);
            alert.showAndWait();
        }
    }

    static Service getService() {
        ApplicationContext context = new ClassPathXmlApplicationContext("SwimConfig.xml");
        Service service = context.getBean(Service.class);
        return service;
    }

    public static void main(String[] args) {
        launch(args);

//        Properties props = new Properties();
//        try {
//            props.load(new FileInputStream("src/config.properties"));
//        } catch (IOException e) {
//            System.out.println("Error: " + e);
//        }
//        final String url = props.getProperty("db.url");
//        if (url == null)
//            System.out.println("error");
//        ParticipantRepositoryDB repo = new ParticipantRepositoryDB(url);
//        ProbaRepositoryDB repo2 = new ProbaRepositoryDB(url);
//        InscriereRepositoryDB repo3 = new InscriereRepositoryDB(url);
//        UserRepositoryDB repo4 = new UserRepositoryDB(url);
//        repo.findAll().forEach(System.out::println);
//        repo2.findAll().forEach(System.out::println);
//        repo3.findAll().forEach(System.out::println);
//        repo4.findAll().forEach(System.out::println);
//        repo.add(new Participant(null, "test",10));
//        repo2.add(new Proba(null, 10,"test"));
//        repo3.add(new Inscriere(new Pair<>(1,1)));
//        repo4.add(new User(null, "test","test"));
//        repo.update(new Participant(10, "tezt",10));
//        repo2.update(new Proba(20, 10,"tezt"));
//        repo3.update(new Inscriere(new Pair<>(1,1)));
//        repo4.update(new User(5, "tezt","tezt"));
//        System.out.println(repo.findOne(10));
//        System.out.println(repo2.findOne(20));
//        System.out.println(repo3.findOne(new Pair<>(1,1)));
//        System.out.println(repo4.findOne(5));
//        repo.delete(10);
//        repo2.delete(20);
//        repo3.delete(new Pair<>(1,1));
//        repo4.delete(5);
//        repo.findAll().forEach(System.out::println);
//        repo2.findAll().forEach(System.out::println);
//        repo3.findAll().forEach(System.out::println);
//        repo4.findAll().forEach(System.out::println);
//        repo3.getInscrisi(new Proba(1,null,null)).forEach(System.out::println);
//        System.out.println(repo3.nrParticipanti(new Proba(1,null,null)));
    }


}