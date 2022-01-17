package services;

import domain.Inscriere;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Observer extends Remote {
    void update(Inscriere inscriere) throws Exception, RemoteException;
}
