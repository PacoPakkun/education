package utils;

import rpcprotocol.RpcReflectionWorker;
import services.IService;

import java.net.Socket;


public class RpcConcurrentServer extends AbsConcurrentServer {
    private IService server;

    public RpcConcurrentServer(int port, IService chatServer) {
        super(port);
        this.server = chatServer;
        System.out.println("RpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        // ChatClientRpcWorker worker = new ChatClientRpcWorker(chatServer, client);
        RpcReflectionWorker worker = new RpcReflectionWorker(server, client);
        Thread tw = new Thread(worker);
        return tw;
    }

    @Override
    public void stop() {
        System.out.println("Stopping services ...");
    }
}
