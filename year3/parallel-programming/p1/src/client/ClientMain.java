package client;

import model.SaleDTO;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class ClientMain {
    public static void main(String[] args) throws IOException {
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                int tickets = ThreadLocalRandom.current().nextInt(1, 11);
                Client client1 = new Client();
                client1.startConnection("127.0.0.1", 5555);
                List<Integer> seats = ThreadLocalRandom.current().ints(0, 100).distinct().limit(tickets).boxed().collect(Collectors.toList());
                int showId = ThreadLocalRandom.current().nextInt(1, 4);
                SaleDTO saleDTO = new SaleDTO(showId, seats);

                try {
                    System.out.println(client1.sendSale(saleDTO));
                    client1.stopConnection();
                } catch (IOException | NullPointerException e) {
                    t.cancel();
                    t.purge();
                    System.out.println("Server has stopped!");

                }


            }
        }, 0, 2000);
    }

}
