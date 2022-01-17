import ctrl.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IService;


public class RpcClient extends Application {
    private Stage primaryStage;

    public void start(Stage primaryStage) throws Exception {
        try {
            ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:SwimClient.xml");
            IService server = (IService) factory.getBean("service");
            System.out.println("Obtained a reference to remote chat server");

            FXMLLoader loader = new FXMLLoader(
                    getClass().getClassLoader().getResource("views\\MainWindow.fxml"));
            Parent root = loader.load();

            MainController ctrl =
                    loader.<MainController>getController();
            ctrl.setService(server);

            primaryStage.setTitle("Swim Contest Manager");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

        } catch (Exception e) {
            System.err.println("Chat Initialization exception:" + e);
            e.printStackTrace();
        }
    }


}


