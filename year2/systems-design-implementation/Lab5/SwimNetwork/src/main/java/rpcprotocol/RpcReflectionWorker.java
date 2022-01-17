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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;


public class RpcReflectionWorker implements Runnable, Observer {
    private IService server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    private static Response okResponse = new Response.Builder().type(ResponseType.OK).build();
//    private static Response errorResponse = new Response.Builder().type(ResponseType.ERROR).build();

    public RpcReflectionWorker(IService server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (connected) {
            try {
                Object request = input.readObject();
                Response response = handleRequest((Request) request);
                if (response != null) {
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error " + e);
        }
    }

    private Response handleRequest(Request request) {
        Response response = null;
        String handlerName = "handle" + (request).type();
        System.out.println("HandlerName " + handlerName);
        try {
            Method method = this.getClass().getDeclaredMethod(handlerName, Request.class);
            response = (Response) method.invoke(this, request);
            System.out.println("Method " + handlerName + " invoked");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return response;
    }

    private void sendResponse(Response response) throws IOException {
        System.out.println("sending response " + response);
        output.writeObject(response);
        output.flush();
    }

    public void update(Inscriere inscriere) {
        Response resp = new Response.Builder().type(ResponseType.UPDATE).data(inscriere).build();
        System.out.println("Added Inscriere " + inscriere);
        try {
            sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Response handleLOGIN(Request request) {
        System.out.println("Login request ..." + request.type());
        User user = (User) request.data();
        try {
            server.login(user, this);
            return okResponse;
        } catch (Exception e) {
            connected = false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLOGOUT(Request request) {
        System.out.println("Logout request...");
        User user = (User) request.data();
        try {
            server.logout(user);
            connected = false;
            return okResponse;

        } catch (Exception e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_PROBE(Request request) {
        System.out.println("Get Probe Request ...");
        try {
            List<Proba> probe = server.findAllProbe();
            return new Response.Builder().type(ResponseType.GET_PROBE).data(probe).build();
        } catch (Exception e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_INSCRISI(Request request) {
        System.out.println("Get Inscrisi Request ...");
        Proba proba = (Proba) request.data();
        try {
            List<Participant> participants = server.getInscrisi(proba);
            return new Response.Builder().type(ResponseType.GET_INSCRISI).data(participants).build();
        } catch (Exception e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_INSCRIERI(Request request) {
        System.out.println("Get Inscrieri Request ...");
        Participant participant = (Participant) request.data();
        try {
            List<Proba> probe = server.getInscrieri(participant);
            return new Response.Builder().type(ResponseType.GET_INSCRIERI).data(probe).build();
        } catch (Exception e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleNR_PARTICIPANTI(Request request) {
        System.out.println("Get Nr Participanti Request ...");
        Proba proba = (Proba) request.data();
        try {
            int nr = server.nrParticipanti(proba);
            return new Response.Builder().type(ResponseType.NR_PARTICIPANTI).data(nr).build();
        } catch (Exception e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleADD_PARTICIPANT(Request request) {
        System.out.println("Add Participant request ..." + request.type());
        Participant participant = (Participant) request.data();
        try {
            Participant p = server.addParticipant(participant);
            if (p == null) {
                throw new Exception("Participant Invalid");
            } else
                return new Response.Builder().type(ResponseType.ADD_PARTICIPANT).data(p).build();
        } catch (Exception e) {
            connected = false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleADD_INSCRIERE(Request request) {
        System.out.println("Add Inscriere request ..." + request.type());
        Inscriere inscriere = (Inscriere) request.data();
        try {
            Inscriere i = server.addInscriere(inscriere);
            if (i != null) {
                throw new Exception("Inscriere Invalida");
            } else
                return okResponse;
        } catch (Exception e) {
            connected = false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }
}
