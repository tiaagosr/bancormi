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

/**
 *
 * @author tiagosr
 */
public class InstanciaBancoServidor extends UnicastRemoteObject implements Runnable, InstanciaBanco{
    public GerenciaContas dataBanco;
    protected String endLocal, endMestre;
    protected RegistroNomes registro;
    protected List<InstanciaBanco> servidores;
    protected InstanciaBanco servidorMestre;
    protected int idMestre, idLocal;
    
    public InstanciaBancoServidor(String endRegistro, String endLocal) throws RemoteException{
        this.endLocal = endLocal;
        
        try {
            this.iniciaInstancia(endRegistro);
        } catch (NotBoundException ex) {
            Logger.getLogger(InstanciaBancoServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(InstanciaBancoServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(InstanciaBancoServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InstanciaBancoServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InstanciaBancoServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void iniciaInstancia(String endRegistro) throws NotBoundException, MalformedURLException, RemoteException, IOException, ClassNotFoundException{
        //Cadastra-se no servidor de nomes
        registro = (RegistroNomes) Naming.lookup("//"+endRegistro+"/RegistroNomes");
        Naming.rebind("//"+endLocal+"/Banco", this);
        idLocal = this.registro.registrarServidor(this.endLocal);
        
        this.idMestre = this.registro.getIdMestre();
        this.endMestre = this.registro.getEndMestre();
        //Cria referencia para os outros servidores
        this.conectaServidores(); 
        
        if(idMestre != idLocal){ //Caso já existir servidor mestre online
            System.out.println("Sincronizando com outros servidores!");
            this.dataBanco = this.copiarDadosServidor();
        }else{
            this.dataBanco = new GerenciaContas();
        }
    }
    
    protected void conectaServidores() throws NotBoundException, MalformedURLException, RemoteException{
        List<String> endServidores = this.registro.getServidores();
        this.servidores = new ArrayList<>();
        
        for(String endServidor: endServidores){
            InstanciaBanco stump = this.conectaServidor(endServidor);
            if(stump != null){
                stump.conectaServidor(this.endLocal);
                
            }
        }
    }
    
    @Override
    public InstanciaBanco conectaServidor(String endServidor) throws NotBoundException, MalformedURLException, RemoteException{
        InstanciaBanco stump = null;
        if(!endServidor.equals(endLocal)){
            stump = (InstanciaBanco) Naming.lookup("//"+endServidor+"/Banco");
            this.servidores.add(stump);
            if(this.endMestre.equals(endServidor)){
                this.servidorMestre = stump;
            }
        }
        return stump;
    }
    
    @Override
    public boolean isAlive() throws RemoteException{
        if(this.idLocal%2 == 0){
            return false;
        }
        return true;
    }
    
    @Override
    public byte[] getBancoInstancia() throws IOException, RemoteException{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        
        oos.writeObject(this.dataBanco);
        return bos.toByteArray();
    }
    
    protected GerenciaContas copiarDadosServidor() throws IOException, ClassNotFoundException{
        Random genRandom = new Random();
        int servidorOrigem = genRandom.nextInt(servidores.size());
        
        byte[] clone = servidores.get(servidorOrigem).getBancoInstancia();
        ByteArrayInputStream bis = new ByteArrayInputStream(clone);
        ObjectInputStream ois = new ObjectInputStream(bis);
        
         return (GerenciaContas) ois.readObject();
    }
    
    @Override
    public GerenciaContas getBanco() throws RemoteException{
        return this.dataBanco;
    }
    
    @Override
    public void setMestre(String endMestre, int idMestre) throws RemoteException, NotBoundException, MalformedURLException{
        this.servidorMestre = (InstanciaBancoServidor) Naming.lookup("//"+endMestre+"/Banco");
        this.endMestre = endMestre;
        this.idMestre = idMestre;
        System.out.println("Novo mestre: "+endMestre);
        
    }

    protected void substituirMestre(){
        int idNovoMestre = -1;
        
        try {
            idNovoMestre = registro.novoMestre(this.idMestre);
        } catch (RemoteException ex) {
            System.out.println("Falha ao conectar-se com o registro");
        }
        
        if(idNovoMestre == this.idLocal){
            System.out.println("EU SOU O NOVO MESTRE :D");
            for(InstanciaBanco servidor: servidores){
                try {
                    servidor.setMestre(this.endLocal, this.idLocal);
                } catch (RemoteException ex) {
                    Logger.getLogger(InstanciaBancoServidor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NotBoundException ex) {
                    Logger.getLogger(InstanciaBancoServidor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(InstanciaBancoServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(InstanciaBancoServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Verifica no "+this.endLocal+" t:"+servidores.size());
            boolean mestreOff = false;
            
            //Verifica se algum servidor está offline e remove-o da lista
            for(InstanciaBanco servidor: servidores){
                try {
                    System.out.println("resultado: "+servidor.isAlive());;
                } catch (RemoteException ex) {
                    System.out.println("Servidor offline encontrado, removendo-o da lista");
                    if(servidor == servidorMestre){
                        mestreOff = true;
                    }
                    servidores.remove(servidor);
                }
            }
            
            //Se o servidor mestre foi removido da lista
            if(mestreOff){
                System.out.println("Servidor mestre inacessível, elegendo novo mestre");
                this.substituirMestre();
            }
        }   
    }

    protected void replicaConta(int numeroConta) throws RemoteException {
        for(InstanciaBanco servidor: servidores){
            servidor.novaConta(numeroConta);
        }
    }

    protected void replicaSaque(int conta, double qtd) throws RemoteException {
        for(InstanciaBanco servidor: servidores){
            servidor.saque(conta, qtd);
        }
    }

    protected void replicaDeposito(int conta, double qtd) throws RemoteException {
        for(InstanciaBanco servidor: servidores){
            servidor.deposito(conta, qtd);
        }
    }

    protected void replicaTransfere(int contaOrigem, int contaDest, double qtd) throws RemoteException {
        for(InstanciaBanco servidor: servidores){
            servidor.transfere(contaOrigem, contaDest, qtd);
        }
    }

    @Override
    public boolean conectaConta(int conta) throws RemoteException {
        return this.getBanco().conectaConta(conta);
    }

    @Override
    public int novaConta(int numeroConta) throws RemoteException {
        int resultado = this.getBanco().novaConta(numeroConta);
        
        if(this.idMestre == this.idLocal){
            this.replicaConta(numeroConta);
        }
        return resultado;
        
    }

    @Override
    public double saque(int conta, double qtd) throws RemoteException {
        double resultado = this.getBanco().saque(conta, qtd);
        
        if(this.idMestre == this.idLocal){
            this.replicaSaque(conta, qtd);
        }
        return resultado;
    }

    @Override
    public double deposito(int conta, double qtd) throws RemoteException {
        double resultado = this.getBanco().deposito(conta, qtd);
        
        if(this.idMestre == this.idLocal){
            this.replicaDeposito(conta, qtd);
        }
        return resultado;
    }

    @Override
    public double transfere(int contaOrigem, int contaDest, double qtd) throws RemoteException {
        double resultado = this.getBanco().transfere(contaOrigem, contaDest, qtd);
        
        if(this.idMestre == this.idLocal){
            this.replicaTransfere(contaOrigem, contaDest, qtd);
        }
        return resultado;
    }

    @Override
    public double saldo(int conta) throws RemoteException {
        return this.getBanco().saldo(conta);
    }
    
}
