/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabrmi.servidor;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author tiagosr
 */
public interface Instancia extends Remote{
    public boolean isAlive() throws RemoteException;
    public BancoServidor getBanco() throws RemoteException;
    public byte[] getBancoInstancia() throws IOException, RemoteException;
}
