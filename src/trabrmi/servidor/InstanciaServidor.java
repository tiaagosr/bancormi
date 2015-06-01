/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabrmi.servidor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import trabrmi.RegistroNomes;

/**
 *
 * @author tiagosr
 */
public class InstanciaServidor extends UnicastRemoteObject implements Runnable, Instancia{
    private BancoServidor dataBanco;
    private String endLocal;
    private RegistroNomes registro;
    private List<InstanciaServidor> servidores;
    private InstanciaServidor servidorMestre;
    private int idMestre;
    
    public InstanciaServidor(String endRegistro, String endLocal) throws RemoteException{
        this.endLocal = endLocal;
        
        try {
            this.criaInstancia(endRegistro);
        } catch (NotBoundException ex) {
            Logger.getLogger(InstanciaServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(InstanciaServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(InstanciaServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InstanciaServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InstanciaServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void criaInstancia(String endRegistro) throws NotBoundException, MalformedURLException, RemoteException, IOException, ClassNotFoundException{
        //Cadastra-se no servidor de nomes
        registro = (RegistroNomes) Naming.lookup("//"+endRegistro+"/RegistroNomes");
        String endMestre = this.registro.getMestreEnd();
        Naming.rebind("//"+endLocal+"/Instancia", this);
        Naming.rebind("//"+endLocal+"/Banco", this.dataBanco);
        this.registro.registraServidor(this.endLocal);
        if(endMestre != ""){
            this.sincronizarServidor();
        }
        
        this.conectaServidores();
    }
    
    private void conectaServidores() throws NotBoundException, MalformedURLException, RemoteException{
        List<String> endServidores = this.registro.getServidores();
        this.servidores = new ArrayList<>();
        
        for(String endServidor: endServidores){
            this.conectaServidor(endServidor);
        }
    }
    
    public void conectaServidor(String endServidor) throws NotBoundException, MalformedURLException, RemoteException{
        if(!endServidor.equals(endLocal)){
            InstanciaServidor stump = (InstanciaServidor) Naming.lookup("//"+endServidor+"/Instancia");
            this.servidores.add(stump);
        }
    }
    
    @Override
    public boolean isAlive() throws RemoteException{
        return true;
    }
    
    @Override
    public byte[] getBancoInstancia() throws IOException, RemoteException{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        
        oos.writeObject(this.dataBanco);
        return bos.toByteArray();
    }
    
    private void sincronizarServidor() throws IOException, ClassNotFoundException{
        Random genRandom = new Random();
        int servidorOrigem = genRandom.nextInt(servidores.size()-1);
        
        byte[] clone = servidores.get(servidorOrigem).getBancoInstancia();
        ByteArrayInputStream bis = new ByteArrayInputStream(clone);
        ObjectInputStream ois = new ObjectInputStream(bis);
        
        this.dataBanco = (BancoServidor) ois.readObject();
    }
    
    @Override
    public BancoServidor getBanco() throws RemoteException{
        return this.dataBanco;
    }
    
    public void setMestre(String mestre) throws RemoteException, NotBoundException, MalformedURLException{
        this.servidorMestre = (InstanciaServidor) Naming.lookup("//"+mestre+"/Instancia");
        System.out.println("Novo mestre: "+mestre);
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(InstanciaServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            boolean mestreOff = false;
            
            for(InstanciaServidor servidor: servidores){
                try {
                    servidorMestre.isAlive();
                } catch (RemoteException ex) {
                    System.out.println("Servidor offline encontrado, removendo-o da lista");
                    if(servidor == servidorMestre){
                        mestreOff = true;
                    }
                    servidores.remove(servidor);
                }
            }
            
            if(mestreOff == true){
                System.out.println("Servidor mestre inacessível, elegendo novo mestre");
                int mestre = -1;
                try {
                    mestre = registro.novoMestre(this.idMestre);
                } catch (RemoteException ex) {
                    Logger.getLogger(InstanciaServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(mestre == this.endLocal){
                    System.out.println("EU SOU O NOVO MESTRE :D");
                    for(InstanciaServidor servidor: servidores){
                        try {
                            servidor.setMestre(this.endLocal);
                        } catch (RemoteException ex) {
                            Logger.getLogger(InstanciaServidor.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NotBoundException ex) {
                            Logger.getLogger(InstanciaServidor.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (MalformedURLException ex) {
                            Logger.getLogger(InstanciaServidor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }

        }
        
    }
    
}
