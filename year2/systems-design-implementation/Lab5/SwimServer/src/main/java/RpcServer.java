import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IService;
import srv.Service;
import utils.AbstractServer;
import utils.RpcConcurrentServer;
import utils.ServerException;

import java.io.IOException;
import java.util.Properties;

public class RpcServer {

    public static void main(String[] args) {
        Properties serverProps = new Properties();
        try {
            serverProps.load(RpcServer.class.getResourceAsStream("/config.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties " + e);
            return;
        }
        int port = -1;
        try {
            port = Integer.parseInt(serverProps.getProperty("server.port"));
        } catch (NumberFormatException nef) {
            System.err.println("Wrong Port Number" + nef.getMessage());
            System.err.println("Using default port " + port);
        }
        System.out.println("Starting server on port: " + port);
        AbstractServer server = new RpcConcurrentServer(port, getService());
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        } finally {
            try {
                server.stop();
            } catch (ServerException e) {
                System.err.println("Error stopping server " + e.getMessage());
            }
        }
    }

    static IService getService() {
        ApplicationContext context = new ClassPathXmlApplicationContext("SwimConfig.xml");
        Service service = context.getBean(Service.class);
        return service;
    }
}
