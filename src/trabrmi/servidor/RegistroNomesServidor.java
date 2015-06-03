/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabrmi.servidor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import trabrmi.servidor.InstanciaBanco;

/**
 *
 * @author tiagosr
 */
public class RegistroNomesServidor extends UnicastRemoteObject implements RegistroNomes{

    private TreeMap<Integer, String> servidores = new TreeMap<>();
    private int idMestre = -1;
    private String endMestre = "", endLocal = "";
    private InstanciaBanco mestre;
    
    public RegistroNomesServidor(String endLocal) throws RemoteException{
        this.endLocal = endLocal;
        try {
            Naming.rebind("//"+endLocal+"/RegistroNomes", this);
        } catch (MalformedURLException ex) {
            Logger.getLogger(RegistroNomesServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Override
    public List<String> getServidores() throws RemoteException{
        return new ArrayList<>(servidores.values());
    }
    
    public String getMaiorServidor() throws RemoteException{
        return servidores.lastEntry().getValue();
    }


    @Override
    public int registrarServidor(String endereco) throws RemoteException{
        int id = 0;
        if(servidores.size() > 0){
            id = servidores.lastKey()+1;
        }
        
        servidores.put(id, endereco);

        if(this.idMestre == -1){
            this.novoMestre(-1);
        }
        
        return id;
    }

    @Override
    public int getIdMestre() throws RemoteException{
        return this.idMestre;
    }
    
    @Override
    public String getEndMestre() throws RemoteException {
        return this.endMestre;
    }
    
        @Override
    public int novoMestre(int mestreAtual) throws RemoteException {
        int mestreId = -1;
        String mestreEnd = "";
        
        if(this.idMestre == mestreAtual){
            this.servidores.remove(this.idMestre);
        }
        
        Entry<Integer, String> entrada = this.servidores.lastEntry();
        if(entrada != null){
            mestreId = entrada.getKey();
            mestreEnd = entrada.getValue();
        }
        
        this.idMestre = mestreId;
        this.endMestre = mestreEnd;
        
        this.conectaMestre();
        
        return mestreId;
    }
    
    public void conectaMestre(){
        try {
            this.mestre = (InstanciaBanco) Naming.lookup("//"+this.endMestre+"/Banco");
            Naming.rebind("//"+endLocal+"/Bancos", mestre);
        } catch (MalformedURLException ex) {
            Logger.getLogger(RegistroNomesServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(RegistroNomesServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(RegistroNomesServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
