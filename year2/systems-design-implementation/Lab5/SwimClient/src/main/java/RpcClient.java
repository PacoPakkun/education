import com.sun.tools.javac.Main;
import ctrl.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rpcprotocol.ChatServicesRpcProxy;
import services.IService;

import java.io.IOException;
import java.util.Properties;


public class RpcClient extends Application {
    private Stage primaryStage;


    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(RpcClient.class.getResourceAsStream("/config.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find config.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("server.host", "");
        int serverPort = -1;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + serverPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        IService server = new ChatServicesRpcProxy(serverIP, serverPort);


        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("views\\MainWindow.fxml"));
        Parent root = loader.load();


        MainController ctrl =
                loader.<MainController>getController();
        ctrl.setService(server);

        primaryStage.setTitle("Swim Contest Manager");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


    }


}


