package server;

import model.Hall;
import model.Sale;
import model.SaleDTO;
import model.Show;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.util.List;


public class Worker implements Runnable {

    protected Socket clientSocket = null;
    private Hall hall;

    public Worker(Socket clientSocket, Hall hall) {
        this.clientSocket = clientSocket;
        this.hall = hall;
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            InputStream is = clientSocket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            SaleDTO sale = (SaleDTO) ois.readObject();
            if (checkAvailability(sale))
                out.println("Vanzare reusita");
            else
                out.println("vanzare nereusita");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    private boolean checkAvailability(SaleDTO sale) {
        List<Integer> seats_to_be_sold = sale.getSeats();
        List<Integer> busySlots = hall.seatsFromShow(sale.getIdShow());
        boolean sold = busySlots.stream().anyMatch(seats_to_be_sold::contains);

        System.out.println("Seats to be sold: " + seats_to_be_sold);
        System.out.println("Occupied seats: " + busySlots);
        System.out.println("Sale id: " + sale.getIdShow());

        if (sold) {
            return false;
        }

        Show show = hall.getTicketPriceOfShow(sale.getIdShow());
        synchronized (show) {
            int totalPrice = show.getTicketPrice() * sale.getSeats().size();
            Sale sale1 = new Sale(sale.getIdShow(), LocalDate.now(), sale.getSeats().size(), sale.getSeats(), totalPrice);

            System.out.println("Sale sum: " + totalPrice);
            hall.addSale(sale1);
            show.addSoldSum(totalPrice);
            show.addSeats(sale.getSeats());
            return true;
        }
    }
}