package trabrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author udesc
 */
public interface Banco extends Remote{
    public boolean conectaConta(int conta) throws RemoteException;
    
    public int novaConta() throws RemoteException;
    
    public double saque(double qtd) throws RemoteException;
    
    public double deposito(double qtd) throws RemoteException;
    
    public double transfere(double qtd, int conta) throws RemoteException;
    
    public double saldo() throws RemoteException;
}
