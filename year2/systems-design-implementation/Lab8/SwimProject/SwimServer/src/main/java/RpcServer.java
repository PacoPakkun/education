import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import srv.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

//public class RpcServer {
//    public static void main(String[] args) {
//        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:SwimConfig.xml");
//    }
//}

public class RpcServer {
    private static final Logger logger = Logger.getLogger(RpcServer.class.getName());

    private Server server;

    private void start() throws IOException {
        /* The port on which the server should run */
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(getService())
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                try {
                    RpcServer.this.stop();
                } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                }
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final RpcServer server = new RpcServer();
        server.start();
        server.blockUntilShutdown();
    }

    static Service getService() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:SwimConfig.xml");
        Service service = context.getBean(Service.class);
        return service;
    }
}