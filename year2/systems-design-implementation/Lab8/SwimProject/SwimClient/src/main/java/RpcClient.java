import ctrl.MainController;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IService;
import swim.SwimServiceGrpc;

import java.util.logging.Logger;


//public class RpcClient extends Application {
//    private Stage primaryStage;
//
//    public void start(Stage primaryStage) throws Exception {
//        try {
//            ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:SwimClient.xml");
//            IService server = (IService) factory.getBean("service");
//            System.out.println("Obtained a reference to remote chat server");
//
//            FXMLLoader loader = new FXMLLoader(
//                    getClass().getClassLoader().getResource("views\\MainWindow.fxml"));
//            Parent root = loader.load();
//
//            MainController ctrl =
//                    loader.<MainController>getController();
//            ctrl.setService(server);
//
//            primaryStage.setTitle("Swim Contest Manager");
//            primaryStage.setScene(new Scene(root));
//            primaryStage.show();
//
//        } catch (Exception e) {
//            System.err.println("Chat Initialization exception:" + e);
//            e.printStackTrace();
//        }
//    }
//}

public class RpcClient extends Application {
    private static final Logger logger = Logger.getLogger(RpcClient.class.getName());
    private Stage primaryStage;
    private SwimServiceGrpc.SwimServiceBlockingStub server;
    private SwimServiceGrpc.SwimServiceStub asyncServer;

    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting. The second argument is the target server.
     */
    public void start(Stage primaryStage) throws Exception {
        String target = "localhost:50051";

        // Create a communication channel to the server, known as a Channel. Channels are thread-safe
        // and reusable. It is common to create channels at the beginning of your application and reuse
        // them until the application shuts down.
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build();
        try {
            server = SwimServiceGrpc.newBlockingStub(channel);
            asyncServer = SwimServiceGrpc.newStub(channel);
            FXMLLoader loader = new FXMLLoader(
                    getClass().getClassLoader().getResource("views\\MainWindow.fxml"));
            Parent root = loader.load();

            MainController ctrl =
                    loader.<MainController>getController();
            ctrl.setService(server, asyncServer);

            primaryStage.setTitle("Swim Contest Manager");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } finally {
            // ManagedChannels use resources like threads and TCP connections. To prevent leaking these
            // resources the channel should be shut down when it will no longer be used. If it may be used
            // again leave it running.
            //channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}


