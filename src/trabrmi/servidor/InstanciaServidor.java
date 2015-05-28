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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import trabrmi.RegistroNomes;

/**
 *
 * @author tiagosr
 */
public class InstanciaServidor {
    private BancoServidor dataBanco;
    private String endRegistro, endLocal, endMestre;
    private RegistroNomes registro;
    private List<InstanciaServidor> servidores;
    
    public InstanciaServidor(String endRegistro, String endLocal){
        this.endRegistro = endRegistro;
        this.endLocal = endLocal;
        
        try {
            this.criaInstancia();
        } catch (NotBoundException ex) {
            Logger.getLogger(InstanciaServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(InstanciaServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(InstanciaServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void criaInstancia() throws NotBoundException, MalformedURLException, RemoteException{
        registro = (RegistroNomes) Naming.lookup("rmi://"+endRegistro+"/registroNome");
        this.registro.registraServidor(this.endLocal);
        this.conectaServidores();
    }
    
    private void conectaServidores() throws NotBoundException, MalformedURLException, RemoteException{
        List<String> endServidores = this.registro.getServidores();
        this.servidores = new ArrayList<>();
        for(String endServidor: endServidores){
            if(!endServidor.equals(endLocal)){
                this.conectaServidor(endServidor);
            }
        }
    }
    
    private void setMestre(){
        this.endMestre = this.registro.getMestre();
    }
    
    public void conectaServidor(String endServidor) throws NotBoundException, MalformedURLException, RemoteException{
        InstanciaServidor stump = (InstanciaServidor) Naming.lookup("rmi://"+endServidor+"/instancia");
        this.servidores.add(stump);
    }
    
    public boolean isAlive(){
        return true;
    }
    
    public InstanciaServidor 
}
