package rpcprotocol;

import domain.Inscriere;
import domain.Participant;
import domain.Proba;
import domain.User;
import services.IService;
import services.Observer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class ChatServicesRpcProxy implements IService {
    private String host;
    private int port;

    private Observer client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public ChatServicesRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<Response>();
    }

    public void login(User user, Observer client) throws Exception {
        initializeConnection();
        Request req = new Request.Builder().type(RequestType.LOGIN).data(user).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.OK) {
            this.client = client;
            return;
        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            closeConnection();
            throw new Exception(err);
        }
    }

    public void logout(User user) throws Exception {
        Request req = new Request.Builder().type(RequestType.LOGOUT).data(user).build();
        sendRequest(req);
        Response response = readResponse();
        closeConnection();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new Exception(err);
        }
    }

    public List<Proba> findAllProbe() throws Exception {
        Request req = new Request.Builder().type(RequestType.GET_PROBE).data("null").build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new Exception(err);
        }
        List<Proba> probe = (List<Proba>) response.data();
        return probe;
    }

    public Participant addParticipant(Participant participant) throws Exception {
        Request req = new Request.Builder().type(RequestType.ADD_PARTICIPANT).data(participant).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new Exception(err);
        }
        Participant p = (Participant) response.data();
        return p;
    }

    public Inscriere addInscriere(Inscriere inscriere) throws Exception {
        Request req = new Request.Builder().type(RequestType.ADD_INSCRIERE).data(inscriere).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new Exception(err);
        }
        Inscriere i = (Inscriere) response.data();
        return i;
    }

    public int nrParticipanti(Proba proba) throws Exception {
        Request req = new Request.Builder().type(RequestType.NR_PARTICIPANTI).data(proba).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new Exception(err);
        }
        int nr = (int) response.data();
        return nr;
    }

    public List<Participant> getInscrisi(Proba proba) throws Exception {
        Request req = new Request.Builder().type(RequestType.GET_INSCRISI).data(proba).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new Exception(err);
        }
        List<Participant> participants = (List<Participant>) response.data();
        return participants;
    }

    public List<Proba> getInscrieri(Participant participant) throws Exception {
        Request req = new Request.Builder().type(RequestType.GET_INSCRIERI).data(participant).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new Exception(err);
        }
        List<Proba> probe = (List<Proba>) response.data();
        return probe;
    }

    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request) throws Exception {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new Exception("Error sending object " + e);
        }

    }

    private Response readResponse() throws Exception {
        Response response = null;
        try {

            response = qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() throws Exception {
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }


    private void handleUpdate(Response response) {
        if (response.type() == ResponseType.UPDATE) {
//            Inscriere inscriere = (Inscriere) response.data();
            Inscriere inscriere = new Inscriere(-1,-1);
            System.out.println("Participant registered " + inscriere);
            try {
                client.update(inscriere);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isUpdate(Response response) {
        return response.type() == ResponseType.UPDATE;
    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    Object response = input.readObject();
                    System.out.println("response received " + response);
                    if (isUpdate((Response) response)) {
                        handleUpdate((Response) response);
                    } else {

                        try {
                            qresponses.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error " + e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }
}
