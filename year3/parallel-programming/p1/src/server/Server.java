package server;

import model.Hall;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    protected int serverPort;
    protected ServerSocket serverSocket = null;
    protected boolean isStopped = false;
    protected ExecutorService threadPool =
            Executors.newFixedThreadPool(10);
    private Hall hall;
    private Timer timer;

    public Server(int port, Hall hall) {
        this.serverPort = port;
        this.hall = hall;
        timer = new Timer();
    }

    public void run() {
        Verify verify = null;
        try {
            verify = new Verify(hall);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int delay = 5000;
        int period = 5000;
        timer.scheduleAtFixedRate(verify, delay, period);
        openServerSocket();
        while (!isStopped()) {
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if (isStopped()) {
                    System.out.println("Server Stopped.");
                    break;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }
            threadPool.execute(new Worker(clientSocket, hall));
        }
        threadPool.shutdown();
        System.out.println("Server Stopped.");
    }


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop() {
        isStopped = true;
        timer.cancel();
        timer.purge();
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }
}