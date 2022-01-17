package client;

import model.SaleDTO;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private OutputStream os;
    private ObjectOutputStream oos;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            os = clientSocket.getOutputStream();
            oos = new ObjectOutputStream(os);
        } catch (IOException e) {
        }

    }

    public String sendSale(SaleDTO saleDTO) throws IOException {
        oos.writeObject(saleDTO);
        return in.readLine();
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}