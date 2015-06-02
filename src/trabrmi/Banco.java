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
    
    public int novaConta(int numeroConta) throws RemoteException;
    
    public double saque(int conta, double qtd) throws RemoteException;
    
    public double deposito(int conta, double qtd) throws RemoteException;
    
    public double transfere(int contaOrigem, int contaDest, double qtd) throws RemoteException;
    
    public double saldo(int conta) throws RemoteException;
}
