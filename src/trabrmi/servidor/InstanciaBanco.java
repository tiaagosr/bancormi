/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabrmi.servidor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import trabrmi.Banco;

/**
 *
 * @author tiagosr
 */
public interface InstanciaBanco extends Banco{
    
    public boolean isAlive() throws RemoteException;
    
    public GerenciaContas getBanco() throws RemoteException;
    
    public byte[] getBancoInstancia() throws IOException, RemoteException;

    public void setMestre(String endLocal, int idLocal) throws RemoteException, NotBoundException, MalformedURLException;
}
