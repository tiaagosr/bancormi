/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabrmi;

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
import javafx.util.Pair;
import trabrmi.servidor.Instancia;

/**
 *
 * @author tiagosr
 */
public class RegistroNomesServidor extends UnicastRemoteObject implements RegistroNomes, Runnable{

    private TreeMap<Integer, String> servidores = new TreeMap<>();
    private Integer mestreId;
    private String mestreEnd = "", endLocal = "";
    private Instancia mestre;
    
    public RegistroNomesServidor(String endLocal) throws RemoteException{
        this.endLocal = endLocal;
    }
    
    
    @Override
    public List<String> getServidores() throws RemoteException{
        return new ArrayList<>(servidores.values());
    }
    
    public String getMaiorServidor() throws RemoteException{
        return servidores.lastEntry().getValue();
    }

    @Override
    public void registraServidor(String endereco) throws RemoteException{
        servidores.put(servidores.lastKey()+1, endereco);
        if(this.mestreId == -1){
            this.novoMestre(-1);
        }
    }

    @Override
    public int getMestreId() throws RemoteException{
        return this.mestreId;
    }
    
        @Override
    public int novoMestre(int mestreAtual) throws RemoteException {
        int mestreId = -1;
        String mestreEnd = "";
        
        if(this.mestreId == mestreAtual){
            this.servidores.remove(this.mestreId);
        }
        
        Entry<Integer, String> entrada = this.servidores.lastEntry();
        if(entrada != null){
            mestreId = entrada.getKey();
            mestreEnd = entrada.getValue();
        }
        
        this.mestreId = mestreId;
        this.mestreEnd = mestreEnd;
        
        return mestreId;
    }
    
    @Override
    public String getMestreEnd() throws RemoteException {
        
    }
    
    public void conectaMestre(){
        try {
            this.mestre = (Instancia) Naming.lookup("//"+this.mestreEnd+"/Banco");
            Naming.rebind("//"+endLocal+"/banco", mestre);
        } catch (MalformedURLException ex) {
            Logger.getLogger(RegistroNomesServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(RegistroNomesServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(RegistroNomesServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {

    }
    
}