package trabrmi;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import trabrmi.Banco;

/**
 *
 * @author udesc
 */
public class BancoServidor extends UnicastRemoteObject implements Banco{

    BancoServidor() throws RemoteException{
        
    }
    
    @Override
    public boolean conectaConta(int conta) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int novaConta() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double saque(double qtd) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double deposito(double qtd) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double transfere(double qtd, int conta) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double saldo() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
