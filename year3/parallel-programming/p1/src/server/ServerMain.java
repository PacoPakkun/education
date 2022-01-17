package server;

import model.Hall;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        final Hall hall = new Hall(500);
        Server server = new Server(5555, hall);
        new Thread(server).start();

        try {
            Thread.sleep(30 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        server.stop();
        hall.writeShows();
        hall.writeSales();
        hall.writeHall();
    }
}
