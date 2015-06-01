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
    
    public void registraServidor(String endereco) throws RemoteException;
    
    public int novoMestre(int mestreAtual) throws RemoteException;
    
    public int getMestreId() throws RemoteException;
    
    public String getMestreEnd() throws RemoteException;
}
