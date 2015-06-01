/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trabrmi;

import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author udesc
 */
public interface RegistroNomes {
    public List<String> getServidores() throws RemoteException;
    
    public int registraServidor(String endereco) throws RemoteException;
    
    public int novoMestre(int mestreAtual) throws RemoteException;
    
    public int getIdMestre() throws RemoteException;
    
    public String getEndMestre() throws RemoteException;
}
